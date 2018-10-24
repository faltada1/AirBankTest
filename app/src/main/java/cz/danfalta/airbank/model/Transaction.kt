package cz.danfalta.airbank.model

import android.os.Parcel
import android.os.Parcelable
import java.math.BigDecimal

data class Transaction(var id: Int, var amountInAccountCurrency: BigDecimal, var direction: TransactionDirection) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readSerializable() as BigDecimal,
            TransactionDirection.values()[parcel.readInt()])

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeSerializable(amountInAccountCurrency)
        parcel.writeInt(direction.ordinal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Transaction> {
        override fun createFromParcel(parcel: Parcel): Transaction {
            return Transaction(parcel)
        }

        override fun newArray(size: Int): Array<Transaction?> {
            return arrayOfNulls(size)
        }
    }
}