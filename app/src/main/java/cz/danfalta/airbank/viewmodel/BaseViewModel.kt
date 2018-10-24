package cz.danfalta.airbank.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    open fun handleError(it: Throwable) {
        //Global handle error
    }

    abstract fun detachView()

}