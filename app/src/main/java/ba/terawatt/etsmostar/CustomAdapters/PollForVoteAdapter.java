package ba.terawatt.etsmostar.CustomAdapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import ba.terawatt.etsmostar.APIsForFetchingData.PutVoteForPollAPI;
import ba.terawatt.etsmostar.CustomItems.PollForVoteItem;
import ba.terawatt.etsmostar.R;
import ba.terawatt.etsmostar.UpdateVotedPoll;

import java.util.List;

/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>25.7.2017. </p></br>
 * <p>Pushing data to RecyclerView.</p></br>
 * 
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 * <p>PS..This is funny part of my life...</p>
 */
public class PollForVoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String url = "http://etsmostar.edu.ba/Android/";
    private final String urlforvote = "android_vote_poll.php";

    private List<PollForVoteItem> items;
    private StringBuilder questionText;
    private RadioButton lastChecked = null;
    private String IDForVote = null;
    private UpdateVotedPoll updateVotedPoll;
    public PollForVoteAdapter(List<PollForVoteItem> list, StringBuilder questionText, Fragment activity){
        items = list;
        this.questionText = questionText;
        updateVotedPoll = (UpdateVotedPoll) activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0)
            return new ViewHolderForVoteQuestion(LayoutInflater.from(parent.getContext()).inflate(R.layout.poll_question, parent, false));
        else if(viewType == 1)
            return new ViewHolderForVoteButtons(LayoutInflater.from(parent.getContext()).inflate(R.layout.poll_for_vote_item, parent, false));
        return new ViewHolderVoteButton(LayoutInflater.from(parent.getContext()).inflate(R.layout.button_for_vote, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PollForVoteItem item;
        if(holder.getItemViewType() == 0){
            ViewHolderForVoteQuestion questionHolder = (ViewHolderForVoteQuestion) holder;
            questionHolder.setQuestionText(questionText.toString());
        } else if (holder.getItemViewType() == 1){
            item = items.get(position-1);
            final ViewHolderForVoteButtons buttons = (ViewHolderForVoteButtons) holder;
            buttons.setRadioButtonText(item.getContent());
            buttons.setIDForVote(item.getID());
            buttons.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    RadioButton button = (RadioButton) compoundButton;
                    if(lastChecked !=  null)
                        lastChecked.setChecked(false);
                    lastChecked = button;
                    IDForVote = buttons.getIDForVote();
                }
            });
        } else {
            ViewHolderVoteButton holderVoteButton = (ViewHolderVoteButton) holder;
            holderVoteButton.buttonForVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(IDForVote != null)
                        new PutVoteForPollAPI(updateVotedPoll).execute(url + urlforvote, IDForVote);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return items.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : position >=1 && position < items.size() + 1 ? 1 : 2;
    }
    // dugmad za glasanje
    public class ViewHolderForVoteButtons extends RecyclerView.ViewHolder{
        private RadioButton radioButton;
        private String ID;
        public ViewHolderForVoteButtons(View itemView) {
            super(itemView);
            radioButton = (RadioButton) itemView.findViewById(R.id.radioButtonForVotingPoll);
        }
      public void setRadioButtonText(String text){
          radioButton.setText(text);
      }
        public void setIDForVote(String id){
            ID = id;
        }
        public String getIDForVote(){
            return ID;
        }
    }
    // pitanje
    public class ViewHolderForVoteQuestion extends RecyclerView.ViewHolder{
        public TextView questionTextView;

       public ViewHolderForVoteQuestion(View itemView){
           super(itemView);
           questionTextView = (TextView) itemView.findViewById(R.id.pollQuestion);
       }
        public void setQuestionText(String text){
            questionTextView.setText(text);
        }
    }
    // Dugme za slanje glasa
    public class ViewHolderVoteButton extends RecyclerView.ViewHolder{
        private Button buttonForVote;
        public ViewHolderVoteButton(View itemView){
            super(itemView);
            buttonForVote = (Button) itemView.findViewById(R.id.button_for_vote);
        }
    }
}
