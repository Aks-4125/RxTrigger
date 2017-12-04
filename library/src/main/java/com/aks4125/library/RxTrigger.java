package com.aks4125.library;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by akashb on 14-11-2017.
 */
public class RxTrigger {

    private PublishSubject<Object> mRxTrigger = PublishSubject.create();

    public RxTrigger() {
        // required
    }

    public void send(Object o) {
        mRxTrigger.onNext(o);
    }

    public Observable<Object> toObservable() {
        return mRxTrigger;
    }

    public boolean hasObservers() {
        return mRxTrigger.hasObservers();
    }
}