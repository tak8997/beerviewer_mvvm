package home.self.beerviewer_mvvm.view.splash;

public interface SplashNavigator {

    void startBeerViewActivity();

    void showFailureMessage(String msg);

    void showSplashAnimation();
}
