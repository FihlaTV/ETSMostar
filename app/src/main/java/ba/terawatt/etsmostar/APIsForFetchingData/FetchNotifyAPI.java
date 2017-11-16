package ba.terawatt.etsmostar.APIsForFetchingData;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import ba.terawatt.etsmostar.CustomItems.NotifyItem;
import ba.terawatt.etsmostar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Emir on 28.7.2017.
 */
public class FetchNotifyAPI extends AsyncTask<String, Void, JSONObject> {

    private List<NotifyItem> notifyItems;
    private List<Integer> secureList;
    private ProgressDialog progressDialog;
    private Context context;
    private RecyclerView.Adapter adapter;
    public FetchNotifyAPI(List<NotifyItem> list, List<Integer> secureList, Context context, RecyclerView.Adapter adapter) {
        this.notifyItems = list;
        this.secureList = secureList;
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        String _url = strings[0];
        String ID = strings[1];

        try {
            URL url = new URL(_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoOutput(true);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));
            String data = URLEncoder.encode("ID", "UTF-8")+"="+URLEncoder.encode(ID, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();

            progressDialog.dismiss();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
            String line ="";
            StringBuilder response = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null)
                response.append(line + "\n");
            bufferedReader.close();
            httpURLConnection.disconnect();
            JSONObject json = new JSONObject(response.toString());
            return json;
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
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        try {
            JSONArray array = jsonObject.getJSONArray("notification");
            for (int i = 0; i<array.length();i++){
                if(!secureList.contains(Integer.parseInt(array.getJSONObject(i).getString("ID")))){
                    secureList.add(Integer.parseInt(array.getJSONObject(i).getString("ID")));
                    notifyItems.add(new NotifyItem(
                            array.getJSONObject(i).getString("ID"),
                            array.getJSONObject(i).getString("Content"),
                            array.getJSONObject(i).getString("Date")
                    ));
                }
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        super.onPostExecute(jsonObject);
    }
}
