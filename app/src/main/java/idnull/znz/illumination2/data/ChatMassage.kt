package idnull.znz.illumination2.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChatMassage(

    val name:String? =null,
    val text:String? = null,

): Parcelable
