package app.sample.com.product.DataSource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import app.sample.com.product.APIUtils.JSONParser
import app.sample.com.product.APIUtils.NetworkState
import app.sample.com.product.DataModels.PWebService
import app.sample.com.product.Entities.Now
import app.sample.com.product.Entities.Product
import okhttp3.ResponseBody
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Executor

/**
 * Created by arunprasad on 3/14/19.
 */

class ProductDataSource(private val retryExecutor: Executor, private val tmdbWebService: PWebService) : PageKeyedDataSource<Long, Product>() {
    val networkState: MutableLiveData<NetworkState>
    private val initialLoading: MutableLiveData<NetworkState>

    init {
        networkState = MutableLiveData()
        initialLoading = MutableLiveData()
    }

    fun getInitialLoading(): MutableLiveData<*> {
        return initialLoading
    }

    override fun loadInitial(params: PageKeyedDataSource.LoadInitialParams<Long>, callback: PageKeyedDataSource.LoadInitialCallback<Long, Product>) {
        Log.d(TAG, "loadInitial: ")
        initialLoading.postValue(NetworkState.LOADING)
        networkState.postValue(NetworkState.LOADING)
        tmdbWebService.getProducts("https://jl-nonprod-syst.apigee.net/v1/categories/600001506/products?key=2ALHCAAs6ikGRBoy6eTHA58RaG097Fma").enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val responseString: String
                val speciesList: List<Product>
                if (response.isSuccessful && response.code() == 200) {
                    try {
                        initialLoading.postValue(NetworkState.LOADING)
                        networkState.postValue(NetworkState.LOADED)
                        responseString = response.body()!!.string()
                        speciesList = JSONParser?.getSpecieList(responseString)!!.products!!
                        val sList = loadCalculation(speciesList)
                        callback.onResult(sList, null, 2.toLong())
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                } else {
                    Log.e(TAG, "onResponse error " + response.message())
                    initialLoading.postValue(NetworkState(NetworkState.Status.FAILED, response.message()))
                    networkState.postValue(NetworkState(NetworkState.Status.FAILED, response.message()))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                val errorMessage = t.message
                Log.e(TAG, "onFailure: $errorMessage")
                networkState.postValue(NetworkState(NetworkState.Status.FAILED, errorMessage!!))
            }
        })
    }

    private fun loadCalculation(productList: List<Product>): List<Product> {
        for (i in productList.indices) {
            var temp = ""
            try {
                if (productList[i].price?.now?.javaClass == String::class.java) {
                    temp = productList[i].price?.now as String
                } else {
                    val aNow = productList[i].price?.now as Now
                    temp = aNow.to as String
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (temp == "")
                temp = "0"

            val aWas = productList[i].price?.was

            if (aWas != "") {
                val aCalculated = java.lang.Float.parseFloat(aWas) - java.lang.Float.parseFloat(temp)
                productList[i].calculated = aCalculated
            } else {
                productList[i].calculated = 0f
            }
        }
        return productList.sortedByDescending { it.calculated }
    }

    override fun loadBefore(params: PageKeyedDataSource.LoadParams<Long>, callback: PageKeyedDataSource.LoadCallback<Long, Product>) {

    }

    override fun loadAfter(params: PageKeyedDataSource.LoadParams<Long>, callback: PageKeyedDataSource.LoadCallback<Long, Product>) {
    }

    companion object {
        private val TAG = "ProductDataSource"
    }


}

