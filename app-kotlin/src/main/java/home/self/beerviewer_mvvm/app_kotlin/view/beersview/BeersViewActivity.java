package home.self.beerviewer_mvvm.app_kotlin.view.beersview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import home.self.beerviewer_mvvm.app_kotlin.R;

public class BeersViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beers_view);
    }
}
