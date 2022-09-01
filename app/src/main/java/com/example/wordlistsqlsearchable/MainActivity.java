package com.example.wordlistsqlsearchable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int WORD_ADD = -1;
    public static final int WORD_EDIT = 1;

    private WordListOpenHelper mDB;
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;
    EditText editTextname;
    Button addData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // AddData();
        editTextname = (EditText) findViewById(R.id.editText_name);
       mDB = new WordListOpenHelper(this);

       Button addaData = (Button)findViewById(R.id.Add_Data);
        addaData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextname.getText().toString();

                WordListOpenHelper wordListOpenHelper = new WordListOpenHelper(MainActivity.this);
                wordListOpenHelper.insertData(name);
                // intent = new Intent(MainActivity.this,Details.class);
                // startActivity(intent);
                Toast.makeText(getApplicationContext(), "Details Inserted Successfully", Toast.LENGTH_SHORT).show();
                // Starts empty edit activity.
               // Intent intent = new Intent(getBaseContext(),EditWordActivity.class);
               //startActivity(intent);
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

   // public void AddData() {
  // Button addaData = (Button) findViewById(R.id.Add_Data);


                       /* boolean isInserted = myDb.insertData(editRoom.getText().toString(),
                                editRoom.getText().toString(),
                                editDate.getText().toString() );
                        if(isInserted == true)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                        */





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
