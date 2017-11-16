package ba.terawatt.etsmostar.CustomItems;

/**
 * Created by Emir on 15.8.2017.
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
