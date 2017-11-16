package ba.terawatt.etsmostar.CustomItems;

/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>25.7.2017. </p></br>
 * <p>Custom Data Objects.</p></br>
 * 
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 * <p>PS..This is funny part of my life...</p>
 */
public class NotifyItem  {
    private String notifyText;
    private String NotifyDate;
    private String NotifyID;

    public NotifyItem(String notifyID,String notifyText, String notifyDate) {
        this.NotifyDate = notifyDate;
        this.NotifyID = notifyID;
        this.notifyText = notifyText;
    }

    public String getNotifyText() {
        return notifyText;
    }

    public String getNotifyID() {
        return NotifyID;
    }

    public String getNotifyDate() {
        return NotifyDate;
    }


}
