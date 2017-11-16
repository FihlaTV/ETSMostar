package ba.terawatt.etsmostar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ba.terawatt.etsmostar.APIsForFetchingData.FetchAlbumPhotosAPI;
import ba.terawatt.etsmostar.CustomAdapters.PhotoAdapter;
import ba.terawatt.etsmostar.CustomItems.GalleryItem;

/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>25.7.2017. </p></br>
 * <p>GalleryView Class.</p></br>
 * 
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 * <p>PS..This is funny part of my life...</p>
 */
public class GalleryView extends AppCompatActivity implements UpdateUIInterface {

    private final String url = "http://etsmostar.edu.ba/Android/";
    private final String dataurl = "android_fetch_photos.php";

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView.Adapter adapter;
    private List<GalleryItem> items;
    private Toolbar toolbar;
    private String URLForShare;
    private UpdateUIInterface updateUIInterface;
    private String albumTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String ID  = getIntent().getExtras().getString("AlbumID");
        URLForShare = getIntent().getExtras().getString("AlbumURLForShare");
        albumTitle = getIntent().getExtras().getString("AlbumTitle");

        final Bundle refreshActivity = new Bundle();
        refreshActivity.putString("AlbumID", ID);
        refreshActivity.putString("AlbumURLForShare", URLForShare);
        refreshActivity.putString("AlbumTitle", albumTitle);
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        if(!isConnected()){
            setContentView((View) inflater.inflate(R.layout.no_internet_access, (ViewGroup) this.findViewById(android.R.id.content), false));
            refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshview);
            refreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED);
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshLayout.setEnabled(false);
                    startActivity(new Intent(GalleryView.this, GalleryView.class).putExtras(refreshActivity));
                    refreshLayout.setRefreshing(false);
                    refreshLayout.setEnabled(true);
                    finish();
                }
            });
            final Snackbar snackbar = Snackbar.make(findViewById(R.id.cordinatorLayout), "Nema internetske veze", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Zatvori", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
            return;
        }

        setContentView((View) inflater.inflate(R.layout.view_photos_layout, (ViewGroup) this.findViewById(android.R.id.content), false));

        updateUIInterface = this;

        toolbar = (Toolbar) findViewById(R.id.toolbarGalleryView);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(albumTitle);
        }
        if(Build.VERSION.SDK_INT >= 21){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.statusBarColor));
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_GalleryView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.offsetTo(1,1);

            }
        });
        items = new ArrayList<>();

        adapter = new PhotoAdapter(items,this);
        recyclerView.setAdapter(adapter);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshviewGalleryView);
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setEnabled(false);
                // update content
                startActivity(new Intent(GalleryView.this, GalleryView.class).putExtras(refreshActivity));
                refreshLayout.setRefreshing(false);
                refreshLayout.setEnabled(true);
                finish();
            }
        });

        new FetchAlbumPhotosAPI(updateUIInterface, this).execute(url + dataurl, ID);
    }

    @Override
    public void updateData(JSONObject o) {
        if(o != null){
            try {
                JSONArray array = o.getJSONArray("Photos");
                for (int i = 0; i<array.length();i++){
                    items.add(new GalleryItem(
                            array.getJSONObject(i).getString("ID"),
                            array.getJSONObject(i).getString("ImageURL"),
                            albumTitle,
                            array.getJSONObject(i).getString("ImageURLForShare")
                    ));
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_card_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        } else if(item.getItemId() == R.id.shareContent){
            startActivity(Intent.createChooser(new Intent(Intent.ACTION_SEND).putExtra(Intent.EXTRA_TEXT, URLForShare).setType("text/plain"), "Podijeli"));
            return true;
        }
        return false;
    }

    private boolean isConnected(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        return info != null && info.isConnected();
    }
}
