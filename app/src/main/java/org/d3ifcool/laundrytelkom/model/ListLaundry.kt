package org.d3ifcool.laundrytelkom.model

import com.squareup.moshi.Json

data class ListLaundry (
    @Json(name = "list_data") val data: List<Laundry>
)