package home.self.beerviewer_mvvm.view.beerdetail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import home.self.beerviewer_mvvm.Constant;
import home.self.beerviewer_mvvm.R;
import home.self.beerviewer_mvvm.data.model.BeerModel;
import home.self.beerviewer_mvvm.data.source.BeerRepository;
import home.self.beerviewer_mvvm.data.source.local.BeerLocalDataSource;
import home.self.beerviewer_mvvm.data.source.remote.BeerRemoteDataSource;
import home.self.beerviewer_mvvm.databinding.ActivityBeerDetailBinding;

public class BeerDetailActivity extends AppCompatActivity implements BeerDetailNavigator {

    private BeerDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int beerId = getIntent().getIntExtra(Constant.KEY_BEAR_ID, -1);

        ActivityBeerDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_beer_detail);
        viewModel = new BeerDetailViewModel(new BeerRepository(new BeerRemoteDataSource(), new BeerLocalDataSource()), this);
        binding.setViewModel(viewModel);

        viewModel.getBeer(beerId);
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
