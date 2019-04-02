package app.sample.com.product.Entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DynamicAttributes {

    @SerializedName("newinproducttype")
    @Expose
    var newinproducttype: String? = null
    @SerializedName("washinginstructions")
    @Expose
    var washinginstructions: String? = null
    @SerializedName("dressbyoccasion")
    @Expose
    var dressbyoccasion: String? = null
    @SerializedName("dressshape")
    @Expose
    var dressshape: String? = null
    @SerializedName("typeofpattern")
    @Expose
    var typeofpattern: String? = null

}
