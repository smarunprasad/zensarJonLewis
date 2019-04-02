package app.sample.com.product.APIUtils

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

import org.json.JSONException

import app.sample.com.product.Entities.ProductInfo

/**
 * Created by arunprasad on 3/14/19.
 */

object JSONParser {

    @Throws(JSONException::class)
    fun getSpecieList(jsonString: String): ProductInfo? {
        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
        return gson.fromJson<ProductInfo>(jsonString, object : TypeToken<ProductInfo>() {

        }.type)
    }
}
