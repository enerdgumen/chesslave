package io.chesslave.app

import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.json.Json
import io.vertx.ext.web.handler.sockjs.BridgeOptions
import io.vertx.ext.web.handler.sockjs.PermittedOptions
import io.vertx.reactivex.core.Vertx
import io.vertx.reactivex.core.eventbus.EventBus
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.handler.sockjs.SockJSHandler
import org.slf4j.LoggerFactory
import io.vertx.core.eventbus.EventBus as VertxEventBus

object log {

    private val logger = LoggerFactory.getLogger("main")
    internal lateinit var bus: EventBus

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
    val eventBusHandler = SockJSHandler.create(vertx).bridge(bridgeOptions)
    router.route("/events/*").handler(eventBusHandler)
    vertx.createHttpServer().requestHandler(router::accept).listen(8080) {
        if (it.succeeded()) log.info("Server started")
    }
    Chesslave.configure(vertx.eventBus())
}