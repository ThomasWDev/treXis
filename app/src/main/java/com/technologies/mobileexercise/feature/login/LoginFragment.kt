package com.technologies.mobileexercise.feature.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.technologies.mobileexercise.R
import com.technologies.mobileexercise.core.base.BaseActivity
import com.technologies.mobileexercise.core.base.BaseFragment
import com.technologies.mobileexercise.core.base.BaseViewModel
import com.technologies.mobileexercise.core.extension.observe
import com.technologies.mobileexercise.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    override val layoutRes: Int
        get() = R.layout.fragment_login

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreated(savedInstance: Bundle?) {
        initBinding()
        initObservers()
    }

    override fun onResume() {
        super.onResume()

        (activity as BaseActivity<*>).setToolbar(show = true, title = "Login")
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }


    private fun initObservers() {
        viewModel.apply {
            observe(loginSuccess) {
                if (it == true) {
                    findNavController().navigate(R.id.action_loginFragment_to_usersFragment)
                    setLoginSuccess(false)
                }
            }
        }
    }
}