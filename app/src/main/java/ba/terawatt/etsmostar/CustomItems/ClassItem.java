package ba.terawatt.etsmostar.CustomItems;

/**
 * Created by Emir on 30.7.2017.
 */
public class ClassItem {
    private String heading;
    private String imageURL;
    private String ID;

    public ClassItem(String ID, String heading, String imageURL) {
        this.ID = ID;
        this.heading = heading;
        this.imageURL = imageURL;
    }

    public String getHeading() {
        return heading;
    }
    public String getID() {
        return ID;
    }
    public String getImageURL() {
        return imageURL;
    }
}
