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
