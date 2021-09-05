package com.example.mynote.Adapter;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.mynote.Note;
import com.example.mynote.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.PrimaryKey;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> implements Filterable {

    private List<Note> notes = new ArrayList<>();
    List<Note> noteListAll;
    public OnItemClickListener listener;
    private final static String[] colors = {"#E74C3C", "#2980B9", "#16A085", "#34495E"};

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.Title.setText(currentNote.getTitle());
        holder.Description.setText(currentNote.getDescription());
        holder.Priority.setText(String.valueOf(currentNote.getPriority()));

//        holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(getRandomColor(), null));


//        List<String> colors;
//
//        colors = new ArrayList<String>();
//
//        colors.add("#5E97F6");
//        colors.add("#9CCC65");
//        colors.add("#e6663e");
//
//        Random r = new Random();
//        int i1 = r.nextInt(3 - 0) + 0;

//            Random rnd = new Random();
//            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//            holder.itemView.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        this.noteListAll = new ArrayList<>(notes);
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position) {
        return notes.get(position);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Note> filteredList = new ArrayList<>();
            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(noteListAll);
            } else {
                for (Note note : noteListAll) {
                    if (note.getTitle().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(note);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        //run on ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            notes.clear();
            notes.addAll((Collection<? extends Note>) filterResults.values);
            notifyDataSetChanged();
        }
    };


    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView Title;
        private TextView Description;
        private TextView Priority;
        private CardView cardView;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.textViewTitle);
            Description = itemView.findViewById(R.id.textViewDescription);
            Priority = itemView.findViewById(R.id.textViewPriority);
            cardView = itemView.findViewById(R.id.cardView);

            Priority.setVisibility(View.GONE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.OnItemClick(notes.get(position));
                    }
                }
            });
        }
    }

    private int getRandomColor() {
        List<Integer> colors = new ArrayList<>();

        colors.add(R.color.orange);
        colors.add(R.color.red);
        colors.add(R.color.green);
        colors.add(R.color.purple);
        colors.add(R.color.paste);
        colors.add(R.color.yellow);

        Random r = new Random();
        int num = r.nextInt(colors.size());

        return colors.get(num);
    }

    public interface OnItemClickListener {
        void OnItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
