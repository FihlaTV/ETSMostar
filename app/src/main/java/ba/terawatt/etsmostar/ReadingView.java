package ba.terawatt.etsmostar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import ba.terawatt.etsmostar.APIsForFetchingData.FetchForReadAPI;


public class ReadingView extends ActionBarActivity implements UpdateUIInterface {

    private Toolbar toolbar;
    private static CollapsingToolbarLayout collapsingToolbarLayout;
    private final String url = "http://etsmostar.edu.ba/Android/";
    private String dataurl;
    private  String ID;
    private UpdateUIInterface delegate = null;
    // UI of activity
    private TextView heading;
    private TextView author;
    private TextView uploadDate;
    private TextView content;
    private ImageView image;
    // UI of "no_internet_access" actaivity
    private Snackbar snackbar;
    private SwipeRefreshLayout swipe;

    public ReadingView(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ID = getIntent().getExtras().getString("ID");
        final String script = getIntent().getExtras().getString("ViewType");

        // NERADI OVO KAD NEMA INTERNETA -> -> -> -> POPRAVITI <- <- <- <-
        if(!isConnected()){
            setContentView(R.layout.no_internet_access);
            swipe = (SwipeRefreshLayout) findViewById(R.id.refreshview);



            swipe.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipe.setEnabled(false);
                    swipe.setRefreshing(false);
                    swipe.setEnabled(true);
                    Intent in = new Intent(getBaseContext(), ReadingView.class);
                    Bundle bundleForRefresh = new Bundle();
                    bundleForRefresh.putString("ID", ID);
                    bundleForRefresh.putString("ViewType", script);
                    in.putExtras(bundleForRefresh);
                    startActivity(in);
                    finish();
                }
            });

            snackbar = Snackbar.make((CoordinatorLayout) findViewById(R.id.cordinatorLayout), "Nema internetske veze", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Zatvori", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                        }
                    });
            snackbar.show();

            return;
        }
        setContentView(R.layout.reading_layout);
        delegate = this;
        dataurl = "android_fetch_view_" + script + ".php";

        loadReadingData();

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_close_white_24dp));
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);


        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher);

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @SuppressWarnings("ResourceType")
            @Override
            public void onGenerated(Palette palette) {

                collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.actionBarColor));
                collapsingToolbarLayout.setStatusBarScrimColor(ContextCompat.getColor(getBaseContext(), R.color.actionBarColor));
            }
        });


        heading = (TextView) findViewById(R.id.readingHeading);
        author = (TextView) findViewById(R.id.readingAuthor);
        uploadDate = (TextView) findViewById(R.id.readingDate);
        content = (TextView) findViewById(R.id.readingContent);
        image = (ImageView) findViewById(R.id.readingImage);
        if(script.equals("class")){
            author.setVisibility(View.GONE);
            uploadDate.setVisibility(View.GONE);
            View v = (View) findViewById(R.id.lineSeparator);
            v.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void loadReadingData(){
       new FetchForReadAPI(this, delegate).execute(url+dataurl,ID);
    }

    @Override
    public void updateData(JSONObject o) {
        try {

            heading.setText(o.getString("Title"));
            collapsingToolbarLayout.setTitle(o.getString("Title"));
            author.setText(o.getString("Author"));
            uploadDate.setText(o.getString("UploadDate"));
            content.setText(Html.fromHtml(o.getString("Content")));
            // setting image
            Picasso
                    .with(this)
                    .load(o.getString("Image"))
                    .fit()
                    .centerCrop()
                    .into(image);


    } catch (JSONException e) {
            e.printStackTrace();
    }
    }
    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}
