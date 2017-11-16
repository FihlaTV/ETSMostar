package ba.terawatt.etsmostar.APIsForFetchingData;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import ba.terawatt.etsmostar.CustomItems.GalleryItem;
import ba.terawatt.etsmostar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>25.7.2017. </p></br>
 * <p>Sending request to -> php <- script and fetching data in JSON form.</p></br>
 * 
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 * <p>PS..This is funny part of my life...</p>
 */
public class FetchAlbumsGalleryAPI extends AsyncTask<String, Void, JSONObject> {
    private List<GalleryItem> items;
    private ProgressDialog progressDialog;
    private RecyclerView.Adapter adapter;
    public FetchAlbumsGalleryAPI(List<GalleryItem> items, Context context, RecyclerView.Adapter adapter){
        this.items = items;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.loading));
        this.adapter = adapter;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
            String line = "";
            StringBuilder response = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null)
                response.append(line + "\n");
            bufferedReader.close();
            httpURLConnection.getInputStream().close();
            httpURLConnection.disconnect();

            return new JSONObject(response.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        progressDialog.dismiss();
        if(jsonObject != null){
            try {
                JSONArray array = jsonObject.getJSONArray("Albums");
                for (int i = 0;i<array.length(); i++){
                    items.add(new GalleryItem(
                            array.getJSONObject(i).getString("AlbumID"),
                            array.getJSONObject(i).getString("AlbumImage"),
                            array.getJSONObject(i).getString("AlbumTitle"),
                            array.getJSONObject(i).getString("AlbumUrlForShare")
                    ));
                    adapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
