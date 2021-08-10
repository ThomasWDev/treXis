package com.technologies.mobileexercise.feature.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.technologies.mobileexercise.R
import com.technologies.mobileexercise.core.base.BaseActivity
import com.technologies.mobileexercise.core.base.BaseFragment
import com.technologies.mobileexercise.core.base.BaseViewModel
import com.technologies.mobileexercise.core.extension.observe
import com.technologies.mobileexercise.databinding.FragmentUsersBinding
import com.technologies.mobileexercise.feature.transactions.UserTransactionsFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UsersFragment : BaseFragment<FragmentUsersBinding>() {

    private val viewModel: UsersViewModel by viewModels()

    @Inject
    lateinit var adapter: UsersAdapter

    override val layoutRes: Int
        get() = R.layout.fragment_users

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreated(savedInstance: Bundle?) {
        initViews()
        initObserver()
    }

    override fun onResume() {
        super.onResume()

        (activity as BaseActivity<*>).setToolbar(show = true, title = "Users")
    }


    private fun initViews() {
        binding.usersRvData.adapter = adapter
        binding.usersRvData.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )

        adapter.clickListener = {
            findNavController().navigate(
                R.id.action_usersFragment_to_userTransactionsFragment,
                bundleOf(
                    UserTransactionsFragment.ARGS_USER to gson.toJson(it)
                )
            )
        }
    }

    private fun initObserver() {
        viewModel.apply {
            observe(users) {
                it?.let { adapter.collection = it }
            }
        }
    }
}