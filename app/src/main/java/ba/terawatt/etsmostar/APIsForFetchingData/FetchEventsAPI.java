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
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import ba.terawatt.etsmostar.CustomItems.EventItem;
import ba.terawatt.etsmostar.R;

/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>25.7.2017. </p></br>
 * <p>Sending request to -> php <- script and fetching data in JSON form.</p></br>
 * <p>Data is put in recycler view and displaying on screen.</p></br>
 * 
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 * <p>PS..This is funny part of my life...</p>
 */
public class FetchEventsAPI extends AsyncTask<String, Void, JSONObject> {
    private ProgressDialog progressDialog;
    private Context context;
    private List<EventItem> listOfEvents;
    private RecyclerView.Adapter adapter;
    private List<Integer> secureList;
    public FetchEventsAPI(List<EventItem> list, Context context, RecyclerView.Adapter adapter, List<Integer> secure){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(context.getResources().getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
        this.context = context;
        this.listOfEvents = list;
        this.adapter = adapter;
        this.secureList = secure;
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
            //kreiramo URL na osnovu primljenog string url-a kao parametar
            URL url = new URL(_url);
            // na osnovu kreiranog URL otvaramo konekciju
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // saljemo kao atribut vrstu zahtjeva
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            //kreiramo output stream da bi mogli serveru poslati podatke
            OutputStream outputStream = httpURLConnection.getOutputStream();
            // kreiramo buffer za slanje podataka serveru
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String postRequest = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(ID, "UTF-8");
            bufferedWriter.write(postRequest);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            //kreiramo input stream za dohvatanje podataka sa servera
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder response = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null)
                response.append(line + "\n");
            //zatvaranje loadera
            progressDialog.dismiss();
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            String dataForCreatingJSON = response.toString();
            JSONObject json = new JSONObject(dataForCreatingJSON);

            return json;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        progressDialog.dismiss();
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        try {
            JSONArray array = jsonObject.getJSONArray("events");

            for (int i = 0; i<array.length();i++){
                if(!secureList.contains(Integer.parseInt(array.getJSONObject(i).getString("ID")))){
                    secureList.add(Integer.parseInt(array.getJSONObject(i).getString("ID")));
                    listOfEvents.add(new EventItem(
                            array.getJSONObject(i).getString("ID"),
                            array.getJSONObject(i).getString("Title"),
                            array.getJSONObject(i).getString("startDate"),
                            array.getJSONObject(i).getString("Place"),
                            array.getJSONObject(i).getString("Author"),
                            array.getJSONObject(i).getString("postDate"),
                            array.getJSONObject(i).getString("Image")
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
