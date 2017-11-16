package ba.terawatt.etsmostar;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Emir on 8.8.2017.
 */
public interface UpdateVotedPoll {
    void beforeFetcingPoll();
    void UpdateVotedPollUI(JSONObject o) throws JSONException;
    void afterUpdatingPoll();
    void refreshPoll();
}
