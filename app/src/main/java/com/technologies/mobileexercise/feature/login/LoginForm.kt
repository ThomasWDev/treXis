package com.technologies.mobileexercise.feature.login

import androidx.annotation.IntDef

/**
 * Data validation state of the login form.
 */

data class LoginForm(
        var username: String? = null,
        var password: String? = null
)

data class LoginFormState(
        var usernameError: String? = null,
        var passwordError: String? = null,
        var isDataValid: Boolean = false
)

@Retention(AnnotationRetention.RUNTIME)
@IntDef(LoginField.USERNAME, LoginField.PASSWORD)
annotation class LoginField {
    companion object {
        const val USERNAME = 0
        const val PASSWORD = 1
    }
}