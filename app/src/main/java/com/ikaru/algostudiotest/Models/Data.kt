package com.ikaru.algostudiotest.Models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Data {
    @SerializedName("memes")
    @Expose
    var memes: List<Meme>? = null
}