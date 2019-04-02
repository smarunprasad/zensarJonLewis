package app.sample.com.product.Entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PromoMessages {

    @SerializedName("priceMatched")
    @Expose
    var priceMatched: String? = null
    @SerializedName("offer")
    @Expose
    var offer: String? = null
    @SerializedName("customPromotionalMessage")
    @Expose
    var customPromotionalMessage: String? = null
    @SerializedName("bundleHeadline")
    @Expose
    var bundleHeadline: String? = null
    @SerializedName("customSpecialOffer")
    @Expose
    var customSpecialOffer: CustomSpecialOffer? = null

}
