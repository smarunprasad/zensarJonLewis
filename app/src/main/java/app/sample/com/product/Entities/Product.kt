package app.sample.com.product.Entities

import android.support.v7.util.DiffUtil

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Product {
    @SerializedName("productId")
    @Expose
    var productId: String? = null
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("code")
    @Expose
    var code: String? = null
    @SerializedName("averageRating")
    @Expose
    var averageRating: Double? = null
    @SerializedName("reviews")
    @Expose
    var reviews: Int? = null
    @SerializedName("price")
    @Expose
    var price: Price? = null
    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("additionalServices")
    @Expose
    var additionalServices: List<Any>? = null
    @SerializedName("displaySpecialOffer")
    @Expose
    var displaySpecialOffer: String? = null
    @SerializedName("promoMessages")
    @Expose
    var promoMessages: PromoMessages? = null
    @SerializedName("nonPromoMessage")
    @Expose
    var nonPromoMessage: String? = null
    @SerializedName("defaultSkuId")
    @Expose
    var defaultSkuId: String? = null
    @SerializedName("colorSwatches")
    @Expose
    var colorSwatches: List<ColorSwatch>? = null
    @SerializedName("colorSwatchSelected")
    @Expose
    var colorSwatchSelected: Int? = null
    @SerializedName("colorWheelMessage")
    @Expose
    var colorWheelMessage: String? = null
    @SerializedName("outOfStock")
    @Expose
    var outOfStock: Boolean? = null
    @SerializedName("emailMeWhenAvailable")
    @Expose
    var emailMeWhenAvailable: Boolean? = null
    @SerializedName("availabilityMessage")
    @Expose
    var availabilityMessage: String? = null
    @SerializedName("compare")
    @Expose
    var compare: Boolean? = null
    @SerializedName("fabric")
    @Expose
    var fabric: String? = null
    @SerializedName("swatchAvailable")
    @Expose
    var swatchAvailable: Boolean? = null
    @SerializedName("categoryQuickViewEnabled")
    @Expose
    var categoryQuickViewEnabled: Boolean? = null
    @SerializedName("swatchCategoryType")
    @Expose
    var swatchCategoryType: String? = null
    @SerializedName("brand")
    @Expose
    var brand: String? = null
    @SerializedName("ageRestriction")
    @Expose
    var ageRestriction: Int? = null
    @SerializedName("isInStoreOnly")
    @Expose
    var isInStoreOnly: Boolean? = null
    @SerializedName("isMadeToMeasure")
    @Expose
    var isMadeToMeasure: Boolean? = null
    @SerializedName("isBundle")
    @Expose
    var isBundle: Boolean? = null
    @SerializedName("isProductSet")
    @Expose
    var isProductSet: Boolean? = null
    @SerializedName("promotionalFeatures")
    @Expose
    var promotionalFeatures: List<Any>? = null
    @SerializedName("features")
    @Expose
    var features: List<Any>? = null
    @SerializedName("quickAddToBasket")
    @Expose
    var quickAddToBasket: QuickAddToBasket? = null
    @SerializedName("dynamicAttributes")
    @Expose
    var dynamicAttributes: DynamicAttributes? = null
    @SerializedName("directorate")
    @Expose
    var directorate: String? = null
    @SerializedName("releaseDateTimestamp")
    @Expose
    var releaseDateTimestamp: Int? = null

    var calculated = 0f

    companion object {

        val DIFF_CALL: DiffUtil.ItemCallback<Product> = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.productId === newItem.productId
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.productId === newItem.productId
            }
        }
    }

}
