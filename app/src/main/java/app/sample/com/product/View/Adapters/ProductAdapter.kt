package app.sample.com.product.View.Adapters

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import app.sample.com.product.APIUtils.NetworkState
import app.sample.com.product.Entities.Now
import app.sample.com.product.Entities.Product
import app.sample.com.product.R


/**
 * Created by arunprasad on 3/14/19.
 */
class ProductAdapter(private val mContext: Context) : PagedListAdapter<Product, RecyclerView.ViewHolder>(Product.DIFF_CALL) {
    private var mNetworkState: NetworkState? = null

    val isLoadingData: Boolean
        get() = mNetworkState != null && mNetworkState !== NetworkState.LOADED


    override fun getItemViewType(position: Int): Int {
        return if (isLoadingData && position == itemCount - 1) LOAD_ITEM_VIEW_TYPE else ITEM_VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View
        if (viewType == ITEM_VIEW_TYPE) {
            view = inflater.inflate(R.layout.specie_item_v1, parent, false)
            return ProductViewHolder(view)
        } else {
            view = inflater.inflate(R.layout.load_progress_item, parent, false)
            return ProgressViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductViewHolder) {
            val specie = getItem(position)
            holder.bind(specie, mContext, position)
        }
    }

    fun setNetworkState(networkState: NetworkState) {
        val prevState = networkState
        val wasLoading = isLoadingData
        mNetworkState = networkState
        val willLoad = isLoadingData
        if (wasLoading != willLoad) {
            if (wasLoading)
                notifyItemRemoved(itemCount)
            else
                notifyItemInserted(itemCount)
        }
    }

    private fun loadColor(aProduct: Product): StringBuilder {
        val aBuilder = StringBuilder()
        for (i in 0 until aProduct.colorSwatches?.size!!) {
            if (aProduct.colorSwatches!![i].basicColor != "") {
                var hexColor = ""
                try {
                    val aGivenColr = Color.parseColor(aProduct.colorSwatches!![i].basicColor)
                    hexColor = String.format("#%06X", 0xFFFFFF and aGivenColr)
                    if (aBuilder.toString().equals("")) {
                        aBuilder.append("Color : " + aProduct.colorSwatches!![i].color)
                        aBuilder.append("\nBasic Color : " + aProduct.colorSwatches!![i].basicColor + " RGB : " + hexColor)
                        aBuilder.append("\nsku id : " + aProduct.colorSwatches!![i].skuId)
                    } else {
                        aBuilder.append("\n\nColor : " + aProduct.colorSwatches!![i].color)
                        aBuilder.append("\nBasic Color : " + aProduct.colorSwatches!![i].basicColor + " RGB : " + hexColor)
                        aBuilder.append("\nsku id : " + aProduct.colorSwatches!![i].skuId)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
        return aBuilder
    }

    private fun calculatePrice(product: Product): Float {

        var temp = ""

        try {
            if (product.price?.now?.javaClass == String::class.java) {
                temp = product?.price!!.now as String
            } else {
                val aNow = product.price?.now as Now
                temp = aNow.to as String
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (temp == "")
            temp = "0"


        return java.lang.Float.parseFloat(temp)
    }

    private fun loadPriceLabel(aProduct: Product): StringBuilder {
        val aBuilder = StringBuilder()
        val aPrice = aProduct.price
        if (aPrice?.was != "" && (aPrice?.then1 != "" || aPrice.then2 != "") && aPrice?.now != "") {
            if (aPrice?.then2 != "") {
                aBuilder.append("Was £" + aPrice?.was + ", then £" + aPrice?.then2 + ", now £" + aPrice?.now)
            } else if (aPrice.then1 != "") {
                aBuilder.append("Was £" + aPrice?.was + ", then £" + aPrice?.then1 + ", now £" + aPrice.now)
            } else {
                aBuilder.append("Was £" + aPrice.was + ", then £----" + ", now £" + aPrice.was)
            }
        } else if (aPrice.was != "" && aPrice.now != "") {
            aBuilder.append("Was £" + aPrice.was + ", now £" + aPrice.now)
        }
        return aBuilder
    }

    private class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var nameTXT: TextView
        internal var colorTXT: TextView
        internal var nowPriceTXT: TextView
        internal var priceLabel: TextView
        internal var priceReduction: TextView

        init {
            nameTXT = itemView.findViewById(R.id.product_details)
            colorTXT = itemView.findViewById(R.id.color_swatches)
            nowPriceTXT = itemView.findViewById(R.id.now_price)
            priceReduction = itemView.findViewById(R.id.price_reduction)
            priceLabel = itemView.findViewById(R.id.price_label)
        }

        fun bind(aProduct: Product?, context: Context, dpostion: Int) {
            nameTXT.text = aProduct!!.title + "\n" +
                    aProduct.productId
            val aBuilder = loadColor(aProduct)
            val aPrice = calculatePrice(aProduct)
            colorTXT.text = aBuilder.toString()
            if (aPrice > 10) {
                nowPriceTXT.text = "Now Price : " + aPrice.toInt()
            } else {
                nowPriceTXT.text = "Now Price : $aPrice"
            }
            val aLbel = loadPriceLabel(aProduct)
            priceLabel.text = aLbel.toString()
            priceReduction.text = "Price Reduction : " + aProduct.calculated
        }

    }

    companion object {
        val ITEM_VIEW_TYPE = 1
        val LOAD_ITEM_VIEW_TYPE = 0
        private val TAG = "ProductAdapter"
    }

}
