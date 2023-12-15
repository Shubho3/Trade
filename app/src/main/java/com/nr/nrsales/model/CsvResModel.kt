package com.nr.nrsales.model

import com.google.gson.annotations.SerializedName

data class CsvResModel(@SerializedName("data") var data: ArrayList<Csv> = arrayListOf()) {
    data class Csv(
        @SerializedName("id") var id: String? = null,
        @SerializedName("HS6_COD") var HS6COD: String? = null,
        @SerializedName("TAR_PR1") var TARPR1: String? = null,
        @SerializedName("TAR_PR2") var TARPR2: String? = null,
        @SerializedName("TARIFF_ALL") var TARIFFALL: String? = null,
        @SerializedName("TARIFF_DSC") var TARIFFDSC: String? = null,
        @SerializedName("SUP_VALUE_1") var SUPVALUE1: String? = null,
        @SerializedName("SUP_VALUE_2") var SUPVALUE2: String? = null,
        @SerializedName("IMPORT_DUTY") var IMPORTDUTY: String? = null,
        @SerializedName("EXCISE_TAX") var EXCISETAX: String? = null,
        @SerializedName("CSC") var CSC: String? = null,
        @SerializedName("VAT") var VAT: String? = null,
        @SerializedName("IMPORT_ALCO") var IMPORTALCO: String? = null
    )
}
