package cz.utb.fai.lyricsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{

    private ArrayList<String> artistList = new ArrayList<String>();
    private ArrayList<String> songList = new ArrayList<String>();
    Context context;

    public RecyclerViewAdapter(ArrayList<String> artistList, ArrayList<String> songList, Context context) {
        this.artistList = artistList;
        this.songList = songList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position)
    {
        holder.artistLabel.setText(artistList.get(position));
        holder.songLabel.setText(songList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return songList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout historyItemLayout;
        TextView artistLabel;
        TextView songLabel;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            historyItemLayout = itemView.findViewById(R.id.historyItemLayout);
            artistLabel = itemView.findViewById(R.id.artistLabel);
            songLabel = itemView.findViewById(R.id.songLabel);
        }
    }
}
