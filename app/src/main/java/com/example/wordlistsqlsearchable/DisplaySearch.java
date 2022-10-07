package com.example.wordlistsqlsearchable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.SearchView;

import java.util.ArrayList;


public class DisplaySearch extends AppCompatActivity implements SearchView.OnQueryTextListener {
WordListOpenHelper db;
WordItem wordItem;
Context context;
RecyclerView recyclerView;
private ArrayList<WordItem> wordItemArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}