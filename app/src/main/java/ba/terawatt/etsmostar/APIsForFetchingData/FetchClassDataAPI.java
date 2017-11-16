package ba.terawatt.etsmostar.APIsForFetchingData;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

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

import ba.terawatt.etsmostar.CustomItems.ClassItem;
import ba.terawatt.etsmostar.R;

/**
 * Created by Emir on 30.7.2017.
 */
public class FetchClassDataAPI extends AsyncTask<String, Void, JSONObject>{
    private List<ClassItem> classes;
    private RecyclerView.Adapter adapter;
    private Context context;
    private ProgressDialog progressDialog;
    public  FetchClassDataAPI(List<ClassItem> list, RecyclerView.Adapter adapter, Context context){
        classes = list;
        this.adapter = adapter;
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }
    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        String _url = strings[0];
        try {
            URL url = new URL(_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
            String line = "";
            StringBuilder respone = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null)
                respone.append(line + "\n");
            progressDialog.dismiss();
            bufferedReader.close();
            httpURLConnection.disconnect();

           return new JSONObject(respone.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        try {
            JSONArray  array = jsonObject.getJSONArray("zanimanja");
            for (int i = 0;i<array.length();i++){
                classes.add(new ClassItem(
                        array.getJSONObject(i).getString("ID"),
                        array.getJSONObject(i).getString("Title"),
                        array.getJSONObject(i).getString("Image")
                ));
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        super.onPostExecute(jsonObject);
    }
}
