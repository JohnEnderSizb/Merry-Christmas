package com.andrew.merrychristmas;;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.andrew.merrychristmas.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    DBHelper dbHelper;
    Functions functions;
    EditText theTitleEditText;
    EditText theEntryEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Diary");

        theEntryEditText = findViewById(R.id.theEntryEditText);
        theTitleEditText = findViewById(R.id.theTitleEditText);

        functions = new Functions();
        dbHelper = new DBHelper(this);

        webView = (WebView) findViewById(R.id.thewv);

        WebSettings webSetting = webView.getSettings();

        //webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(false);

        webView.addJavascriptInterface(this, "JSReceiver");

        webView.loadUrl("file:///android_asset/diary.html");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_message) {
            startActivity(new Intent(MainActivity.this, Repeat.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void save(View view) {
        String theEnteredTitle = theTitleEditText.getText().toString();
        String theEnteredEntry = theEntryEditText.getText().toString();;
        long theCurrentTime = functions.getTimeStamp();

        if(!theEnteredTitle.isEmpty() & !theEnteredTitle.isEmpty()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("entry_title", theEnteredTitle);
            values.put("the_entry", theEnteredEntry);
            values.put("entry_time", theCurrentTime);

            long the_id =  db.insert("entries", null, values);

            theEntryEditText.setText("");
            theTitleEditText.setText("");

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

            if(theEnteredTitle.length() > 24) {
                theEnteredTitle = theEnteredTitle.substring(0, 24) + "...";
            }


            Timestamp theTS = new Timestamp(theCurrentTime);

            Date the_date = new Date(theTS.getTime());


            String st2 = "{\"theEnteredEntry\":\"" + theEnteredEntry + "\", \"the_entered_title\":\"" + theEnteredTitle +"\", \"the_entry_time\":\"" + the_date + "\"}";


            webView.loadUrl("javascript:showNew('"+ st2 +"')");
        }

    }

    @JavascriptInterface
    public  String loadEntrys(){

        String theList = "{ \"entries\": [";

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT DISTINCT * FROM entries ORDER BY entry_time DESC";

        Cursor cursor = db.rawQuery(query, null);

        int rows = cursor.getCount();

        if(rows > 0) {
            while (cursor.moveToNext()) {
                String the_entry = cursor.getString(cursor.getColumnIndex("the_entry"));
                String entry_title = cursor.getString(cursor.getColumnIndex("entry_title"));
                long the_id = cursor.getLong(cursor.getColumnIndex("id"));
                long entry_time = cursor.getLong(cursor.getColumnIndex("entry_time"));

                if(entry_title.length() > 24) {
                    entry_title = entry_title.substring(0, 24) + "...";
                }

                /*
                if(the_entry.length() > 40) {
                    the_entry = the_entry.substring(0, 24) + "...";
                }*/

                the_entry = sanitize(the_entry);
                entry_title= sanitize(entry_title);

                Timestamp theTS = new Timestamp(entry_time);

                Date the_date = new Date(theTS.getTime());

                //String ts = (String) the_date;


                String toJS = "{ \"the_entry\":\"" + the_entry + "\", \"entry_title\":\"" + entry_title + "\", \"the_id\":\"" + the_id  + "\", \"entry_time\":\"" + the_date + "\" },";
                theList += toJS;

            }

            theList = theList.substring(0, theList.length() - 1);

            theList += " ], \"outcome\":\"found\" }";

        }

        else {

            theList += " ], \"outcome\":\"none\" }";
        }

        cursor.close();

        //Log.d("Test", theList + "Beep bop boop");
        return theList;

    }

public String sanitize(String theInput) {

    String output = "";

    int length = theInput.length();
    for ( int i = 0; i < length; i++ ) {
        char current = theInput.charAt(i);
        if(current == '\"') {
            current = '\'';
        }

        if(current == '\n') {
            current = ' ';
        }

     output += current;
    }

    return output;

    }

}
