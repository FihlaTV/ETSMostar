package ba.terawatt.etsmostar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>25.7.2017. </p></br>
 * <p>Main Class.</p></br>
 * 
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 * <p>PS..This is funny part of my life...</p>
 */
public class MainActivity extends ActionBarActivity {

    private IntroManager introManager;
    private  ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private int[] layouts;
    private TextView[] dots;
    private LinearLayout dots_ly;
    private Button next;
    private Button skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        introManager = new IntroManager(this);
        if(!introManager.CheckIsFirtTimeRun()){
            introManager.SetFirstTime(false);
            Intent in = new Intent(MainActivity.this, WelcomeScreen.class);
            startActivity(in);
            finish();

        }
        if(Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_intro_screen_main);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dots_ly = (LinearLayout) findViewById(R.id.layout_dots);
        next = (Button) findViewById(R.id.btn_next);
        skip = (Button) findViewById(R.id.btn_skip);
        layouts = new int[]{
                R.layout.activity_intro_screen_1,
                R.layout.activity_intro_screen_2,
                R.layout.activity_intro_screen_3,
                R.layout.activity_intro_screen_4,
                R.layout.activity_intro_screen_5};

        addBottomDots(0);

        ChangeStatusBarColor();

        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOnPageChangeListener(viewListener);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                introManager.SetFirstTime(false);
                Intent intent = new Intent(MainActivity.this, WelcomeScreen.class);
                startActivity(intent);
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = GetItem(+1);
                if(current < dots.length){
                    viewPager.setCurrentItem(current);
                    return;
                }
                introManager.SetFirstTime(false);
                Intent in = new Intent(MainActivity.this, WelcomeScreen.class);
                startActivity(in);
                finish();
            }
        });
    }
    private int GetItem(int i){
        return viewPager.getCurrentItem() + i;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    private void addBottomDots(int position){
        dots = new TextView[layouts.length];
        int[] colorActive = getResources().getIntArray(R.array.dot_enable);
        int[] colorDisable = getResources().getIntArray(R.array.dot_disable);
        dots_ly.removeAllViews();
        for (int i = 0; i<dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorDisable[position]);
            dots_ly.addView(dots[i]);
        }
        if(dots.length>0)
            dots[position].setTextColor(colorActive[position]);
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int i, float v, int i1) {
            return;
        }

        @Override
        public void onPageSelected(int i) {
            addBottomDots(i);

            if(i == dots.length-1){
                next.setText("ZATVORI");
                skip.setVisibility(View.GONE);
                return;
            }
            next.setText("NAREDNI");
            skip.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageScrollStateChanged(int i) {
            return;
        }
    };

    private void ChangeStatusBarColor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public class ViewPagerAdapter extends PagerAdapter{

        private LayoutInflater layoutInflater;
        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            return  view;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = (View)object;
            container.removeView(v);
        }
    }
}
