package ba.terawatt.etsmostar.CustomAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import ba.terawatt.etsmostar.CustomItems.PollVotedItem;
import ba.terawatt.etsmostar.R;

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
public class PollVotedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PollVotedItem> items;
    private Context context;
    private StringBuilder question;
    public PollVotedAdapter(List<PollVotedItem> list, Context context, StringBuilder question){
        items = list;
        this.context = context;
        this.question = question;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0)
            return new ViewHolderQuestion(LayoutInflater.from(parent.getContext()).inflate(R.layout.poll_question, parent, false));
        else
            return new ViewHolderAnswers(LayoutInflater.from(parent.getContext()).inflate(R.layout.poll_voted_item, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PollVotedItem item;
        if(holder.getItemViewType() == 0){
            ViewHolderQuestion holderQuestion = (ViewHolderQuestion) holder;
            holderQuestion.setQuestion(question.toString());
        } else {
            item = items.get(position-1);
            ViewHolderAnswers holderAnswers = (ViewHolderAnswers) holder;
            holderAnswers.setContent(item.getContent());
            holderAnswers.setProgressBarPercentage(item.getPercent());
            holderAnswers.setContentPercentage(item.getPercent());
        }

    }

    @Override
    public int getItemCount() {
        return items.size()+1;
    }

    public class ViewHolderAnswers extends RecyclerView.ViewHolder {
        private TextView content;
        private ProgressBar progressBar;
        private TextView contentProgress;
        public ViewHolderAnswers(View itemView) {
            super(itemView);

            content = (TextView) itemView.findViewById(R.id.contentPoll);
            progressBar = (ProgressBar) itemView.findViewById(R.id.percentagePoll);
            contentProgress = (TextView) itemView.findViewById(R.id.contentPercentPoll);
        }

        public void setContent(String content){
            this.content.setText(content);
        }
        public  void setProgressBarPercentage(float percent) {
            progressBar.setProgress((int)percent);
        }
        public  void setContentPercentage(float percent) {
            contentProgress.setText(String.valueOf(percent) + "%");
        }
    }
    public class ViewHolderQuestion extends RecyclerView.ViewHolder{
        private TextView question;
        public ViewHolderQuestion(View itemView) {
            super(itemView);
            question = (TextView) itemView.findViewById(R.id.pollQuestion);
        }
        public void setQuestion(String text){
            question.setText(text);
        }

    }
}
