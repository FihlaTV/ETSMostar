package ba.terawatt.etsmostar.CustomAdapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ba.terawatt.etsmostar.CustomItems.NewsItem;
import ba.terawatt.etsmostar.R;
import ba.terawatt.etsmostar.ReadingView;
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
public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHodler> {

    private List<NewsItem> listItem;
    private Context context;

    public CustomRecyclerViewAdapter(List<NewsItem> listItem, Context context){
        this.listItem = listItem;
        this.context = context;
    }
    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);

        return new ViewHodler(v);
    }

    @Override
    public void onBindViewHolder(ViewHodler holder, int position) {
        final NewsItem news = listItem.get(position);
        holder.SetHeading(news.GetHeading());
        holder.SetAuthor(news.GetAuthor());
        holder.SetDate(news.GetDate());

        Picasso.with(context)
                .load(news.GetImageURL())
                .fit()
                .centerCrop()
                .into(holder.getImageView());

        holder.getURLForShareTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.inflate(R.menu.share_card_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.shareContent) {
                            Intent intent = new Intent();
                            intent.putExtra(Intent.EXTRA_TEXT, news.getURLForShare());
                            intent.setAction(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            context.startActivity(Intent.createChooser(intent, "Podijeli"));
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // dodavanje listenera kada se klikne na vijest
                Bundle bundle = new Bundle();
                bundle.putString("ID", news.GetID());
                bundle.putString("ViewType", "news");
                Intent intent = new Intent(context, ReadingView.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder{
        private TextView textViewHeading;
        private TextView textViewAuthor;
        private TextView textViewDate;
        private ImageView imageURL;
        private LinearLayout layout;
        private TextView URLForShare;

        public ViewHodler(View itemView) {
            super(itemView);

            textViewHeading = (TextView) itemView.findViewById(R.id.headingNews);
            textViewAuthor = (TextView) itemView.findViewById(R.id.authorNews);
            textViewDate = (TextView) itemView.findViewById(R.id.dateNews);
            imageURL = (ImageView) itemView.findViewById(R.id.imageNews);
            layout = (LinearLayout) itemView.findViewById(R.id.linearLy);
            URLForShare = (TextView) itemView.findViewById(R.id.shareMenuNews);
        }

        public void SetHeading(String head){
            textViewHeading.setText(head);
        }
        public void SetAuthor(String author){
            textViewAuthor.setText(author);
        }
        public void SetDate(String date){
            textViewDate.setText(date);
        }
        public ImageView getImageView(){
            return imageURL;
        }
        public TextView getURLForShareTextView() {
            return URLForShare;
        }
    }
}
