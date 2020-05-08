package com.ikaru.algostudiotest.Models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Meme (
    var id: String? = null,
    var name: String? = null,
    var url: String? = null,
    var width: Int? = null,
    var height: Int? = null,
    var boxCount: Int? = null
    ): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(url)
        parcel.writeValue(width)
        parcel.writeValue(height)
        parcel.writeValue(boxCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Meme> {
        override fun createFromParcel(parcel: Parcel): Meme {
            return Meme(parcel)
        }

        override fun newArray(size: Int): Array<Meme?> {
            return arrayOfNulls(size)
        }
    }

}