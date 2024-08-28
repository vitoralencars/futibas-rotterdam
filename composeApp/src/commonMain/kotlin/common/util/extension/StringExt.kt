package common.util.extension

fun String.checkEmptyTag() = if (this == "<empty>") "" else this
