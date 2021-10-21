package com.roche.roche.dis.auth.model

import com.amazonaws.mobile.client.results.UserCodeDeliveryDetails

class UserCodeDeliveryDetails(
    var destination: String? = null,
    var deliveryMedium: String? = null,
    var attributeName: String? = null
) {
    constructor(destination: UserCodeDeliveryDetails?) : this() {
        this.destination = destination?.destination
        this.deliveryMedium = destination?.deliveryMedium
        this.attributeName = destination?.attributeName
    }
}