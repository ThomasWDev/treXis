package com.technologies.mobileexercise.feature.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.technologies.mobileexercise.R
import com.technologies.mobileexercise.core.base.BaseViewModel
import com.technologies.mobileexercise.core.data.api.AuthRepository
import com.technologies.mobileexercise.core.extension.isPasswordValid
import com.technologies.mobileexercise.core.extension.updateFields
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@HiltViewModel
class LoginViewModel @Inject constructor(
        @ApplicationContext private val context: Context,
        private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _formState = MutableLiveData(LoginFormState())
    val formState: LiveData<LoginFormState> = _formState

    private val _form = MutableLiveData<LoginForm>()
    val form: LiveData<LoginForm> = _form

    private val _loginSuccess = MutableLiveData(false)
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    private val dataStreamFlow = MutableSharedFlow<Pair<String, Int>>()

    //Observe form updates and update formState based on form data
    private val formObserver = Observer<LoginForm> { form ->
        if (form != null) {
            _formState.updateFields {
                it.value?.let { state ->
                    state.usernameError = if (form.username.isNullOrBlank()) {
                        context.getString(R.string.format_invalid_field_generic, "username")
                    } else {
                        null
                    }
                    state.passwordError = if (!form.password.isPasswordValid()) {
                        context.getString(R.string.format_invalid_field_generic, "password")
                    } else {
                        null
                    }
                    state.isDataValid = form.username?.isNotEmpty() == true && form.password.isPasswordValid()
                }
            }
        }
    }

    init {
        form.observeForever(formObserver)

        viewModelScope.launch { observeDataStream() }
    }

    fun setLoginSuccess(value: Boolean) {
        _loginSuccess.value = value
    }

    //Observing text change updates based on field (Int) as flow with debounce
    suspend fun observeDataStream() {
        dataStreamFlow.debounce(500)
                .collect { data ->
                    _form.updateFields {
                        if (it.value == null) {
                            it.value = LoginForm()
                        }
                        it.value?.let { form ->
                            when (data.second) {
                                LoginField.USERNAME -> form.username = data.first
                                LoginField.PASSWORD -> {
                                    form.password = data.first
                                }
                            }
                        }
                    }
                }
    }

    //Send text changes to SharedFlow with field (Int) as edittext identifier
    fun onDataChange(text: CharSequence, field: Int) {
        viewModelScope.launch {
            dataStreamFlow.emit(Pair(text.toString(), field))
        }
    }

    fun login() {
        _form.value?.let { form ->

            authRepository.login(form).onEach { resource ->
                resource.handleResponse {
                    if (resource.data == null) {
                        setError(context.getString(R.string.error_generic))
                    } else {
                        resource.data.let { user ->
                            _loginSuccess.value = true
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

}