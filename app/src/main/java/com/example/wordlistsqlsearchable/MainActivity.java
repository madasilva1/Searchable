package com.example.wordlistsqlsearchable;

import static android.graphics.PorterDuff.*;
import static android.graphics.PorterDuff.Mode.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    public static final int WORD_ADD = -1;
    public static final int WORD_EDIT = 1;
    private static final String TAG = MainActivity.class.getSimpleName();
    EditText editTextname,editText_date;
    Button addDate;
    private WordListOpenHelper mDB;
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // AddData();
        editTextname = (EditText) findViewById(R.id.editText_name);
        editText_date = (EditText)findViewById(R.id.editText_date);

       mDB = new WordListOpenHelper(this);

       Button adddate = (Button)findViewById(R.id.Add_Date);
       adddate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String date = editText_date.getText().toString();

               WordListOpenHelper wordListOpenHelper = new WordListOpenHelper(MainActivity.this);
               if(!TextUtils.isEmpty(date)) {
                   wordListOpenHelper.insertData(date);
                   Toast.makeText(getApplicationContext(), "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
               }
               else{
                   Toast.makeText(getApplicationContext(),"Not Inserted, enter some data",Toast.LENGTH_LONG).show();
               }

               editText_date.setText("");
           }
       });

       Button addaData = (Button)findViewById(R.id.Add_Data);
        addaData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextname.getText().toString();

                WordListOpenHelper wordListOpenHelper = new WordListOpenHelper(MainActivity.this);
                if(!TextUtils.isEmpty(name)) {
                    wordListOpenHelper.insertData(name);
                    Toast.makeText(getApplicationContext(), "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Not Inserted, enter some data",Toast.LENGTH_LONG).show();
                }

                editTextname.setText("");
            }
        });
        Button viewlist = (Button) findViewById(R.id.view);
        viewlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), DisplayList.class);
               startActivity(intent);
            }
        });
    }
         /**
         * Inflates the menu, and adds items to the action bar if it is present.
         *
         * @param   menu    Menu to inflate.
         * @return Returns true if the menu inflated.
         */
        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        /**
         * Handles app bar item clicks.
         *
         * @param item  Item clicked.
         * @return Returns true if one of the defined items was clicked.
         */
        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            switch (item.getItemId()) {
                case R.id.action_search:
                    // Starts search activity.
                    Intent intent = new Intent(getBaseContext(), com.example.wordlistsqlsearchable.SearchActivity.class);
                    startActivity(intent);
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }

        public void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == WORD_EDIT) {
                if (resultCode == RESULT_OK) {
                    String word = data.getStringExtra(EditWordActivity.EXTRA_REPLY);

                    // Update the database.
                    if (!TextUtils.isEmpty(word)) {
                        int id = data.getIntExtra(WordListAdapter.EXTRA_ID, -99);

                        if (id == WORD_ADD) {
                            mDB.insert(word);
                        } else if (id >= 0) {
                            mDB.update(id, word);
                        }
                        // Update the UI.
                        mAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                R.string.empty_word_not_saved,
                                Toast.LENGTH_LONG).show();
                    }
                }
            }

        }
    }
