package ba.terawatt.etsmostar;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>25.7.2017. </p></br>
 * <p>Manager for IntroScreen Class.</p></br>
 * 
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 * <p>PS..This is funny part of my life...</p>
 */
public class IntroManager {
    private SharedPreferences sh_pref;
    private SharedPreferences.Editor sh_pref_editor;
    private Context context;

    public IntroManager(Context context){
        this.context = context;
        sh_pref = context.getSharedPreferences("FIRST", 0);
        sh_pref_editor = sh_pref.edit();
    }

    public void SetFirstTime(boolean isFirst){
        sh_pref_editor.putBoolean("check", isFirst);
        sh_pref_editor.commit();
    }

    public boolean CheckIsFirtTimeRun(){
        return sh_pref.getBoolean("check", true);
    }
}
