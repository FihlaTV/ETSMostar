package ba.terawatt.etsmostar;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import ba.terawatt.etsmostar.DrawerFragments.AboutApp_Fragment;
import ba.terawatt.etsmostar.DrawerFragments.AboutUs_Fragment;
import ba.terawatt.etsmostar.DrawerFragments.Download_Fragment;
import ba.terawatt.etsmostar.DrawerFragments.Events_Fragment;
import ba.terawatt.etsmostar.DrawerFragments.Gallery_Fragment;
import ba.terawatt.etsmostar.DrawerFragments.News_Fragment;
import ba.terawatt.etsmostar.DrawerFragments.Notify_Fragment;
import ba.terawatt.etsmostar.DrawerFragments.Poll_Fragment;
import ba.terawatt.etsmostar.DrawerFragments.Smjerovi_Fragment;

/**
 * Created by Emir on 12.7.2017.
 */
public class NavigationViewActivity extends AppCompatActivity {
    private static DrawerLayout mDrawerLayout;
    private static ActionBarDrawerToggle mDrawerToggle;
    private static Toolbar toolbar;
    private static FragmentManager fragmentManager;
    private static NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //according to boolean value set content view
        setContentView(R.layout.custom_navigation_view);

        initViews();
        setUpHeaderView();
        onMenuItemSelected();

        ChangeStatusBarColor(ContextCompat.getColor(getBaseContext(),R.color.statusBarColor));

        //At start set home fragment
        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.news);
            MenuItem item = navigationView.getMenu().findItem(R.id.news);
            setNewsFragment(item);
        }
    }

    /*  Init all views  */
    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.slider_menu);
        fragmentManager = getSupportFragmentManager();
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, toolbar, // nav menu toggle icon
                R.string.drawer_open, // nav drawer open - description for
                // accessibility
                R.string.drawer_close // nav drawer close - description for
                // accessibility
        ) {
            public void onDrawerClosed(View view) {
            }

            public void onDrawerOpened(View drawerView) {

            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    private void setUpHeaderView() {
        View headerView = navigationView.inflateHeaderView(R.layout.header_view);
        TextView textOne = (TextView) headerView.findViewById(R.id.username);
        TextView textTwo = (TextView) headerView.findViewById(R.id.email_address);
    }


    /*  Method for Navigation View item selection  */
    private void onMenuItemSelected() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                // check if is active menu item
                if(item.isChecked()){
                    mDrawerLayout.closeDrawers();
                    return false;
                }
                //Check and un-check menu item if they are checkable behaviour
                if (item.isCheckable()) {
                    if (item.isChecked()) item.setChecked(false);
                    else item.setChecked(true);
                }

                //Closing drawer on item click
                mDrawerLayout.closeDrawers();

                switch (item.getItemId()) {
                    case R.id.news:
                        //Replaceing fragment

                        setNewsFragment(item);
                        break;
                    case R.id.events:
                        //Replaceing fragment

                        setEventsFragment(item);
                        break;
                    case R.id.notifications:
                        //Replaceing fragment

                        setNotifyFragment(item);
                        break;
                    case R.id.gallery:
                        //Replaceing fragment

                        setGalleryFragment(item);
                        break;
                    case R.id.study:
                        //Replaceing fragment

                        setClassFragment(item);
                        break;
                    case R.id.download:
                        //Replaceing fragment

                        setDownloadFragment(item);
                        break;
                    case R.id.poll:
                        //Replaceing fragment

                        setPollFragment(item);
                        break;
                    case R.id.abouts:
                        //Replaceing fragment

                        setAboutUsFragment(item);
                        break;
                    case R.id.share_app:
                        //Start new Activity or do your stuff

                        setShareFragment(item);
                        break;
                    case R.id.rate_app:
                        //Replaceing fragment

                        startActivity(new Intent(NavigationViewActivity.this, AboutApp_Fragment.class));
                        break;
                    default:break;
                }

                return false;
            }
        });
    }
    public void setNewsFragment(MenuItem item) {
        toolbar.setTitle(item.getTitle());
        toolbar.setTitleTextColor(Color.WHITE);
        //Find fragment by tag
        Fragment fr = fragmentManager.findFragmentByTag(item.getTitle().toString());

        Fragment news_fragment = new News_Fragment();
        Bundle b = new Bundle();

        //If fragment is null replace fragment
        if (fr == null) {
            b.putString("data", item.getTitle().toString());
            news_fragment.setArguments(b);//Set Arguments
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container,
                            news_fragment, item.getTitle().toString())
                    .commit();
        }
    }

    private void setEventsFragment(MenuItem item){
        toolbar.setTitle(item.getTitle());
        toolbar.setTitleTextColor(Color.WHITE);
        //setting fragment
        Fragment eventFragment = new Events_Fragment();
        fragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, eventFragment, item.getTitle().toString())
                .commit();
    }
    private void setNotifyFragment(MenuItem item){
        toolbar.setTitle(item.getTitle());
        toolbar.setTitleTextColor(Color.WHITE);
        //setting fragment
        Fragment notifyFragment = new Notify_Fragment();
        fragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, notifyFragment, item.getTitle().toString())
                .commit();
    }
    private void setClassFragment(MenuItem item){
        toolbar.setTitle(item.getTitle());
        toolbar.setTitleTextColor(Color.WHITE);
        //setting fragment
        Fragment classFragment = new Smjerovi_Fragment();
        fragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, classFragment, item.getTitle().toString())
                .commit();
    }
    private void setDownloadFragment(MenuItem item){
        toolbar.setTitle(item.getTitle());
        toolbar.setTitleTextColor(Color.WHITE);
        //setting fragment
        Fragment fragment = new Download_Fragment();
        fragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, fragment, item.getTitle().toString())
                .commit();
    }
    private void setAboutUsFragment(MenuItem item){
        toolbar.setTitle(item.getTitle());
        toolbar.setTitleTextColor(Color.WHITE);
        //setting fragment
        Fragment fragment = new AboutUs_Fragment();
        fragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, fragment, item.getTitle().toString())
                .commit();
    }
    private void setPollFragment(MenuItem item){
        toolbar.setTitle(item.getTitle());
        toolbar.setTitleTextColor(Color.WHITE);
        Fragment fragment = new Poll_Fragment();
        //setting poll fragment
        fragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, fragment, item.getTitle().toString())
                .commit();
    }
    private void setShareFragment(MenuItem item){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=ba.terawatt.etsmostar&hl=en");
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "Podijeli"));
    }
    private void setGalleryFragment(MenuItem item){
        toolbar.setTitle(item.getTitle());
        toolbar.setTitleTextColor(Color.WHITE);
        fragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, new Gallery_Fragment(), item.getTitle().toString())
                .commit();
    }
    //On back press check if drawer is open and closed
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT))
            mDrawerLayout.closeDrawers();
        else
            super.onBackPressed();
    }

    private void ChangeStatusBarColor(int color){
        if(Build.VERSION.SDK_INT >= 21){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }
}