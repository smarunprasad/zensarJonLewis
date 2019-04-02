package app.sample.com.product.DataModels

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by arunprasad on 3/14/19.
 */

interface PWebService {

    /**
     * Get a list of species.
     */
    @GET
    fun getProducts(@Url aUrl: String): Call<ResponseBody>
}
