package com.technologies.mobileexercise.feature.transactions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.technologies.mobileexercise.R
import com.technologies.mobileexercise.core.base.BaseActivity
import com.technologies.mobileexercise.core.base.BaseFragment
import com.technologies.mobileexercise.core.base.BaseViewModel
import com.technologies.mobileexercise.core.data.pojo.User
import com.technologies.mobileexercise.core.extension.observe
import com.technologies.mobileexercise.databinding.FragmentUserTransactionsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserTransactionsFragment : BaseFragment<FragmentUserTransactionsBinding>() {

    private val viewModel: UserTransactionsViewModel by viewModels()

    @Inject
    lateinit var adapter: TransactionsAdapter

    override val layoutRes: Int
        get() = R.layout.fragment_user_transactions

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreated(savedInstance: Bundle?) {
        initViews()
        checkArgs()
        initObserver()
    }

    private fun initViews() {
        binding.transactionsRvData.adapter = adapter
        binding.transactionsRvData.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )
        adapter.clickListener = {

        }
    }

    private fun checkArgs() {
        arguments?.let {
            if (it.containsKey(ARGS_USER)) {
                viewModel.setUser(gson.fromJson(it.getString(ARGS_USER, ""), User::class.java))
            }
        }
    }

    private fun initObserver() {
        viewModel.apply {
            observe(user) {
                it?.let {
                    (activity as BaseActivity<*>).setToolbar(
                        show = true, showBackButton = true, title = it.name
                    )
                }
            }

            observe(transactions){
                it?.let { adapter.collection = it }
            }
        }
    }

    companion object {
        const val ARGS_USER = "_args_user"
    }
}