package ba.terawatt.etsmostar;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Emir on 2.7.2017.
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
