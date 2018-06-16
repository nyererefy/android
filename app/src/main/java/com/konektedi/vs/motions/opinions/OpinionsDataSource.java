package com.konektedi.vs.motions.opinions;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.konektedi.vs.utilities.api.Api;
import com.konektedi.vs.utilities.api.ApiUtilities;
import com.konektedi.vs.utilities.NetworkState;
import com.konektedi.vs.utilities.NetworkStatus;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OpinionsDataSource extends PageKeyedDataSource<Integer, Opinions> {
    private static final String TAG = "OpinionsDataSource";

    private Api apiClient;
    private MutableLiveData networkState;
    private MutableLiveData initialLoading;
    private Executor retryExecutor;
    private int motion_id;

    OpinionsDataSource(Executor retryExecutor, int motion_id) {
        apiClient = ApiUtilities.getClient();
        networkState = new MutableLiveData();
        initialLoading = new MutableLiveData();
        this.retryExecutor = retryExecutor;
        this.motion_id = motion_id;
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull final LoadInitialCallback<Integer, Opinions> callback) {

        networkState.postValue(NetworkState.LOADING);

        Call<List<Opinions>> request = apiClient.getOpinions(motion_id, 0);

        request.enqueue(new Callback<List<Opinions>>() {
            @Override
            public void onResponse(Call<List<Opinions>> call, Response<List<Opinions>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    callback.onResult(response.body(), 0, 10);
                    initialLoading.postValue(NetworkState.LOADED);
                    networkState.postValue(NetworkState.LOADED);


                } else {

                    initialLoading.postValue(new NetworkState(NetworkStatus.FAILED, response.message()));
                    networkState.postValue(new NetworkState(NetworkStatus.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<Opinions>> call, Throwable t) {
                String errorMessage;
                errorMessage = t.getMessage();
                if (t == null) {
                    errorMessage = "unknown error";
                }
                networkState.postValue(new NetworkState(NetworkStatus.FAILED, errorMessage));
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
                           @NonNull LoadCallback<Integer, Opinions> callback) {
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull LoadCallback<Integer, Opinions> callback) {

        networkState.postValue(NetworkState.LOADING);

        Call<List<Opinions>> request = apiClient.getOpinions(motion_id, params.key);

        request.enqueue(new Callback<List<Opinions>>() {
            @Override
            public void onResponse(Call<List<Opinions>> call, Response<List<Opinions>> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body(), params.key + 10);
                    networkState.postValue(NetworkState.LOADED);

                } else {
                    networkState.postValue(new NetworkState(NetworkStatus.FAILED, response.message()));

                }
            }

            @Override
            public void onFailure(Call<List<Opinions>> call, Throwable t) {
                String errorMessage;
                errorMessage = t.getMessage();
                if (t == null) {
                    errorMessage = "unknown error";
                }
                networkState.postValue(new NetworkState(NetworkStatus.FAILED, errorMessage));
            }
        });
    }
}
