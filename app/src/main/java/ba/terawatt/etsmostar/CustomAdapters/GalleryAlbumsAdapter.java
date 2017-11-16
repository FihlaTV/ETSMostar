package ba.terawatt.etsmostar.CustomAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import ba.terawatt.etsmostar.CustomItems.GalleryItem;
import ba.terawatt.etsmostar.GalleryView;
import ba.terawatt.etsmostar.R;

import java.util.List;

/**
 * Created by Emir on 16.8.2017.
 */
public class GalleryAlbumsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GalleryItem> items;
    private Context context;
    public GalleryAlbumsAdapter(List<GalleryItem> list, Activity activity){
        items = list;
        this.context = (Context)activity;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GalleryAlbumsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        GalleryAlbumsAdapter.ViewHolder viewHolder = (GalleryAlbumsAdapter.ViewHolder) holder;
        viewHolder.getTextView().setText(items.get(position).getName());

        Glide
                .with(context)
                .load(items.get(position).getImageURL())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(viewHolder.getImageView());

        viewHolder.getTextViewShare().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v7.widget.PopupMenu popupMenu = new android.support.v7.widget.PopupMenu(context, view);
                popupMenu.inflate(R.menu.share_card_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.shareContent) {
                            context.startActivity(Intent.createChooser(
                                    new Intent(Intent.ACTION_SEND)
                                            .putExtra(Intent.EXTRA_TEXT, items.get(position).getURLForShare())
                                            .setType("text/plain"), "Podijeli"));
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

       viewHolder.getView().setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Bundle bundle = new Bundle();
               bundle.putString("AlbumID", items.get(position).getID());
               bundle.putString("AlbumTitle", items.get(position).getName());
               bundle.putString("AlbumURLForShare", items.get(position).getURLForShare());
               context.startActivity(new Intent(context, GalleryView.class).putExtras(bundle));
           }
       });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textView;
        private TextView textViewShare;
        private View view;
        public ViewHolder (View view){
            super(view);

            imageView = (ImageView) view.findViewById(R.id.galleryAlbumImage);
            textView = (TextView) view.findViewById(R.id.galleryAlbumHeadin);
            textViewShare = (TextView) view.findViewById(R.id.galleryAlbumShare);
            this.view = view;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextView() {
            return textView;
        }

        public TextView getTextViewShare() {
            return textViewShare;
        }

        public View getView(){
            return view;
        }

    }
}
