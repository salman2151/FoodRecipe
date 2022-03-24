package com.murata.foodrecipeapp.network.dto

import android.os.Parcel
import android.os.Parcelable

data class DtoTag(
    val id: Int,
    val type: String?,
    val name: String?,
    val display_name: String?
) : Parcelable {
    var isChecked = false

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
        isChecked = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(type)
        parcel.writeString(name)
        parcel.writeString(display_name)
        parcel.writeByte(if (isChecked) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DtoTag> {
        override fun createFromParcel(parcel: Parcel): DtoTag {
            return DtoTag(parcel)
        }

        override fun newArray(size: Int): Array<DtoTag?> {
            return arrayOfNulls(size)
        }
    }
}