package com.example.facta.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;


public class ResultsActivity extends Activity {

    private String finalUrl = "http://rss.cnn.com/rss/cnn_topstories.rss";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        this.fetch();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetch()
    {
        HandleXML obj = new HandleXML(finalUrl);
        obj.fetchXML();
        while(!obj.parsingComplete);
        final ArrayList<RSSInfo> rssInfos = obj.getRssInfos();
        final TableLayout tableLayout = (TableLayout) findViewById(R.id.results_table);

        Log.d("fetch", "Size of rssinfos " + rssInfos.size());

        for(int i=0; i < rssInfos.size(); i++)
        {
            final int index = i;
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            //Get information from infos
            final TextView textView = new TextView(this);
            textView.setText(rssInfos.get(index).getTitle());
            textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            tableRow.setClickable(true);
            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(rssInfos.get(index).getLink()));
                    startActivity(intent);
                }
            });

            tableRow.addView(textView);
            tableLayout.addView(tableRow);

        }

        Log.d("fetch", "tableLayout = " + tableLayout.toString());

    }

}
