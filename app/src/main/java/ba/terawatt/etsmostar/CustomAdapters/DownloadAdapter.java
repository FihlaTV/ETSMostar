package ba.terawatt.etsmostar.CustomAdapters;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import ba.terawatt.etsmostar.CustomItems.DownloadItem;
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
public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {
    private List<DownloadItem> items;
    private Context context;
    public DownloadAdapter(List<DownloadItem> list, Context context){
        items = list;
        this.context = context;
    }
    @Override
    public DownloadAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.download_item ,parent, false));
    }

    @Override
    public void onBindViewHolder(DownloadAdapter.ViewHolder holder, int position) {
        final DownloadItem item = items.get(position);
        holder.getButton().setText(item.getName());
        holder.getButton().setEnabled(true);
        holder.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // kada klike dugme za download
                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(item.getLinkToDownload()));
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private Button button;
        public ViewHolder(View itemView) {
            super(itemView);

            button = (Button) itemView.findViewById(R.id.downloadButton);
        }
        public Button getButton(){
            return button;
        }
    }
}
