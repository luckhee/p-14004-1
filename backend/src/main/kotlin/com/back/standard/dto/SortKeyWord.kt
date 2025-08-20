package com.back.standard.dto

import com.back.standard.extensions.toCamelCase

enum class SortKeyWord {
    ID,
    ID_ASC,
    USERNAME,
    USERNAME_ASC,
    NICKNAME,
    NICKNAME_ASC;

    val property by lazy {
        name.removeSuffix("_ASC").toCamelCase()
    }

    val isAsc by lazy {
        name.endsWith("_ASC")
    }
}
