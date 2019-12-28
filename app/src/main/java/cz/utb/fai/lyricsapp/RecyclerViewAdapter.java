package cz.utb.fai.lyricsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.HistoryViewHolder> {

    private Context context;
    private ArrayList<String> historyItemList = new ArrayList<String>();

    public RecyclerViewAdapter(Context context, ArrayList<String> historyItemList) {
        this.context = context;
        this.historyItemList = historyItemList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        HistoryViewHolder viewHolder = new HistoryViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        String lyricsFileName = "lyricsFile";
        String countKeyInternal = "count";

        SharedPreferences sharedPreferences = context.getSharedPreferences(lyricsFileName, MODE_PRIVATE);
        // Jedna položka má 3 záznamy
        int count = sharedPreferences.getInt(countKeyInternal, 0) / 3;

        return count;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout historyItem;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            historyItem = itemView.findViewById(R.id.historyItem);
        }
    }
}
