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
