package ba.terawatt.etsmostar.APIsForFetchingData;

import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import ba.terawatt.etsmostar.UpdateVotedPoll;

/**
 * Created by Emir on 14.8.2017.
 */
public class PutVoteForPollAPI extends AsyncTask<String, Void, Boolean> {

    private UpdateVotedPoll updateVotedPoll;

    public PutVoteForPollAPI(UpdateVotedPoll updateVotedPoll){
        this.updateVotedPoll = updateVotedPoll;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        String _url = strings[0];
        String _id = strings[1];
        try {
            URL url = new URL(_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));
            String ID = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(_id, "UTF-8");
            bufferedWriter.write(ID);
            bufferedWriter.flush();
            bufferedWriter.close();
            httpURLConnection.getInputStream().close();
            httpURLConnection.disconnect();

            return true;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPreExecute() {
        updateVotedPoll.beforeFetcingPoll();
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean isOK) {
       if(isOK){
           updateVotedPoll.afterUpdatingPoll();
           updateVotedPoll.refreshPoll();
       }else{
           updateVotedPoll.afterUpdatingPoll();
       }

    }
}
