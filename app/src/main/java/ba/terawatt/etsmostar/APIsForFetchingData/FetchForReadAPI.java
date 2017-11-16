package ba.terawatt.etsmostar.APIsForFetchingData;

import android.app.Activity;
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
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import ba.terawatt.etsmostar.R;
import ba.terawatt.etsmostar.UpdateUIInterface;

/**
 * Created by Emir on 29.7.2017.
 */
public class FetchForReadAPI extends AsyncTask<String, Void, JSONObject> {

    private ProgressDialog progressDialog;
    private Context context;
    private WeakReference<Activity> ac;
    UpdateUIInterface delegate;

    public  FetchForReadAPI(Activity context, UpdateUIInterface a){

        this.context = (Context) context;
        progressDialog = new ProgressDialog(context);

        progressDialog.setMessage(context.getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        ac = new WeakReference<Activity>(context);
        delegate = a;
    }
    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        String _url = strings[0];
        String _ID = strings[1];

        try {
            URL url = new URL(_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));
            String data = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(_ID, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
            String line = "";
            StringBuilder returnedData = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null)
                returnedData.append(line + "\n");
            bufferedReader.close();
            httpURLConnection.disconnect();

            //JSONObject jsonObject = new JSONObject(returnedData.toString());
            return new JSONObject(returnedData.toString());
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
        progressDialog.dismiss();
        delegate.updateData(jsonObject);
        super.onPostExecute(jsonObject);
    }
}
