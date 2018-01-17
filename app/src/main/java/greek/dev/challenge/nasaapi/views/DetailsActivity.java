package greek.dev.challenge.nasaapi.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import greek.dev.challenge.nasaapi.R;
import greek.dev.challenge.nasaapi.model.MyNasaItem;

/**
 * Created by programbench on 1/14/2018.
 */

public class DetailsActivity extends AppCompatActivity {
    @BindView(R.id.title_photo)
    public TextView title;
    @BindView(R.id.image_photo)
    public ImageView image;
    @BindView(R.id.description_photo)
    public TextView description;
    private MyNasaItem selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intentStartedThisActivity = getIntent();
        selectedItem = intentStartedThisActivity.getExtras().getParcelable("item");
        setData(selectedItem);
    }

    private void setData(MyNasaItem selectedItem) {
        title.setText(selectedItem.getTitle());
        description.setText(selectedItem.getDescription());
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .dontTransform()
                .placeholder(R.drawable.ic_public_black_24dp)
                .error(R.mipmap.ic_launcher_round);
        Glide.with(image.getContext()).load(selectedItem.getImageLink()).apply(options).into(image);

    }
}
