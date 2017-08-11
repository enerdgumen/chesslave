package io.chesslave.app

import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.reactivex.Observable
import io.reactivex.Single
import io.vertx.core.Vertx
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.eventbus.Message
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.sockjs.BridgeEventType
import io.vertx.ext.web.handler.sockjs.BridgeOptions
import io.vertx.ext.web.handler.sockjs.PermittedOptions
import io.vertx.ext.web.handler.sockjs.SockJSHandler
import org.slf4j.LoggerFactory
import io.vertx.core.eventbus.EventBus as VertxEventBus

object log {

    private val logger = LoggerFactory.getLogger("main")
    internal lateinit var bus: VertxEventBus

    fun info(message: String) {
        logger.info(message)
        publish("INFO", message)
    }

    fun debug(message: String) {
        logger.debug(message)
        publish("DEBUG", message)
    }

    fun error(message: String, throwable: Throwable) {
        logger.error(message, throwable)
        publish("ERROR", "$message ($throwable)")
    }

    private fun publish(level: String, message: String) {
        bus.publish("log", message, DeliveryOptions().addHeader("level", level))
    }
}

fun main(args: Array<String>) {
    Json.mapper.registerModule(KotlinModule())
    val vertx = Vertx.vertx()
    log.bus = vertx.eventBus()
    val router = Router.router(vertx)
    val bridgeOptions = BridgeOptions()
        .addInboundPermitted(PermittedOptions())
        .addOutboundPermitted(PermittedOptions())
    val eventBusHandler = SockJSHandler.create(vertx).bridge(bridgeOptions) { event ->
        if (event.type() === BridgeEventType.SOCKET_CREATED) log.info("Web socket created")
        event.complete(true)
    }
    router.route("/events/*").handler(eventBusHandler)
    vertx.createHttpServer().requestHandler(router::accept).listen(8080) {
        if (it.succeeded()) log.info("Server started")
    }
    Chesslave.configure(EventBus(vertx.eventBus()))
}

class EventBus(val bus: VertxEventBus) {


    fun consume(address: String): Observable<Message<Any?>> =
        Observable.create { source -> bus.consumer<Any?>(address, source::onNext) }

    fun <T> consume(address: String, eventClass: Class<T>): Observable<T> =
        Observable.create { source ->
            bus.consumer<JsonObject>(address, { message ->
                source.onNext(message.body().mapTo(eventClass))
            })
        }

    fun publish(address: String, payload: Any? = null) {
        bus.publish(address, payload?.let(JsonObject::mapFrom))
    }

    fun <T> send(address: String, payload: Any?): Single<Message<T>> =
        Single.create { source ->
            bus.send<T>(address, payload?.let(JsonObject::mapFrom)) { response ->
                if (response.succeeded())
                    source.onSuccess(response.result())
                else
                    source.onError(response.cause())
            }
        }
}