package ba.terawatt.etsmostar.APIsForFetchingData;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

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

import ba.terawatt.etsmostar.R;
import ba.terawatt.etsmostar.UpdateUIInterface;

/**
 * Created by Emir on 22.8.2017.
 */
public class FetchAlbumPhotosAPI extends AsyncTask<String, Void, JSONObject> {
    private UpdateUIInterface updateUI;
    private ProgressDialog progressDialog;
    public FetchAlbumPhotosAPI(UpdateUIInterface updateUI, Context context){
        this.updateUI = updateUI;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.loading));
    }
    @Override
    protected JSONObject doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            String dataToSend = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8");
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));
            bufferedWriter.write(dataToSend);
            bufferedWriter.flush();
            bufferedWriter.close();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
            String line = "";
            StringBuilder response = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null)
                response.append(line + "\n");
            bufferedReader.close();

           // httpURLConnection.getOutputStream().close();
           // httpURLConnection.getInputStream().close();
            httpURLConnection.disconnect();

            return new JSONObject(response.toString());

        } catch (MalformedURLException e) {
          //  e.printStackTrace();
        } catch (IOException e) {
          //  e.printStackTrace();
        } catch (JSONException e) {
           // e.printStackTrace();
        }
        return null;
    }


    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
        progressDialog.dismiss();
        updateUI.updateData(jsonObject);
    }
}
