package ba.terawatt.etsmostar.CustomItems;

/**
 * Created by Emir on 6.8.2017.
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
