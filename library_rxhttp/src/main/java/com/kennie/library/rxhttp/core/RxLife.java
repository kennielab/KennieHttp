package com.kennie.library.rxhttp.core;


import androidx.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 描述：
 *
 */
public class RxLife {

    private CompositeDisposable mCompositeDisposable = null;

    private RxLife() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @NonNull
    public static RxLife create() {
        return new RxLife();
    }

    public void destroy() {
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            return;
        }
        mCompositeDisposable.dispose();
    }

    public void add(Disposable d) {
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(d);
    }
}
