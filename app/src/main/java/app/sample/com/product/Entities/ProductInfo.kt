package app.sample.com.product.Entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProductInfo {

    @SerializedName("products")
    @Expose
    var products: List<Product>? = null

}
