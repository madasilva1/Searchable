package com.example.wordlistsqlsearchable;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class SearchActivity extends AppCompatActivity {
    private static final String TAG = EditWordActivity.class.getSimpleName();

    private WordListOpenHelper mDB;

    private EditText mEditWordView;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

         mDB = new WordListOpenHelper(this);

         mEditWordView = ((EditText) findViewById(R.id.search_word));
         mTextView = ((TextView) findViewById(R.id.search_result));
    }

    // Click handler for Search button.
    public void showResult(View view) {
        ArrayAdapter<Object> nameadapter;
        final ListView namelist = (ListView) findViewById(R.id.lv);
        nameadapter = new ArrayAdapter<Object>(getApplicationContext(), android.R.layout.simple_list_item_1);
        namelist.setAdapter(nameadapter);


     //   Intent intent = new Intent(getBaseContext(), DisplaySearch.class);
       // startActivity(intent);
      String word = mEditWordView.getText().toString();
        mTextView.setText("Result for " + word + ":\n\n");
        mTextView.setMovementMethod(new ScrollingMovementMethod());
        // Search for the word in the database.
        Cursor cursor = mDB.search(word);
        // You must move the cursor to the first item.
        cursor.moveToFirst();
        // Only process a non-null cursor with rows.
        if (cursor != null & cursor.getCount() > 0) {
            int index;
            String result;
            // Iterate over the cursor, while there are entries.
            do {
                // Don't guess at the column index. Get the index for the named column.
                index = cursor.getColumnIndex(WordListOpenHelper.KEY_WORD);
                // Get the value from the column for the current cursor.
                result = cursor.getString(index);
                // Add result to what's already in the text view.
                nameadapter.add(result + "\n");
            } while (cursor.moveToNext());
            cursor.close();
            mEditWordView.setText("");
        } else {
            mTextView.append(getString(R.string.no_result));

        }
    }
    }
