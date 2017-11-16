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
