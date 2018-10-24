package cz.danfalta.airbank.data

import com.google.gson.annotations.SerializedName
import cz.danfalta.airbank.model.Transaction

class TransactionListResponse {

    @SerializedName("items")
    var transactionList: List<Transaction>? = null
}