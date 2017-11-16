package ba.terawatt.etsmostar.CustomItems;

/**
 * Created by Emir on 30.7.2017.
 */
public class ReadingViewItem {
    public String heading, author, imageURL, uploadDate, content;

    public ReadingViewItem(String aa, String bb){
        heading = aa;
        author = bb;
    }
    public ReadingViewItem(ReadingViewItem copy){
        heading = copy.getHeading();
        author = copy.getAuthor();
        imageURL = copy.getImageURL();
    }

    public String getHeading() {
        return heading;
    }

    public String getAuthor() {
        return author;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public String getContent() {
        return content;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
