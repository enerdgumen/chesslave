package io.chesslave.extensions

import javaslang.Tuple
import javaslang.Tuple2
import javaslang.collection.Map
import javaslang.collection.Stream
import javaslang.control.Option

// Stdlib

fun <T> T?.nullableToList(): List<T> =
    if (this == null) emptyList() else listOf(this)

fun <T> T?.nullableToSet(): Set<T> =
    if (this == null) emptySet() else setOf(this)

inline val <T> T?.defined: Boolean get() = this != null
inline val <T> T?.undefined: Boolean get() = this == null

fun <T> T?.exists(predicate: (T) -> Boolean): Boolean =
    if (this != null) predicate(this) else false

typealias Predicate<T> = (T) -> Boolean

infix fun <T> Predicate<T>.and(predicate: Predicate<T>): Predicate<T> =
    { t -> this(t) && predicate(t) }

infix fun <T> Predicate<T>.or(predicate: Predicate<T>): Predicate<T> =
    { t -> this(t) || predicate(t) }

infix fun String.concat(other: String): String = this + other

// JavaSlang

fun <T> T?.asOption(): Option<T> = Option.of(this)

operator fun <A, B> Tuple2<A, B>.component1(): A = this._1
operator fun <A, B> Tuple2<A, B>.component2(): B = this._2
fun <A, B> Tuple2<A, B>.asPair(): Pair<A, B> = Pair(this._1, this._2)
fun <A, B> Pair<A, B>.asTuple(): Tuple2<A, B> = Tuple.of(this.first, this.second)

fun <K, V> Map<K, V>.getOrNull(key: K): V?
    = this.get(key).let { if (it.isDefined) it.get() else null }

fun <T> T?.iterate(f: (T) -> T?): Stream<T>
    = Stream.iterate(this) { if (it != null) f(it) else null }
    .takeWhile { it != null }
    .map { it!! }