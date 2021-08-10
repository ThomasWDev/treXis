package com.technologies.mobileexercise.feature.transactions

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.technologies.mobileexercise.R
import com.technologies.mobileexercise.core.base.BaseViewModel
import com.technologies.mobileexercise.core.data.api.UserRepository
import com.technologies.mobileexercise.core.data.pojo.Transaction
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
class UserTransactionsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _transactions = MutableLiveData<List<Transaction>>()
    val transactions: LiveData<List<Transaction>> = _transactions

    fun setUser(user: User) {
        _user.value = user
        getUserTransactions(user.id)
    }

    private fun getUserTransactions(id: String) {
        userRepository.getAccountTransactions(id)
            .onEach { resource ->
                resource.handleResponse {
                    if (it.data == null) {
                        setError(context.getString(R.string.error_generic))
                    } else {
                        it.data.let {
                            _transactions.value = it
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }
}