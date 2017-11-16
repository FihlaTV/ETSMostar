package ba.terawatt.etsmostar.CustomAdapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ba.terawatt.etsmostar.CustomItems.EventItem;
import ba.terawatt.etsmostar.R;
import ba.terawatt.etsmostar.ReadingView;
import com.squareup.picasso.Picasso;

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
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private List<EventItem> listEvents;
    private Context context;
    public EventAdapter(List<EventItem> items , Context context){
        this.listEvents = items;
        this.context = context;
    }
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventAdapter.ViewHolder holder, int position) {
        final EventItem event = listEvents.get(position);
        holder.setHeader(event.getHeading());
        holder.setStartDate(event.getDateStart());
        holder.setlocation(event.getlocation());
        holder.setUploadDate(event.getUploadDate());
        holder.setAuthor(event.getAuthor());

        Picasso.with(context)
                .load(event.getImageURL())
                .centerCrop()
                .fit()
                .into(holder.getImageView());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewNews = new Intent(context,ReadingView.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID", event.getID());
                bundle.putString("ViewType", "event");
                viewNews.putExtras(bundle);
                context.startActivity(viewNews);
            }
        });

    }



    @Override
    public int getItemCount() {
        return listEvents.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView header, startDate, location, uploadDate, author;
        private ImageView imageURL;
        private LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);

            header = (TextView) itemView.findViewById(R.id.headingEvent);
            startDate = (TextView) itemView.findViewById(R.id.startDateEvent);
            location = (TextView) itemView.findViewById(R.id.enddateEvent);
            uploadDate = (TextView) itemView.findViewById(R.id.dateEvent);
            author = (TextView) itemView.findViewById(R.id.authorEvent);
            imageURL = (ImageView) itemView.findViewById(R.id.imageEvent);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLyEvent);
        }

        public void setHeader(String header){
            this.header.setText(header);
        }
        public void setStartDate(String startDate){
            this.startDate.setText(startDate);
        }
        public void setlocation(String location){
            this.location.setText(location);
        }
        public void setUploadDate(String upload){
            this.uploadDate.setText(upload);
        }
        public void setAuthor(String author){
            this.author.setText(author);
        }

        public ImageView getImageView() {
            return imageURL;
        }
    }
}
