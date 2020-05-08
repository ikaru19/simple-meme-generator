package com.ikaru.algostudiotest.Models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class DataResponse {
    @SerializedName("success")
    @Expose
    private var success: Boolean? = null
    @SerializedName("data")
    @Expose
    private var data: Data? = null

    fun getSuccess(): Boolean? {
        return success
    }

    fun setSuccess(success: Boolean?) {
        this.success = success
    }

    fun getData(): Data? {
        return data
    }

    fun setData(data: Data?) {
        this.data = data
    }

}