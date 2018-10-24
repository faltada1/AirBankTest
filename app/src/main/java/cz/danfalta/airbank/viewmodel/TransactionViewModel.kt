package cz.danfalta.airbank.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import cz.danfalta.airbank.AirBankApplication
import cz.danfalta.airbank.model.ContraAccount
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class TransactionViewModel(application: Application) : BaseViewModel(application) {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    var contraAccountLiveData: MutableLiveData<ContraAccount> = MutableLiveData()

    var visibilityObservableField = ObservableField<Int>()
    var accountNameObservableField = ObservableField<String>()
    var accountNumberObservableField = ObservableField<String>()
    var bankCodeObservableField = ObservableField<String>()

    init {
        visibilityObservableField.set(View.GONE)
    }

    fun fetchTransaction(id: Int) {
        val airBankApplication = getApplication() as AirBankApplication
        val dataService = airBankApplication.dataService

        val disposable = dataService.getTransactionDetail(id)
                .subscribeOn(airBankApplication.scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //Success
                    handleSuccess(it.contraAccount)
                }, {
                    //Error
                    handleError(it)
                })

        compositeDisposable.add(disposable)
    }

    private fun handleSuccess(contraAccount: ContraAccount?) {
        visibilityObservableField.set(View.VISIBLE)
        contraAccount?.let {
            accountNameObservableField.set(contraAccount.accountName)
            accountNumberObservableField.set(contraAccount.accountNumber)
            bankCodeObservableField.set(contraAccount.bankCode)
        }
        //Let Observer know about changes
        contraAccountLiveData.value = contraAccount
    }

    override fun handleError(it: Throwable) {
        super.handleError(it)
        visibilityObservableField.set(View.GONE)
        contraAccountLiveData.value = null
    }

    override fun detachView() {
        compositeDisposable.dispose()
    }
}
