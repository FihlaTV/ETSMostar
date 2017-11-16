package ba.terawatt.etsmostar.DrawerFragments;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.List;

import ba.terawatt.etsmostar.APIsForFetchingData.FetchFilesForDownloadAPI;
import ba.terawatt.etsmostar.CustomAdapters.DownloadAdapter;
import ba.terawatt.etsmostar.CustomItems.DownloadItem;
import ba.terawatt.etsmostar.R;

/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>25.7.2017. </p></br>
 * <p>Background of layouts which user can see. This class manipulating with multiple layouts.</p></br>
 * 
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 * <p>PS..This is funny part of my life...</p>
 */
public class Download_Fragment extends Fragment {

    private final String url = "http://etsmostar.edu.ba/";
    private final String dataurl = "/*  PHP Script For Fetching data   */";

    private List<DownloadItem> listOfFiles;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager manager;
    private SwipeRefreshLayout swipe;
    private Snackbar snackbar;

    boolean userScroll = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.event_fragment_layout, container, false);

        if(!isConnected()){
            v = inflater.inflate(R.layout.no_internet_access, container, false);
            swipe = (SwipeRefreshLayout) v.findViewById(R.id.refreshview);
            swipe.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipe.setEnabled(false);
                    UpdateView();
                    swipe.setRefreshing(false);
                    swipe.setEnabled(true);
                }
            });
            snackbar = Snackbar.make(v.findViewById(R.id.cordinatorLayout), "Nema internetske veze", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Zatvori", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                        }
                    });
            snackbar.show();
            return v;
        }
        manager = new LinearLayoutManager(getContext());
        listOfFiles = new ArrayList<>();

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_event);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

        adapter = new DownloadAdapter(listOfFiles, getContext());
        recyclerView.setAdapter(adapter);
        swipe = (SwipeRefreshLayout) v.findViewById(R.id.refreshviewEvent);
        swipe.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setEnabled(false);
                UpdateView();
                swipe.setRefreshing(false);
                swipe.setEnabled(true);
            }
        });
        LoadRecyclerView();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    userScroll = true;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(userScroll &&
                        manager.findFirstVisibleItemPosition() + manager.getChildCount() == manager.getItemCount())
                    LoadRecyclerView();
            }
        });

    return v;

    }
    private void UpdateView(){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, new Download_Fragment(), getActivity().getTitle().toString())
                .commit();
    }
    private void LoadRecyclerView(){
        new FetchFilesForDownloadAPI(getContext(), listOfFiles, adapter).execute(url + dataurl, String.valueOf(listOfFiles.size()));
    }
    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
