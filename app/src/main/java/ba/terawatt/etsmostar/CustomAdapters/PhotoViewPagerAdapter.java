package ba.terawatt.etsmostar.CustomAdapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import ba.terawatt.etsmostar.CustomItems.GalleryItem;
import ba.terawatt.etsmostar.R;

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
public class PhotoViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<GalleryItem> items;

    public PhotoViewPagerAdapter(List<GalleryItem> items, Context context){
        this.context = context;
        this.items = items;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.photo_item, container, false);
        ImageView image = (ImageView) view.findViewById(R.id.images);
        //TextView textView = (TextView) container.getRootView().findViewById(R.id.numberOfFullPhotosActivity);
       // textView.setText(String.valueOf(position) + "/" + String.valueOf(items.size()));
        Glide.with(context)
                .load(items.get(position).getImageURL())
                .centerCrop()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
