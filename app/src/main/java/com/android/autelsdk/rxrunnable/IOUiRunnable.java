package com.android.autelsdk.rxrunnable;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class IOUiRunnable<T> extends RxRunnable<T> {
    protected Scheduler provideProducerOn() {
        return Schedulers.io();
    }

    protected Scheduler provideCustomerOn() {
        return AndroidSchedulers.mainThread();
    }
}
