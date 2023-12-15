package com.nr.nrsales.model

import java.io.Serializable

class OrderModel : Serializable {


    constructor(
        ID: String?,
        TrackingNumber: String?,
        CurrentStatus: String?,
        DateSubmitted: String?,
        RequestedBy: String?,
        TotalCost: Double?,
        Description: String?,
        Weight: Double?,
        CollectionLocation: String?,
        DeliveryLocation: String?,
        Distance: Double?
    ) {
        this.ID = ID
        this.TrackingNumber = TrackingNumber
        this.CurrentStatus = CurrentStatus
        this.DateSubmitted = DateSubmitted
        this.RequestedBy = RequestedBy
        this.TotalCost = TotalCost
        this.Description = Description
        this.Weight = Weight
        this.CollectionLocation = CollectionLocation
        this.DeliveryLocation = DeliveryLocation
        this.Distance = Distance
    }

    var ID: String? = null
    var TrackingNumber: String? = null
    var CurrentStatus: String? = null
    var DateSubmitted: String? = null
    var RequestedBy: String? = null
    var TotalCost: Double? = null
    var Description: String? = null
    var Weight: Double? = null
    var CollectionLocation: String? = null
    var DeliveryLocation: String? = null
    var Distance: Double? = null


}