package com.example.facta.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;


public class MyActivity extends Activity {

    //private String finalUrl = "http://tutorialspoint.com/android/sampleXML.xml";
    private ArrayList<String> finalUrls;

    private String finalUrl = "http://rss.cnn.com/rss/cnn_topstories.rss";
    private HandleXML obj;
    private EditText title, link, description;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        title = (EditText) findViewById(R.id.editText1);
        link = (EditText) findViewById(R.id.editText2);
        description = (EditText) findViewById(R.id.editText3);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    /**
    public void fetch(View view)
    {
        obj = new HandleXML(new ArrayList<String>());
        obj.fetchXML();
        while(!obj.parsingComplete);
        ArrayList<RSSInfo> rssInfos = obj.getRssInfos();
        title.setText(rssInfos.get(0).getTitle());
        link.setText(rssInfos.get(0).getLink());
        description.setText(rssInfos.get(0).getDescription());
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
