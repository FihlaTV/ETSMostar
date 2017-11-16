package ba.terawatt.etsmostar.DrawerFragments;

import android.app.ProgressDialog;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ba.terawatt.etsmostar.APIsForFetchingData.FetchPollVotedAPI;
import ba.terawatt.etsmostar.CustomAdapters.PollForVoteAdapter;
import ba.terawatt.etsmostar.CustomAdapters.PollVotedAdapter;
import ba.terawatt.etsmostar.CustomItems.PollForVoteItem;
import ba.terawatt.etsmostar.CustomItems.PollVotedItem;
import ba.terawatt.etsmostar.R;
import ba.terawatt.etsmostar.UpdateVotedPoll;

/**
 * Created by Emir on 5.8.2017.
 */
public class Poll_Fragment extends Fragment implements UpdateVotedPoll{

    private final String url = "http://etsmostar.edu.ba/Android/";
    private final String dataurl = "android_fetch_voted_poll.php";

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipe;
    private RecyclerView.Adapter adapter;
    private List<PollForVoteItem> list;
    private List<PollVotedItem> listOfVotedItems;
    private StringBuilder question;
    private ProgressDialog progressDialog;
    private UpdateVotedPoll updatePoll;

    private Snackbar snackbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.poll_voted_fragment, container, false);

        if(!isConnected()){

            v = inflater.inflate(R.layout.no_internet_access, container, false);

            swipe = (SwipeRefreshLayout) v.findViewById(R.id.refreshview);
            swipe.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipe.setEnabled(false);
                    getFragmentManager().beginTransaction().replace(R.id.frame_container, new Poll_Fragment(), getTag()).commit();
                    swipe.setRefreshing(false);
                    swipe.setEnabled(true);
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


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getContext().getResources().getString(R.string.loading));
        updatePoll = this;
        list = new ArrayList<>();
        listOfVotedItems = new ArrayList<>();
        question = new StringBuilder();
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_poll);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        swipe = (SwipeRefreshLayout) v.findViewById(R.id.refreshviewPoll);
        swipe.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setEnabled(false);
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new Poll_Fragment(), getTag()).commit();
                swipe.setRefreshing(false);
                swipe.setEnabled(true);
            }
        });
        LoadRecyclerViewData();
        return v;
    }
    private void LoadRecyclerViewData(){
       new FetchPollVotedAPI(updatePoll).execute(url + dataurl);
    }

    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public void beforeFetcingPoll() {
        progressDialog.show();
    }

    @Override
    public void UpdateVotedPollUI(JSONObject o) throws JSONException {
        if(o.getString("isVoted").equals("true")){
            question.append(o.getString("question"));
            JSONArray array = o.getJSONArray("answers");
            for (int i = 0; i<array.length(); i++){
                listOfVotedItems.add(new PollVotedItem(
                        array.getJSONObject(i).getString("AnswerTitle"),
                        Float.parseFloat(array.getJSONObject(i).getString("AnswerPercent"))

                ));
            }
            adapter = new PollVotedAdapter(listOfVotedItems,getContext(), question);
            recyclerView.setAdapter(adapter);
        } else if(o.getString("isVoted").equals("false")){
            question.append(o.getString("question"));
            JSONArray array = o.getJSONArray("answers");
            for (int i = 0; i<array.length(); i++){
                list.add(new PollForVoteItem(
                        array.getJSONObject(i).getString("AnswerID"),
                        array.getJSONObject(i).getString("AnswerTitle")
                ));
            }
            adapter = new PollForVoteAdapter(list, question, this);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void afterUpdatingPoll() {
        progressDialog.dismiss();
    }

    @Override
    public void refreshPoll() {
        Fragment fragment = new Poll_Fragment();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, fragment, getTag())
                .commit();
    }
}
