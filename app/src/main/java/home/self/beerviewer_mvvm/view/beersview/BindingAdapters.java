package home.self.beerviewer_mvvm.view.beersview;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

public class BindingAdapters {

    @BindingAdapter({"imageUrl"})
    public static void loadImage(final ImageView imageView, final String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .apply(new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);

//        Glide.with(imageView.getContext())
//                .load(imageUrl)
//                .asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                // 이미지를 동그랗게 오려낸다
//                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(imageView.getResources(), resource);
//                circularBitmapDrawable.setCircular(true);
//                imageView.setImageDrawable(circularBitmapDrawable);
//            }
//        });
    }
}
