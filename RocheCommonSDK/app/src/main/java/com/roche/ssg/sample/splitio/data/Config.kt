package com.roche.ssg.sample.splitio.data

import com.google.gson.annotations.SerializedName

data class Config(
    @SerializedName("color")
    val color: String = "",
    @SerializedName("text")
    val text: String = "",
    @SerializedName("textcolor")
    val textcolor: String = ""
)