package com.android.autelsdk.rxrunnable;

public interface RequestConfig {

    boolean isDiskCache();

    RequestConfig setDiskCache(boolean diskCache);

    void sendRequest();
}
