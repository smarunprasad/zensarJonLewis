package app.sample.com.product.View

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import app.sample.com.product.R
import app.sample.com.product.View.Adapters.ProductAdapter
import app.sample.com.product.ViewModels.ProductViewModel

class MainActivity : AppCompatActivity() {
    private var mProductViewModel: ProductViewModel? = null
    private var mRecyclerView: RecyclerView? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var adapter: ProductAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById(R.id.list)
        mSwipeRefreshLayout = findViewById(R.id.swipe_container)
        adapter = ProductAdapter(this)
        mProductViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java!!)
        mProductViewModel!!.getaProductList().observe(this, Observer { productList ->
            Log.d(TAG, "onChanged: " + productList!!.size)
            adapter!!.submitList(productList)
            mSwipeRefreshLayout!!.isRefreshing = false
        })
        mProductViewModel!!.networkStateLiveData.observe(this, Observer { networkState ->
            Log.d(TAG, "onChanged: network state changed")
            adapter!!.setNetworkState(networkState!!)
        })
        mSwipeRefreshLayout!!.setOnRefreshListener { mProductViewModel!!.refresh() }

        mRecyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRecyclerView!!.adapter = adapter
    }

    companion object {
        private val TAG = "MainActivity"
    }
}
