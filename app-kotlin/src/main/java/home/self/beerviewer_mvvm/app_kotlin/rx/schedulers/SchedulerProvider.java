package home.self.beerviewer_mvvm.app_kotlin.rx.schedulers;

import androidx.annotation.NonNull;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SchedulerProvider implements BaseSchedulerProvider {

//    @Nullable
//    private static SchedulerProvider instance;

    public SchedulerProvider() {}

//    public static synchronized SchedulerProvider getInstance() {
//        if (instance == null)
//            instance = new SchedulerProvider();
//
//        return instance;
//    }

    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
