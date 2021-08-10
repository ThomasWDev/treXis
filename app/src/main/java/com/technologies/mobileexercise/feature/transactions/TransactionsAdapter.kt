package com.technologies.mobileexercise.feature.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.technologies.mobileexercise.R
import com.technologies.mobileexercise.core.data.pojo.Transaction
import com.technologies.mobileexercise.core.data.pojo.User
import com.technologies.mobileexercise.core.util.AutoUpdatableAdapter
import com.technologies.mobileexercise.databinding.ItemTransactionBinding
import com.technologies.mobileexercise.databinding.ItemUserBinding
import javax.inject.Inject
import kotlin.properties.Delegates

class TransactionsAdapter @Inject constructor() :
    RecyclerView.Adapter<TransactionsAdapter.Holder>(),
    AutoUpdatableAdapter {

    internal var collection: List<Transaction> by Delegates.observable(emptyList()) { prop, old, new ->
        autoNotify(old, new) { o, n -> o.id == n.id }
    }

    internal var clickListener: (Transaction) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder.from(parent, R.layout.item_transaction)

    override fun getItemCount() = collection.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.apply {
            val user = collection[position]
            item = user
            holder.itemView.setOnClickListener {
                clickListener.invoke(user)
            }
            executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class Holder(val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup, layout: Int): Holder {
                val inflater = LayoutInflater.from(parent.context)
                val binding =
                    DataBindingUtil.inflate<ItemTransactionBinding>(
                        inflater, layout,
                        parent, false
                    )
                return Holder(
                    binding
                )
            }
        }
    }
}
