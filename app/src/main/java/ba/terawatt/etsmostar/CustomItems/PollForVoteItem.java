package ba.terawatt.etsmostar.CustomItems;

/**
 * Created by Emir on 12.8.2017.
 */
public class PollForVoteItem {
    private String ID;
    private String content;

    public PollForVoteItem(String ID, String content){
        this.ID = ID;
        this.content = content;
    }

    public final String getID() {return  ID;}
    public final String getContent(){return  content;}
}
