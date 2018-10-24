package cz.danfalta.airbank.view

import androidx.recyclerview.widget.RecyclerView
import cz.danfalta.airbank.BR
import cz.danfalta.airbank.databinding.ItemTransactionBinding
import cz.danfalta.airbank.model.Transaction

class TransactionViewHolder(viewDataBinding: ItemTransactionBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {

    private val binding = viewDataBinding

    fun bind(transaction: Transaction, clickListener: ((Transaction) -> Unit)? = null) {
        binding.setVariable(BR.transaction, transaction)
        binding.executePendingBindings()
        itemView.setOnClickListener { clickListener?.let { it1 -> it1(transaction) } }
    }
}