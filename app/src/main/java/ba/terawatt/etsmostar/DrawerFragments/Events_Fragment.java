package ba.terawatt.etsmostar.DrawerFragments;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
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

import ba.terawatt.etsmostar.APIsForFetchingData.FetchEventsAPI;
import ba.terawatt.etsmostar.CustomAdapters.EventAdapter;
import ba.terawatt.etsmostar.CustomItems.EventItem;
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
public class Events_Fragment extends Fragment {

    private final String url = "http://etsmostar.edu.ba/";
    private final String dataurl = "/*  PHP Script For Fetching data   */";
    private Snackbar snackbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private static RecyclerView.Adapter adapter;
    private static List<Integer> secureList;
    private LinearLayoutManager manager;
    private static List<EventItem> events;

    boolean userScroll = false;

    public Events_Fragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.event_fragment_layout, container, false);

        if(!isConnected()){
            v = inflater.inflate(R.layout.no_internet_access, container, false);
            swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.refreshview);
            swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(false);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_container, new Events_Fragment(), getActivity().getTitle().toString())
                            .commit();
                }
            });
            snackbar = Snackbar.make((CoordinatorLayout) v.findViewById(R.id.cordinatorLayout), "Nema internetske veze", Snackbar.LENGTH_INDEFINITE)
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

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_event);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        secureList = new ArrayList<Integer>();
        events = new ArrayList<EventItem>();
        adapter = new EventAdapter(events, getContext());
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.refreshviewEvent);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setEnabled(false);
                UpdateData();
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setEnabled(true);
            }
        });

        loadRecyclerView();
        srollLoadingData();
        return v;
    }

    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        return info != null && info.isConnected();
    }
    private void loadRecyclerView(){
        new FetchEventsAPI(events,getContext(),adapter,secureList).execute(url + dataurl, String.valueOf(events.size()));
    }
    private void srollLoadingData(){
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
                if (userScroll &&
                        manager.findFirstVisibleItemPosition() + manager.getChildCount() == manager.getItemCount()) {
                    userScroll = false;
                    loadRecyclerView();
                }
            }
        });
    }
    private void UpdateData(){
        getFragmentManager().beginTransaction().replace(R.id.frame_container, new Events_Fragment(), getActivity().getTitle().toString()).commit();
    }
}
