package cz.danfalta.airbank.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.library.baseAdapters.BR
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.getListAdapter
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import cz.danfalta.airbank.R
import cz.danfalta.airbank.databinding.ActivityMainBinding
import cz.danfalta.airbank.model.TransactionDirection
import cz.danfalta.airbank.viewmodel.TransactionListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding, TransactionListViewModel>() {
    override val layoutId = R.layout.activity_main
    override val viewModelClass = TransactionListViewModel::class.java
    override val viewModelBindingVariable = BR.viewmodel

    private lateinit var filterDialog: MaterialDialog
    private var filterSelection: Int = 0

    companion object {
        const val KEY_STATE_FILTER_SELECTION = "KEY_STATE_FILTER_SELECTION"
        const val KEY_STATE_FILTER_OPEN = "KEY_STATE_FILTER_OPEN"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews(savedInstanceState)
        observe()

        if (savedInstanceState == null && viewModel.adapter.data.isEmpty())
            viewModel.fetchTransactionList()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(KEY_STATE_FILTER_SELECTION, filterSelection)
        outState?.putBoolean(KEY_STATE_FILTER_OPEN, filterDialog.isShowing)
    }

    private fun setupViews(savedInstanceState: Bundle?) {
        createFilterDialog(savedInstanceState?.getInt(KEY_STATE_FILTER_SELECTION) ?: 0)
        val isFilterShowing = savedInstanceState?.getBoolean(KEY_STATE_FILTER_OPEN) ?: false
        if (isFilterShowing) filterDialog.show()
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

    private fun createFilterDialog(selection: Int) {
        filterSelection = selection
        filterDialog = MaterialDialog(this)
                .listItemsSingleChoice(R.array.filter, initialSelection = selection) { dialog, index, text ->
                    viewModel.filterItems(when (index) {
                        1 -> TransactionDirection.INCOMING
                        2 -> TransactionDirection.OUTGOING
                        else -> null
                    })
                    filterSelection = index
                }
    }

}
