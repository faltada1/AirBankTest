package cz.danfalta.airbank.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import cz.danfalta.airbank.BR
import cz.danfalta.airbank.R
import cz.danfalta.airbank.databinding.ActivityDetailBinding
import cz.danfalta.airbank.model.Transaction
import cz.danfalta.airbank.viewmodel.TransactionViewModel

class DetailActivity : BaseActivity<ActivityDetailBinding, TransactionViewModel>() {
    override val viewModelClass = TransactionViewModel::class.java
    override val viewModelBindingVariable = BR.transactionViewModel
    override val layoutId = R.layout.activity_detail

    companion object {
        const val INTENT_KEY_TRANSACTION = "DetailActivity.INTENT_KEY_TRANSACTION"

        fun newIntent(context: Context, transaction: Transaction): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(INTENT_KEY_TRANSACTION, transaction)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transaction: Transaction = intent.getParcelableExtra(INTENT_KEY_TRANSACTION)
        transaction.let {
            viewModel.transaction = it
            viewModel.fetchTransaction(it.id)
        }
    }
}