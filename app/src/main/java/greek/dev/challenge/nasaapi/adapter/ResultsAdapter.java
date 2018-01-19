package greek.dev.challenge.nasaapi.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import greek.dev.challenge.nasaapi.R;
import greek.dev.challenge.nasaapi.model.MyNasaItem;

/**
 * Created by programbench on 1/14/2018.
 */

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultsHolder> {
    private final NasaResultsAdapterOnClickHandler mClickHandler;
    private List<MyNasaItem> nasaItemsResults;

    public ResultsAdapter(NasaResultsAdapterOnClickHandler handler) {
        mClickHandler = handler;
    }

    @Override
    public ResultsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item, parent, false);

        return new ResultsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ResultsHolder holder, int position) {
        MyNasaItem nasaItem = nasaItemsResults.get(position);
        holder.itemName.setText(nasaItem.getTitle());
     //   holder.itemPhoto.layout(0,0,0,0);
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .dontTransform()
                .placeholder(R.drawable.ic_public_black_24dp)
                .error(R.mipmap.ic_launcher_round);
        Glide.with(holder.itemPhoto.getContext()).load(nasaItem.getImageLink()).apply(options).into(holder.itemPhoto);

    }

    @Override
    public int getItemCount() {
        if (nasaItemsResults == null) return 0;
        return nasaItemsResults.size();
    }

    public void setNasaRvResults(List<MyNasaItem> nasaItemsResults) {
        this.nasaItemsResults = nasaItemsResults;
        notifyDataSetChanged();
    }

    public interface NasaResultsAdapterOnClickHandler {
        void onClick(MyNasaItem selectedNasaItem);
    }

    public class ResultsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.nasa_item_name)
        public TextView itemName;

        @BindView(R.id.nasa_photo)
        public ImageView itemPhoto;

        public ResultsHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int positionClicked = getAdapterPosition();
            MyNasaItem selectedItem = nasaItemsResults.get(positionClicked);
            mClickHandler.onClick(selectedItem);
        }
    }

}