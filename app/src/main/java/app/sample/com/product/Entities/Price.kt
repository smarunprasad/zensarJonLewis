package app.sample.com.product.Entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Price {

    @SerializedName("was")
    @Expose
    var was: String? = null
    @SerializedName("then1")
    @Expose
    var then1: String? = null
    @SerializedName("then2")
    @Expose
    var then2: String? = null
    @SerializedName("now")
    @Expose
    var now: Any? = null
    @SerializedName("uom")
    @Expose
    var uom: String? = null
    @SerializedName("currency")
    @Expose
    var currency: String? = null

}
