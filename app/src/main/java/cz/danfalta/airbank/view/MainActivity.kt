package cz.danfalta.airbank.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import cz.danfalta.airbank.R
import cz.danfalta.airbank.databinding.ActivityMainBinding
import cz.danfalta.airbank.model.TransactionDirection
import cz.danfalta.airbank.viewmodel.TransactionListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding, TransactionListViewModel>() {

    override val viewModelClass = TransactionListViewModel::class.java

    private lateinit var filterDialog: MaterialDialog
    private var filterSelection: Int = 0

    companion object {
        const val KEY_STATE_FILTER_SELECTION = "KEY_STATE_FILTER_SELECTION"
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupViews(savedInstanceState)
        observe()

        if (savedInstanceState == null && viewModel.adapter.data.isEmpty())
            viewModel.fetchTransactionList()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(KEY_STATE_FILTER_SELECTION, filterSelection)
    }

    private fun setupViews(savedInstanceState: Bundle?) {
        createFilterDialog()
        savedInstanceState?.let {
            filterSelection = savedInstanceState.getInt(KEY_STATE_FILTER_SELECTION)
        }
    }

    private fun setupBinding() {
        binding.viewmodel = viewModel
    }

    private fun observe() {
        viewModel.clickListener = {
            startActivity(DetailActivity.newIntent(this, it))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_filter -> {
                filterDialog.show()
                return false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createFilterDialog() {
        filterDialog = MaterialDialog(this)
                .listItemsSingleChoice(R.array.filter, initialSelection = filterSelection) { dialog, index, text ->
                    val transactionAdapter = recycler_view.adapter as TransactionAdapter
                    transactionAdapter.filter(
                            when (index) {
                                1 -> TransactionDirection.INCOMING
                                2 -> TransactionDirection.OUTGOING
                                else -> null
                            })
                    filterSelection = index
                }
    }

}
