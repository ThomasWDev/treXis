package com.technologies.mobileexercise.feature.users

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.technologies.mobileexercise.R
import com.technologies.mobileexercise.core.base.BaseViewModel
import com.technologies.mobileexercise.core.data.api.UserRepository
import com.technologies.mobileexercise.core.data.pojo.User
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@HiltViewModel
class UsersViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    init {
        getAccounts()
    }

    private fun getAccounts() {
        userRepository.getAccounts()
            .onEach { resource ->
                resource.handleResponse {
                    if (it.data == null) {
                        setError(context.getString(R.string.error_generic))
                    } else {
                        it.data.let { users ->
                            _users.value = users
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

}