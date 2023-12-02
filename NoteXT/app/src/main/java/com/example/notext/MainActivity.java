package com.example.notext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    FloatingActionButton Btn_add;
    RecyclerView recyclerView;
    ImageButton menubtn;
    NoteAdapter noteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Btn_add = findViewById(R.id.btn_add);
        recyclerView = findViewById(R.id.recyview);
        menubtn = findViewById(R.id.btnmenu);

        Btn_add.setOnClickListener((v)-> startActivity(new Intent(MainActivity.this, NoteDeltailActivity.class)));
        menubtn.setOnClickListener((v)-> showMenu());

        setUpRecyclerView();
    }

    private void showMenu() {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this,menubtn);
        popupMenu.getMenu().add("Thoát");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getTitle()=="Thoát") {
                    finish();
                }
                return false;
            }
        });
    }

    void setUpRecyclerView() {
        Query query = Utility.getCollectionReferenceForNote().orderBy("timestamp", Query.Direction.DESCENDING).limit(50);
        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query,Note.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(options,this);
        recyclerView.setAdapter(noteAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        noteAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteAdapter.notifyDataSetChanged();
    }
}