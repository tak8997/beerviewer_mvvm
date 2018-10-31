package home.self.beerviewer_mvvm.view.beerdetail;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import home.self.beerviewer_mvvm.R;
import home.self.beerviewer_mvvm.databinding.ActivityBeerDetailBinding;

public class BeerDetailActivity extends DaggerAppCompatActivity implements BeerDetailNavigator {

    @Inject
    BeerDetailViewModel viewModel;

    @Inject
    int beerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityBeerDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_beer_detail);
        binding.setViewModel(viewModel);

        viewModel.takeView(this);
        viewModel.subscribe();
    }

    @Override
    public void showFailureMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showShareDialog(String beerInfo) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, beerInfo);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, getResources().getText(R.string.send_to)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.unsubscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                viewModel.processBeerContent();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
