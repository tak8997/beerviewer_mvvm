package home.self.beerviewer_mvvm.view.splash;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import home.self.beerviewer_mvvm.R;
import home.self.beerviewer_mvvm.view.beersview.BeersViewActivity;
import home.self.beerviewer_mvvm.data.source.BeerRepository;
import home.self.beerviewer_mvvm.data.source.local.BeerDatabase;
import home.self.beerviewer_mvvm.data.source.local.BeerLocalDataSource;
import home.self.beerviewer_mvvm.data.source.remote.BeerRemoteDataSource;
import home.self.beerviewer_mvvm.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity implements SplashNavigator {

    private SplashViewModel splashViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        splashViewModel = new SplashViewModel(this, new BeerRepository(new BeerRemoteDataSource(), new BeerLocalDataSource()));
        binding.setViewModel(splashViewModel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        splashViewModel.getBeers();
    }

    @Override
    public void showFailureMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSplashAnimation() {
        final ImageView imgSplash = findViewById(R.id.beer_animation);
        Glide.with(this)
                .asGif()
                .load(R.raw.beer_splash)
                .into(imgSplash);
    }

    @Override
    public void startBeerViewActivity() {
        Intent intent = new Intent(SplashActivity.this, BeersViewActivity.class);
        startActivity(intent);
        finish();
    }
}
