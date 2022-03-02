package com.example.notessqlite.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notessqlite.R;
import com.example.notessqlite.database.DatabaseHelper;
import com.example.notessqlite.model.Note;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private ArrayList<Note> courseModalArrayList;
    private Context context;
    DatabaseHelper databaseHelper=new DatabaseHelper(context);;


    public NotesAdapter(ArrayList<Note> courseModalArrayList, Context context,DatabaseHelper databaseHelper) {
        this.courseModalArrayList = courseModalArrayList;
        this.context = context;
        this.databaseHelper= databaseHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notes_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // setting data to our views of recycler view.
        Note modal = courseModalArrayList.get(position);
        holder.tv_note.setText(modal.getNote());
        holder.tv_date.setText(modal.getTimestamp());
        holder.iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseModalArrayList.remove(modal);
            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    public Note addNote(Note note) {
        courseModalArrayList.add(note);
        databaseHelper.insertNote(note);
        notifyDataSetChanged();
        return note;
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return courseModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView tv_note,tv_date;
        public ImageView iv_remove;
        public RelativeLayout view_background, view_foreground;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our views with their ids.
            tv_note = itemView.findViewById(R.id.tv_note);
            tv_date = itemView.findViewById(R.id.tv_date);
            iv_remove = itemView.findViewById(R.id.iv_remove);
            view_foreground = itemView.findViewById(R.id.view_foreground);
            view_foreground = itemView.findViewById(R.id.view_foreground);
        }
    }

}
