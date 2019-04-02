package app.sample.com.product.DataSource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.util.Log;

import java.util.concurrent.Executor;

import app.sample.com.product.DataModels.PWebService;

/**
 * Created by arunprasad on 3/14/19.
 */

public class ProductDataSourceFactory extends DataSource.Factory {
    private static final String TAG = "productDataSource";
    ProductDataSource speciesDataSource;
    MutableLiveData<ProductDataSource> mutableLiveData;
    Executor executor;
    PWebService webService;

    public ProductDataSourceFactory(Executor executor, PWebService webService) {
        this.executor = executor;
        this.webService = webService;
        mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        Log.d(TAG, "create: ");
        speciesDataSource = new ProductDataSource(executor, webService);
        mutableLiveData.postValue(speciesDataSource);
        return speciesDataSource;
    }

    public MutableLiveData<ProductDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
