package com.example.notessqlite.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.notessqlite.R;
import com.example.notessqlite.adapter.NotesAdapter;
import com.example.notessqlite.database.DatabaseHelper;
import com.example.notessqlite.helper.RecyclerItemTouchHelper;
import com.example.notessqlite.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText et_notes;
    private FloatingActionButton bt_add;
    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private ArrayList<Note> courseModalArrayList = new ArrayList<Note>();
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        databaseHelper = new DatabaseHelper(this);
        bt_add = findViewById(R.id.bt_add);
        recyclerView = findViewById(R.id.recyclerView);
        buildRecyclerView();
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogButtonClicked();
            }
        });

    }

    public void showAlertDialogButtonClicked() {
        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);builder.setTitle("New Note");
        final View customLayout = getLayoutInflater().inflate(R.layout.item_dialog, null);
        builder.setView(customLayout);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                et_notes = customLayout.findViewById(R.id.et_notes);

                courseModalArrayList.add(adapter.addNote(getNote(et_notes.getText().toString())));

            }});
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }});
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @SuppressLint("SimpleDateFormat")
    public Note getNote(String text) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateText = dateFormat.format(date);
        return new Note(9 ,text,dateText);
    }

    private void buildRecyclerView(){
        adapter = new NotesAdapter(getNotes(),this, databaseHelper);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, new RecyclerItemTouchHelper.RecyclerItemTouchHelperListener() {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int adapterPosition) {

            }
        });
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }
    private ArrayList<Note> getNotes() {
        return databaseHelper.getAllNotes();
    }
}