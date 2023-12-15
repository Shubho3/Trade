package com.nr.nrsales.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RegisterResModel(
    @SerializedName("PriceSets") var PriceSets: ArrayList<String> = arrayListOf(),
    @SerializedName("DefaultPriceSet") var DefaultPriceSet: String? = null,
    @SerializedName("Departments") var Departments: ArrayList<String> = arrayListOf(),
    @SerializedName("Contacts") var Contacts: ArrayList<String> = arrayListOf(),
    @SerializedName("ID") var ID: String? = null,
    @SerializedName("Name") var Name: String? = null,
    @SerializedName("PrimaryContactName") var PrimaryContactName: String? = null,
    @SerializedName("AddressLine1") var AddressLine1: String? = null,
    @SerializedName("AddressLine2") var AddressLine2: String? = null,
    @SerializedName("City") var City: String? = null,
    @SerializedName("State") var State: String? = null,
    @SerializedName("PostalCode") var PostalCode: String? = null,
    @SerializedName("Country") var Country: String? = null,
    @SerializedName("Phone") var Phone: String? = null,
    @SerializedName("Fax") var Fax: String? = null,
    @SerializedName("Email") var Email: String? = null,
    @SerializedName("Website") var Website: String? = null,
    @SerializedName("AccountNumber") var AccountNumber: String? = null,
    @SerializedName("DestinationCategories") var DestinationCategories: String? = null,
    @SerializedName("BillingAdjustmentPercentage") var BillingAdjustmentPercentage: Int? = null,
    @SerializedName("SendInvoiceByEmail") var SendInvoiceByEmail: Boolean? = null,
    @SerializedName("SendInvoiceByPostal") var SendInvoiceByPostal: Boolean? = null,
    @SerializedName("SendInvoiceByFax") var SendInvoiceByFax: Boolean? = null,
    @SerializedName("HasWebPortalAccess") var HasWebPortalAccess: Boolean? = null,
    @SerializedName("BillingEmail") var BillingEmail: String? = null,
    @SerializedName("TimeZone") var TimeZone: String? = null,
    @SerializedName("ReferenceNumber") var ReferenceNumber: String? = null,
    @SerializedName("PurchaseOrderNumber") var PurchaseOrderNumber: String? = null,
    @SerializedName("Comments") var Comments: String? = null,
    @SerializedName("OrderEntryRequiredFields") var OrderEntryRequiredFields: String? = null,
    @SerializedName("Inactive") var Inactive: Boolean? = null,
    @SerializedName("PricingMethod") var PricingMethod: Int? = null
):Serializable
