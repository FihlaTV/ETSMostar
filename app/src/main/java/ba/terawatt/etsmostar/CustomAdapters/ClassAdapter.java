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

import ba.terawatt.etsmostar.CustomItems.ClassItem;
import ba.terawatt.etsmostar.R;
import ba.terawatt.etsmostar.ReadingView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Emir on 30.7.2017.
 */
public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder>{
    private List<ClassItem> smjerovi;
    private Context context;
    public ClassAdapter(List<ClassItem> list, Context context){
        smjerovi = list;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ClassItem item = smjerovi.get(position);
        holder.setHeading(item.getHeading());
        Picasso
                .with(context)
                .load(item.getImageURL())
                .fit()
                .centerCrop()
                .into(holder.getImageView());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("ID", item.getID());
                bundle.putString("ViewType", "class");
                Intent intent = new Intent(context, ReadingView.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return smjerovi.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        private LinearLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.classImage);
            textView = (TextView) itemView.findViewById(R.id.headingClass);
            layout = (LinearLayout) itemView.findViewById(R.id.classLayout);
        }
        public void setHeading(String heading){
            this.textView.setText(heading);
        }

        public ImageView getImageView() {
            return imageView;
        }
    }
}
