package io.chesslave.extensions

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