package ba.terawatt.etsmostar.CustomAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import ba.terawatt.etsmostar.CustomItems.GalleryItem;
import ba.terawatt.etsmostar.ParcelableForGalleryItems;
import ba.terawatt.etsmostar.PhotoFullScreenActivity;
import ba.terawatt.etsmostar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>25.7.2017. </p></br>
 * <p>Pushing data to RecyclerView.</p></br>
 * 
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 * <p>PS..This is funny part of my life...</p>
 */
public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<GalleryItem> items;
    private Context context;
    public PhotoAdapter(List<GalleryItem> items, Context context){
        this.items = items;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        PhotoViewHolder viewHolder = (PhotoViewHolder) holder;

        Glide
                .with(context)
                .load(items.get(position).getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .thumbnail(0.6f)
                .into(viewHolder.getImageView());

        viewHolder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<ParcelableForGalleryItems> aa = new ArrayList<ParcelableForGalleryItems>();
                for (int i = 0; i<items.size();i++){
                    aa.add(new ParcelableForGalleryItems(
                            items.get(i).getID(),
                            items.get(i).getImageURL(),
                            items.get(i).getName(),
                            items.get(i).getURLForShare()
                    ));
                }

                Intent intent = new Intent(context, PhotoFullScreenActivity.class);
                intent.putParcelableArrayListExtra("PhotoItems", aa);
                intent.putExtra("CurrentPosition", position);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private View view;
        public PhotoViewHolder(View view){
            super(view);
            imageView = (ImageView) view.findViewById(R.id.images);
            imageView.setMinimumHeight(250);

            this.view = view;
        }

        public ImageView getImageView(){
            return imageView;
        }

        public View getView() {
            return view;
        }
    }


}
