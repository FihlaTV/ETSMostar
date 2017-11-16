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
public class PollVotedItem {
    private String content;
    private float percent;

    public PollVotedItem(String content, float  percent){
        this.content = content;
        this.percent = percent;
    }

    public String getContent() {
        return content;
    }

    public float getPercent() {
        return percent;
    }
}
