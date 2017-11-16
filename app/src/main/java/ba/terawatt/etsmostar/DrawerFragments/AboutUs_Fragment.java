package ba.terawatt.etsmostar.DrawerFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ba.terawatt.etsmostar.R;

/**
 * Created by Emir on 3.8.2017.
 */
public class AboutUs_Fragment extends Fragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_us_layout_fragment, container, false);



        return view;
    }
}
