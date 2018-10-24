package cz.danfalta.airbank.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import cz.danfalta.airbank.R
import cz.danfalta.airbank.databinding.ActivityDetailBinding
import cz.danfalta.airbank.model.Transaction
import cz.danfalta.airbank.viewmodel.TransactionViewModel

class DetailActivity : BaseActivity<ActivityDetailBinding, TransactionViewModel>() {
    override val viewModelClass = TransactionViewModel::class.java

    var transaction: Transaction? = null

    companion object {
        const val INTENT_KEY_TRANSACTION = "DetailActivity.INTENT_KEY_TRANSACTION"

        fun newIntent(context: Context, transaction: Transaction): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(INTENT_KEY_TRANSACTION, transaction)
            return intent
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        transaction = savedInstanceState?.let {
            savedInstanceState.getParcelable<Transaction>(INTENT_KEY_TRANSACTION)
        } ?: intent.getParcelableExtra(INTENT_KEY_TRANSACTION)

        transaction?.let {
            viewModel.fetchTransaction(it.id)
        }

        setupBinding()
        setupViews()
        observe()
    }

    private fun setupViews() {
        transaction?.let {
            binding.transaction = it
        }
    }

    private fun setupBinding() {
        binding.viewmodel = viewModel
    }


    private fun observe() {
        viewModel.contraAccountLiveData.observe(this, Observer { t ->
            //Nothing to observe
        })
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(INTENT_KEY_TRANSACTION, transaction)
    }

}