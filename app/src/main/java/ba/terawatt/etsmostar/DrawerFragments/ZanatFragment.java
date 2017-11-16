package ba.terawatt.etsmostar.DrawerFragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ba.terawatt.etsmostar.APIsForFetchingData.FetchClassDataAPI;
import ba.terawatt.etsmostar.CustomAdapters.ClassAdapter;
import ba.terawatt.etsmostar.CustomItems.ClassItem;
import ba.terawatt.etsmostar.R;

/**
 * Created by Emir on 30.7.2017.
 */
public class ZanatFragment extends Fragment {

    private final String url = "http://etsmostar.edu.ba/Android/";
    private final String dataurl = "android_fetch_class3_php.php";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<ClassItem> list;
    private LinearLayoutManager manager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.news_fragment_layout, container, false);

        manager = new LinearLayoutManager(getContext());

        list = new ArrayList<>();
        adapter = new ClassAdapter(list, getContext());

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.refreshview);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setEnabled(false);
                UpdateRecyclerView();
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setEnabled(true);
            }
        });

        /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });*/
        LoadRecyclerViewData();
        return v;
    }
    private void UpdateRecyclerView(){
        getParentFragment()
                .getFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, new Smjerovi_Fragment(), getActivity().getTitle().toString())
                .commit();
    }
    private void LoadRecyclerViewData(){
        new FetchClassDataAPI(list,adapter,getContext()).execute(url+dataurl);
    }
}
