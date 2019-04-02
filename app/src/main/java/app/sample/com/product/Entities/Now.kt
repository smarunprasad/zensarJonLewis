package app.sample.com.product.Entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Now {

    @SerializedName("from")
    @Expose
    var from: String? = null
    @SerializedName("to")
    @Expose
    var to: String? = null

}
