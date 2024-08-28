package common.util.extension

import common.model.Country

fun Country?.orEmpty() = this ?: Country("", "")
