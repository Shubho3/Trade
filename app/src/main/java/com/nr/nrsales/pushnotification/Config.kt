package com.nr.nrsales.pushnotification

object Config {
    val TOPIC_GLOBAL = "global"
    // broadcast receiver intent filters
    val REGISTRATION_COMPLETE = "registrationComplete"
    val PUSH_NOTIFICATION = "pushNotification"
    val NO_CARS = "noCarsFound"
    val NETWORK_CHANGES = "networkChanges"
    val CANCEL_NOTIFICATION = "cancelNotification"
    val NO_PROVIDERS = "noProvidersFound"

    // id to handle the notification in the notification tray
    val NOTIFICATION_ID = 100
    val NOTIFICATION_ID_BIG_IMAGE = 101

    val SHARED_PREF = "ah_firebase"


}
