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

/* TODO
   Need to make a config file that has the following format.
   site.names          = CNN,FOX,NBC,AP

   site.CNN.enabled   = yes
   site.CNN.searchtag = item
   site.CNN.titletag  = title
   site.CNN.linktag   = link
   site.CNN.descriptiontag = description
   site.CNN.numurls   = 10
   site.CNN.url.1     = "https://www.cnn.com/top_stories.rss
   site.CNN.url.2     = "https://www.cnn.com/world_politics.rss

   site.FOX.enabled   = no
   site.FOX.searchtag = story

 */



public class ResultsActivity extends Activity {

    private ArrayList<String> finalUrls = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        finalUrls.add("http://rss.cnn.com/rss/cnn_topstories.rss");
        finalUrls.add("http://rss.cnn.com/rss/cnn_world.rss");
        finalUrls.add("http://rss.cnn.com/rss/cnn_us.rss");
        finalUrls.add("http://rss.cnn.com/rss/money_latest.rss");
        finalUrls.add("http://rss.cnn.com/rss/cnn_allpolitics.rss");
        finalUrls.add("http://rss.cnn.com/rss/cnn_crime.rss");
        finalUrls.add("http://rss.cnn.com/rss/cnn_tech.rss");
        finalUrls.add("http://rss.cnn.com/rss/cnn_health.rss");
        finalUrls.add("http://rss.cnn.com/rss/cnn_showbiz.rss");
        finalUrls.add("http://rss.cnn.com/rss/cnn_travel.rss");
        finalUrls.add("http://rss.cnn.com/rss/cnn_living.rss");
        finalUrls.add("http://rss.cnn.com/rss/cnn_freevideo.rss");
        finalUrls.add("http://rss.cnn.com/rss/cnn_studentnews.rss");
        finalUrls.add("http://rss.cnn.com/rss/cnn_mostpopular.rss");
        finalUrls.add("http://rss.cnn.com/rss/cnn_latest.rss");
        finalUrls.add("http://rss.ireport.com/feeds/oncnn.rss");
        finalUrls.add("http://rss.cnn.com/rss/cnn_behindthescenes.rss");
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
        /**TODO possibly load config then for each provider pass in RSSProviderInfo into HandleXML
           That way HandleXML can set all the tags it needs and then parse the urls from the param
         */
        HandleXML obj = new HandleXML(finalUrls);
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
