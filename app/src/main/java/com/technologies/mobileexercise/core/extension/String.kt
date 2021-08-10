package com.technologies.mobileexercise.core.extension

fun String?.isPasswordValid(): Boolean {
    return !this.isNullOrBlank() && this.length > 4
}