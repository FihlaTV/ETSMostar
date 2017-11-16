package ba.terawatt.etsmostar.APIsForFetchingData;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import ba.terawatt.etsmostar.CustomItems.NewsItem;
import ba.terawatt.etsmostar.DrawerFragments.News_Fragment;
import ba.terawatt.etsmostar.R;

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
public class FetchNewsAPI extends AsyncTask<String, Void, JSONObject> {

    private Context context;
    private ProgressDialog progressDialog;
    private List<NewsItem> itemList;
    private RecyclerView.Adapter itemListAdapter;
    private List<Integer> secureList;
    private News_Fragment newsFragment;

    public FetchNewsAPI(Context context){
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        newsFragment = new News_Fragment();
        itemList = newsFragment.getListNews();
        itemListAdapter = newsFragment.getNewsAdapter();
        secureList = newsFragment.getSecureList();

    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        String urlForServerRequest = strings[0];
        String ID = strings[1];




        try {
            URL url = new URL(urlForServerRequest);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String send_data = URLEncoder.encode("ID", "UTF-8")+"="+ URLEncoder.encode(ID, "UTF-8");
            bufferedWriter.write(send_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder response = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null){
                response.append(line + "\n");
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            // zatvaranje progres dialoga
            progressDialog.dismiss();
            String jsonString = response.toString();
            JSONObject json = new JSONObject(jsonString);
            return json;

        } catch (ProtocolException e) {
            e.printStackTrace();
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
            JSONArray array = jsonObject.getJSONArray("NEWS");
            for (int i = 0; i<array.length(); i++){
                if(!CheckIfExisitID(secureList, Integer.parseInt(array.getJSONObject(i).getString("ID")))){
                    secureList.add(Integer.parseInt(array.getJSONObject(i).getString("ID")));
                    itemList.add(new NewsItem(
                            array.getJSONObject(i).getString("ID"),
                            array.getJSONObject(i).getString("Title"),
                            array.getJSONObject(i).getString("Author"),
                            array.getJSONObject(i).getString("Date"),
                            array.getJSONObject(i).getString("Image"),
                            array.getJSONObject(i).getString("URLForShare")
                    ));
                }

            }
            itemListAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onPostExecute(jsonObject);
    }

    private boolean CheckIfExisitID(List<Integer> list, int ID){
        for (int i = 0; i<list.size(); i++){
            if(list.get(i) == ID) return true;
        }
        return false;
    }
}
