package com.kuanggang.baseframe.data.remote;

import com.kuanggang.baseframe.data.RepositoryContract;
import com.kuanggang.baseframe.network.ApiService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * @author KG on 2017/6/7.
 */

public class RemoteDataSource implements RepositoryContract.RemoteRepository {

    protected Subject<Object> lifecycle = PublishSubject.create().toSerialized();

    /**
     * 根据分类获取gank数据
     */
    @Override
    public void getGankListByCategory(String category, int page, int size, RepositoryContract.GetDataCallback getDataCallback) {
        ApiService.getApi().getGankListByCategory(category, page, size)
                .takeUntil(lifecycle)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(gankCategory -> gankCategory != null && gankCategory.results != null)
                .subscribe(getDataCallback::onDataLoaded,
                        getDataCallback::onDataNotAvailable);
    }

    /**
     * 摧毁时解除绑定
     */
    @Override
    public void onDestory() {
        lifecycle.onNext(null);
        lifecycle.onComplete();
    }
}
