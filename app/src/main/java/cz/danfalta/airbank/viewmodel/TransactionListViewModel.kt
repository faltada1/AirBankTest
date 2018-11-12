package cz.danfalta.airbank.viewmodel

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import cz.danfalta.airbank.AirBankApplication
import cz.danfalta.airbank.data.TransactionListResponse
import cz.danfalta.airbank.model.Transaction
import cz.danfalta.airbank.model.TransactionDirection
import cz.danfalta.airbank.view.TransactionAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class TransactionListViewModel(application: Application) : BaseViewModel(application) {

    var adapter: TransactionAdapter = TransactionAdapter { clickListener?.invoke(it) }
    var clickListener: ((Transaction) -> Unit)? = null

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun fetchTransactionList() {
        val airBankApplication = getApplication() as AirBankApplication
        val dataService = airBankApplication.dataService

        val disposable = dataService.getAllTransactions()
                .subscribeOn(airBankApplication.scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    //Success
                    handleResponse(response)
                }, {
                    //Error
                    handleError(it)
                })

        compositeDisposable.add(disposable)
    }

    private fun handleResponse(response: TransactionListResponse) {
        response.transactionList?.let {
            adapter.data = it
            adapter.notifyDataSetChanged()
        }
    }

    override fun detachView() {
        compositeDisposable.dispose()
    }

    fun filterItems(transactionDirection: TransactionDirection?) {
        adapter.filter(transactionDirection)
    }
}
