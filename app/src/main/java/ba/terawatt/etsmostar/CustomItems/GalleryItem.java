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
public class GalleryItem {
    private String ID;
    private String imageURL;
    private String name;
    private String urlForShare;
    public GalleryItem(String ID, String imageURL){
        this.ID = ID;
        this.imageURL = imageURL;
        name = null;
        urlForShare = null;
    }
    public GalleryItem(String ID, String imageURL, String name, String URLForShare){
        this.ID = ID;
        this.imageURL = imageURL;
        this.name = name;
        this.urlForShare = URLForShare;
    }
    public GalleryItem(GalleryItem item){
        ID = item.getID();
        imageURL = item.getImageURL();
        name = item.getName();
        urlForShare = item.getURLForShare();
    }
    public String getImageURL(){return imageURL;}
    public String getID(){return ID;}
    public String getName(){return name;}
    public String getURLForShare(){return  urlForShare;}
}
