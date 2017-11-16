package ba.terawatt.etsmostar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;


public class WelcomeScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        ChangeStatusBarColor(ContextCompat.getColor(getBaseContext(), R.color.actionBarColor));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(WelcomeScreen.this, NavigationViewActivity.class);
                startActivity(in);
                finish();

            }
        }, 800);
    }
    private void ChangeStatusBarColor(int color){
        if(Build.VERSION.SDK_INT >= 21){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

}
