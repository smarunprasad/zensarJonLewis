package app.sample.com.product.ViewModels;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import app.sample.com.product.APIUtils.NetworkState;
import app.sample.com.product.APIUtils.ServiceGenerator;
import app.sample.com.product.DataModels.PWebService;
import app.sample.com.product.DataSource.ProductDataSource;
import app.sample.com.product.DataSource.ProductDataSourceFactory;
import app.sample.com.product.Entities.Product;


/**
 * Created by arunprasad on 3/14/19.
 */

public class ProductViewModel extends ViewModel {
    private static final String TAG = "ProductViewModel";
    private LiveData<PagedList<Product>> aProductList;
    private LiveData<NetworkState> networkStateLiveData;
    private Executor executor;
    private LiveData<ProductDataSource> dataSource;

    public ProductViewModel() {
        Log.d(TAG, "ProductViewModel: ");
        executor = Executors.newFixedThreadPool(5);
        PWebService webService = ServiceGenerator.INSTANCE.createService(PWebService.class);
        ProductDataSourceFactory factory = new ProductDataSourceFactory(executor, webService);
        dataSource = factory.getMutableLiveData();
        networkStateLiveData = Transformations.switchMap(factory.getMutableLiveData(), new Function<ProductDataSource, LiveData<NetworkState>>() {
            @Override
            public LiveData<NetworkState> apply(ProductDataSource source) {
                Log.d(TAG, "apply: network change");
                return source.getNetworkState();
            }
        });
        PagedList.Config pageConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(1)
                .setPageSize(1).build();
        aProductList = (new LivePagedListBuilder<Long, Product>(factory, pageConfig))
                .setBackgroundThreadExecutor(executor)
                .build();

    }

    public LiveData<PagedList<Product>> getaProductList() {
        Log.d(TAG, "getaProductList: ");
        return aProductList;
    }

    public void refresh() {
        dataSource.getValue().invalidate();
    }

    public LiveData<NetworkState> getNetworkStateLiveData() {
        return networkStateLiveData;
    }
}

