package cz.danfalta.airbank.model

import android.os.Parcel
import android.os.Parcelable

enum class TransactionDirection : Parcelable {
    INCOMING,
    OUTGOING;

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(ordinal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TransactionDirection> {
        override fun createFromParcel(parcel: Parcel): TransactionDirection {
            return TransactionDirection.values()[parcel.readInt()]
        }

        override fun newArray(size: Int): Array<TransactionDirection?> {
            return arrayOfNulls(size)
        }
    }
}