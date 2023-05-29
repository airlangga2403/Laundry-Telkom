package org.d3ifcool.laundrytelkom.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    var title: String ?= "",
    var img: String ?= "",
    var harga: Int = 0
) : Parcelable
