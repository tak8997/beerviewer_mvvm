package home.self.beerviewer_mvvm.view.splash;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import home.self.beerviewer_mvvm.R;
import home.self.beerviewer_mvvm.databinding.ActivitySplashBinding;
import home.self.beerviewer_mvvm.view.beersview.BeersViewActivity;

public class SplashActivity extends DaggerAppCompatActivity implements SplashNavigator {

    @Inject
    SplashViewModel splashViewModel;

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        binding.setViewModel(splashViewModel);

        splashViewModel.takeView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        splashViewModel.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        splashViewModel.unsubscribe();
    }

    @Override
    public void showFailureMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSplashAnimation() {
        Glide.with(this)
                .asGif()
                .load(R.raw.beer_splash)
                .into(binding.beerAnimation);
    }

    @Override
    public void startBeerViewActivity() {
        Intent intent = new Intent(SplashActivity.this, BeersViewActivity.class);
        startActivity(intent);
        finish();
    }
}
