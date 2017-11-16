package ba.terawatt.etsmostar;

import android.app.DownloadManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import ba.terawatt.etsmostar.CustomAdapters.PhotoViewPagerAdapter;
import ba.terawatt.etsmostar.CustomItems.GalleryItem;
/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>25.7.2017. </p></br>
 * <p>Class for view photo.</p></br>
 * 
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 * <p>PS..This is funny part of my life...</p>
 */
public class PhotoFullScreenActivity extends AppCompatActivity {
    private ViewPager pager;
    private PagerAdapter adapter;
    private List<GalleryItem> items;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.full_screen_photo, null);
        setContentView(view);

        if(Build.VERSION.SDK_INT >= 21){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.BLACK);
        }

        toolbar = (Toolbar) findViewById(R.id.actionBar_full_photo_activiry);
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        toolbar.setTitle("Pregled Albuma");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ArrayList<ParcelableForGalleryItems> parcelableObj = getIntent().getParcelableArrayListExtra("PhotoItems");
        items = new ArrayList<>();
        for (int i = 0;i < parcelableObj.size(); i++){
            items.add(new GalleryItem(
                    parcelableObj.get(i).getID(),
                    parcelableObj.get(i).getImageURL(),
                    parcelableObj.get(i).getName(),
                    parcelableObj.get(i).getURLForShare()
            ));
        }
        int position = getIntent().getExtras().getInt("CurrentPosition", 0);

        pager = (ViewPager) findViewById(R.id.viewPagerFullScreenPhoto);

        adapter = new PhotoViewPagerAdapter(items, this);
        pager.setAdapter(adapter);
        pager.setCurrentItem(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.photo_full_screen_activity_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        } else if(item.getItemId() == R.id.downloadImageFullScreenActivity){
            DownloadManager manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Request query =  new DownloadManager.Request(Uri.parse(items.get(pager.getCurrentItem()).getImageURL()));
            query.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            manager.enqueue(query);
        }
        return false;
    }
}
