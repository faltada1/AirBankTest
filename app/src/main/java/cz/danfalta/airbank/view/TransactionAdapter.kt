package cz.danfalta.airbank.view

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import cz.danfalta.airbank.databinding.ItemTransactionBinding
import cz.danfalta.airbank.model.Transaction
import cz.danfalta.airbank.model.TransactionDirection

class TransactionAdapter(private val clickListener: (Transaction) -> Unit) : RecyclerView.Adapter<TransactionViewHolder>() {
    private var filteredData = listOf<Transaction>()
    var data = listOf<Transaction>()
        set(value) {
            //Copy data to filtered data
            filteredData = value
            field = value
        }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TransactionViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        return TransactionViewHolder(ItemTransactionBinding.inflate(layoutInflater, p0, false))
    }

    override fun getItemCount(): Int {
        return filteredData.size
    }

    override fun onBindViewHolder(p0: TransactionViewHolder, p1: Int) {
        p0.bind(filteredData[p1], clickListener)
    }

    fun filter(transactionDirection: TransactionDirection?) {
        filteredData = when (transactionDirection) {
            TransactionDirection.OUTGOING -> data.filter { it.direction == TransactionDirection.OUTGOING }
            TransactionDirection.INCOMING -> data.filter { it.direction == TransactionDirection.INCOMING }
            else -> data
        }
        notifyDataSetChanged()
    }
}