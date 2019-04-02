package app.sample.com.product.Entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ColorSwatch {

    @SerializedName("color")
    @Expose
    var color: String? = null
    @SerializedName("basicColor")
    @Expose
    var basicColor: String? = null
    @SerializedName("colorSwatchUrl")
    @Expose
    var colorSwatchUrl: String? = null
    @SerializedName("imageUrl")
    @Expose
    var imageUrl: String? = null
    @SerializedName("isAvailable")
    @Expose
    var isAvailable: Boolean? = null
    @SerializedName("skuId")
    @Expose
    var skuId: String? = null

}
