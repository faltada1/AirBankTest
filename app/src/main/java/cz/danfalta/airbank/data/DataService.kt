package cz.danfalta.airbank.data

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface DataService {

    @GET("/transactions")
    fun getAllTransactions(): Observable<TransactionListResponse>

    @GET("/transactions/{id}")
    fun getTransactionDetail(@Path("id") id: Int): Observable<ContraAccountResponse>

}
