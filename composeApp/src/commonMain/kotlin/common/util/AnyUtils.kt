package common.util

inline fun <reified T> T.isPrimitiveOrString(): Boolean {
    return when (T::class) {
        String::class,
        Int::class,
        Long::class,
        Float::class,
        Double::class,
        Char::class,
        Boolean::class,
        Short::class,
        Byte::class -> true
        else -> false
    }
}
