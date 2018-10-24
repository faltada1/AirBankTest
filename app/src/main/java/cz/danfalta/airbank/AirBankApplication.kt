package cz.danfalta.airbank

import android.app.Application
import android.content.Context
import cz.danfalta.airbank.data.DataFactory
import cz.danfalta.airbank.data.DataService
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

open class AirBankApplication : Application() {

    val dataService: DataService by lazy { DataFactory.create(this) }
    val scheduler: Scheduler by lazy { Schedulers.io() }

    companion object {
        fun get(context: Context): AirBankApplication {
            return context as AirBankApplication
        }
    }

}