package ba.terawatt.etsmostar.APIsForFetchingData;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import ba.terawatt.etsmostar.CustomItems.DownloadItem;
import ba.terawatt.etsmostar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Emir on 1.8.2017.
 */
public class FetchFilesForDownloadAPI extends AsyncTask<String, Void, JSONObject> {

    private List<DownloadItem> items;
    private RecyclerView.Adapter adapter;
    private ProgressDialog dialog;
    public FetchFilesForDownloadAPI(Context context, List<DownloadItem> list, RecyclerView.Adapter adapter){
        dialog = new ProgressDialog(context);
        dialog.setMessage(context.getResources().getString(R.string.loading));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        this.adapter = adapter;
        this.items = list;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        String _url = strings[0];
        String ID = strings[1];
        String data = "";
        try {
            data = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(ID, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            URL url = new URL(_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));

            bufferedWriter.write(data);
            bufferedWriter.close();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
            String line = "";
            StringBuilder respone = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null)
                respone.append(line + "\n");
            bufferedReader.close();
            httpURLConnection.disconnect();
            return new JSONObject(respone.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        dialog.dismiss();
        JSONArray array = null;
        try {
            array = jsonObject.getJSONArray("downloads");
            for (int i = 0; i<array.length(); i++){
                items.add(new DownloadItem(
                        array.getJSONObject(i).getString("ID"),
                        array.getJSONObject(i).getString("Title"),
                        array.getJSONObject(i).getString("FileURL")
                ));
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        super.onPostExecute(jsonObject);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
