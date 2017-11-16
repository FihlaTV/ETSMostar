package ba.terawatt.etsmostar.CustomItems;

/**
 * Created by Emir on 1.8.2017.
 */
public class DownloadItem {
    private String ID;
    private String name;
    private String linkToDownload;

    public DownloadItem(String ID, String name, String linkToDownload) {
        this.ID = ID;
        this.name = name;
        this.linkToDownload = linkToDownload;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getLinkToDownload() {
        return linkToDownload;
    }
}
