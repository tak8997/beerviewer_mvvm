package home.self.beerviewer_mvvm.app_kotlin.rx.rxbus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxEventBus {

    private static RxEventBus instance;
    private final PublishSubject<Object> bus = PublishSubject.create();

    private RxEventBus() {}

    public static RxEventBus getInstance() {
        if (instance == null)
            instance = new RxEventBus();

        return instance;
    }

    public Observable<Object> getBusObservable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }

    public void post(Object o) {
        bus.onNext(o);
    }
}
