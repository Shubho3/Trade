package com.nr.nrsales.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.ArrayList

class CsvResModel2  : Serializable {

    private var data: ArrayList<Datas>? = null
    private var status: String? = null
    private var message: String? = null

    fun setResult(datas: ArrayList<Datas>?) {
        this.data = datas
    }

    fun getResult(): ArrayList<Datas>? {
        return data
    }

    fun setStatus(status: String?) {
        this.status = status
    }

    fun getStatus(): String? {
        return status
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getMessage(): String? {
        return message
    }

    class Datas : Serializable {
        var id: String? = null
        var HS6_COD: String? = null
        var TAR_PR1: String? = null
        var TAR_PR2: String? = null
        var TARIFF_ALL: String? = null
        var TARIFF_DSC: String? = null
        var SUP_VALUE_1: String? = null
        var SUP_VALUE_2: String? = null
        var IMPORT_DUTY: String? = null
        var EXCISE_TAX: String? = null
        var CSC: String? = null
        var VAT: String? = null
        var IMPORT_ALCO: String? = null






    }

    }