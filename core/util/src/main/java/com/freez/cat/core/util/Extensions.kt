package com.freez.cat.core.util

fun String?.getFlagUrl():String {
    this?.let {
        return "${Constant.FLAG_WEB_SITE_URL}${this.lowercase()}.png"
    }
    return "${Constant.FLAG_WEB_SITE_URL}ir.png"
}