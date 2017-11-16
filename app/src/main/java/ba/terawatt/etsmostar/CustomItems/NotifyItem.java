package ba.terawatt.etsmostar.CustomItems;

/**
 * Created by Emir on 28.7.2017.
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
