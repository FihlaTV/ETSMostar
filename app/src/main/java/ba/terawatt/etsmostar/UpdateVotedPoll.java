package ba.terawatt.etsmostar;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>25.7.2017. </p></br>
 * <p>Poll Class.</p></br>
 * 
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 * <p>PS..This is funny part of my life...</p>
 */
public interface UpdateVotedPoll {
    void beforeFetcingPoll();
    void UpdateVotedPollUI(JSONObject o) throws JSONException;
    void afterUpdatingPoll();
    void refreshPoll();
}
