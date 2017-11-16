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

import ba.terawatt.etsmostar.APIsForFetchingData.FetchNewsAPI;
import ba.terawatt.etsmostar.CustomAdapters.CustomRecyclerViewAdapter;
import ba.terawatt.etsmostar.CustomItems.NewsItem;
import ba.terawatt.etsmostar.R;

/**
 * Created by Emir on 12.7.2017.
 */
public class News_Fragment extends Fragment{

    private final String URL = "http://etsmostar.edu.ba/Android";
    private final String DATAURL = "/android_fetch_news_php.php";
    private static RecyclerView recyclerView;
    private static RecyclerView.Adapter adapter;
    private static List<NewsItem> listNews;
    private LinearLayoutManager LLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static List<Integer> secureList;
    private Snackbar snackbar;

    // variables for detect scrooling
    boolean isScrolled = false;
    public  News_Fragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment_layout, container, false);


        if(!isConnected()){
            View v = inflater.inflate(R.layout.no_internet_access, container, false);
            swipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.refreshview);
            swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(false);
                    getFragmentManager().beginTransaction().replace(R.id.frame_container, new News_Fragment(), getActivity().getTitle().toString()).commit();
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

        LLayoutManager = new LinearLayoutManager(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(LLayoutManager);

        secureList = new ArrayList<Integer>();
        listNews = new ArrayList<NewsItem>();
        adapter = new CustomRecyclerViewAdapter(listNews, getContext());
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshview);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setEnabled(false);
                refreshRecyclerView();
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setEnabled(true);
            }
        });

        loadRecyclerViewData();
        onScrollLoadItems();
        return view;
    }

    public boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        return info != null && info.isConnected();
    }

    public void loadRecyclerViewData() {
        new FetchNewsAPI(getContext()).execute(URL+DATAURL, String.valueOf(listNews.size()));
    }

    private void refreshRecyclerView(){
        getFragmentManager().beginTransaction().replace(R.id.frame_container, new News_Fragment(), getActivity().getTitle().toString()).commit();
    }
    private void onScrollLoadItems(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolled = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isScrolled
                        && (LLayoutManager.findFirstVisibleItemPosition() + LLayoutManager.getChildCount()) == LLayoutManager.getItemCount()) {
                    isScrolled = false;
                    loadRecyclerViewData();
                }
            }
        });
    }

    public List<NewsItem> getListNews(){
        return listNews;
    }
    public RecyclerView.Adapter getNewsAdapter(){
        return  adapter;
    }

    public List<Integer> getSecureList(){
        return secureList;
    }
}
