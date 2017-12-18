package com.agura.com.spotify.Model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by SmartStart on 12/16/17.
 */
data class SongList(
        val song_title: String = " ",
        val song_artist: String = " ",
        val song_album: String = " ",
        val stat: Boolean = false
            ):Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(song_title)
        parcel.writeString(song_artist)
        parcel.writeString(song_album)
        parcel.writeByte(if (stat) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SongList> {
        override fun createFromParcel(parcel: Parcel): SongList {
            return SongList(parcel)
        }

        override fun newArray(size: Int): Array<SongList?> {
            return arrayOfNulls(size)
        }
    }

}