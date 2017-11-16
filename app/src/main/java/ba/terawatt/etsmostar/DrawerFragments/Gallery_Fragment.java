package ba.terawatt.etsmostar.DrawerFragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ba.terawatt.etsmostar.APIsForFetchingData.FetchAlbumsGalleryAPI;
import ba.terawatt.etsmostar.CustomAdapters.GalleryAlbumsAdapter;
import ba.terawatt.etsmostar.CustomItems.GalleryItem;
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
public class Gallery_Fragment extends Fragment {

    private final String url = "http://etsmostar.edu.ba/";
    private final String dataurl = "/*  PHP Script For Fetching data   */";

    private SwipeRefreshLayout swipe;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<GalleryItem> albums;
    private GridLayoutManager gridManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.event_fragment_layout, container, false);

        if(!isConnected()){
            view = LayoutInflater.from(container.getContext()).inflate(R.layout.no_internet_access, container, false);
            swipe = (SwipeRefreshLayout) view.findViewById(R.id.refreshview);
            swipe.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipe.setEnabled(false);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_container, new Gallery_Fragment(), getTag())
                            .commit();
                    swipe.setRefreshing(false);
                    swipe.setEnabled(true);
                }
            });
            final Snackbar snackbar = Snackbar.make(view.findViewById(R.id.cordinatorLayout), "Nema internetske veze", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Zatvori", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();

            return view;
        }

        swipe = (SwipeRefreshLayout) view.findViewById(R.id.refreshviewEvent);
        swipe.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setEnabled(false);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, new Gallery_Fragment(), getTag())
                        .commit();
                swipe.setRefreshing(false);
                swipe.setEnabled(true);
            }
        });
        albums = new ArrayList<>();
        gridManager = new GridLayoutManager(getContext(),2, GridLayoutManager.VERTICAL, false);

        adapter = new GalleryAlbumsAdapter(albums, getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_event);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(gridManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = 6;
                outRect.left = 6;
                outRect.right = 6;

                if (parent.getChildLayoutPosition(view) == 0)
                    outRect.top = 0;
                else
                    outRect.top = 6;
            }
        });

        recyclerView.setAdapter(adapter);
        new FetchAlbumsGalleryAPI(albums, getContext(), adapter).execute(url + dataurl);


        return view;
    }

    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        return info != null && info.isConnected();
    }
}
