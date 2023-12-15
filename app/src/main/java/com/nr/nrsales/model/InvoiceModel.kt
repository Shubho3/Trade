package com.nr.nrsales.model

import java.io.Serializable
import java.util.ArrayList

class InvoiceModel : Serializable {


    constructor(
        Customer: String?,
        ID: String?,
        InvoiceNumber: String?,
        Date: String?,
        DueDate: String?,
        IsPaid: Boolean?,
        Memo: String?,
        TransferredToQuickBooksDesktop: Boolean?,
        TotalAmount: Double?
    ) {
        this.Customer = Customer
        this.ID = ID
        this.InvoiceNumber = InvoiceNumber
        this.Date = Date
        this.DueDate = DueDate
        this.IsPaid = IsPaid
        this.Memo = Memo
        this.TransferredToQuickBooksDesktop = TransferredToQuickBooksDesktop
        this.TotalAmount = TotalAmount
    }

        var Customer: String? = null
        var ID: String? = null
        var InvoiceNumber: String? = null
        var Date: String? = null
        var DueDate: String? = null
        var IsPaid: Boolean? = null
        var Memo: String? = null
        var TransferredToQuickBooksDesktop: Boolean? = null
        var TotalAmount: Double? = null




}