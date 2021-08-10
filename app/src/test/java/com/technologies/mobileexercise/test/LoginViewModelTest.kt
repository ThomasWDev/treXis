package com.technologies.mobileexercise.test

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.technologies.mobileexercise.base.CoroutinesTestRule
import com.technologies.mobileexercise.core.data.api.AuthRepository
import com.technologies.mobileexercise.feature.login.LoginField
import com.technologies.mobileexercise.feature.login.LoginForm
import com.technologies.mobileexercise.feature.login.LoginFormState
import com.technologies.mobileexercise.feature.login.LoginViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest

@FlowPreview
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule(order = 0)
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule(order = 1)
    val rule = InstantTaskExecutorRule()

    @MockK
    lateinit var loginViewModel: LoginViewModel

    @MockK(relaxed = true)
    lateinit var context: Context

    @MockK
    lateinit var authRepository: AuthRepository

    @MockK(relaxed = true)
    lateinit var loginFormObserver: Observer<LoginForm>

    @MockK(relaxed = true)
    lateinit var loginFormStateObserver: Observer<LoginFormState>

    @MockK(relaxed = true)
    lateinit var loginSuccessObserver: Observer<Boolean>

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        loginViewModel = LoginViewModel(context, authRepository)
        loginViewModel.apply {
            loginSuccess.observeForever(loginSuccessObserver)
            form.observeForever(loginFormObserver)
            formState.observeForever(loginFormStateObserver)
        }

    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `User put username but leaves password field empty`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            loginViewModel.run {

                onDataChange("morty", LoginField.USERNAME)

                val formCapture = slot<LoginForm>()
                val formStateCapture = slot<LoginFormState>()

                verify {
                    loginFormObserver.onChanged(
                        capture(formCapture)
                    )

                    loginFormStateObserver.onChanged(
                        capture(formStateCapture)
                    )
                }

                assertEquals(true, formCapture.captured.username == "morty")
                assertEquals(true, formCapture.captured.password == null)
                assertEquals(false, formStateCapture.captured.isDataValid)

                confirmVerified(loginFormObserver)
                confirmVerified(loginFormStateObserver)
            }
        }
}