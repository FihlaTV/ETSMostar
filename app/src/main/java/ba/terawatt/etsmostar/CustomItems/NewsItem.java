package ba.terawatt.etsmostar.CustomItems;

/**
 * Created by Emir Veledar on 24.7.2017.
 */
public class NewsItem {
    private final String ID;
    private String heading;
    private String author;
    private String date;
    private String imageURL;
    private String URLForShare;
    public NewsItem(String id, String heading, String author, String date, String imageURL, String URLForShare) {
        ID = id;
        this.heading = heading;
        this.author = author;
        this.date = date;
        this.imageURL = imageURL;
        this.URLForShare = URLForShare;
    }
    public String GetHeading(){
        return heading;
    }
    public String GetAuthor(){
        return  author;
    }
    public String GetDate(){
        return date;
    }
    public final String GetID(){
        return ID;
    }
    public String GetImageURL(){
        return imageURL;
    }
    public String getURLForShare() {
        return URLForShare;
    }
    public void SetHeading(final String heading){this.heading = heading;}
    public void SetAuthor(final String author){this.author = author;}
    public void SetDate(final String date){this.date = date;}
    public void SetImageURL(final String imageURL){this.imageURL = imageURL;}
    public void setURLForShare(String URLForShare) {
        this.URLForShare = URLForShare;
    }
}
