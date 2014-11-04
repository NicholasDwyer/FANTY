package com.example.facta.myapplication;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;



import android.util.Log;
import android.widget.LinearLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;


/**
 * Created by facta on 10/31/2014.
 */
public class HandleXML {

    private ArrayList<RSSInfo> rssInfos;

    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = false;

    public HandleXML(String url)
    {
        this.urlString = url;
        rssInfos = new ArrayList<RSSInfo>();
    }


    public void parseXMLAndStoreIt(XmlPullParser myParser)
    {
        Log.d("parseXMLAndStoreIt","We are in parse and store");
        int event;
        String text = null;
        boolean prelimfound = false;
        boolean item = false;
        int count = 0;
        RSSInfo info = new RSSInfo();

        try {
            event = myParser.getEventType();


            while(event != XmlPullParser.END_DOCUMENT && !prelimfound)
            {
                String name = myParser.getName();

                switch (event)
                {
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(item) {
                            if (name.equals("title")) {
                                info.setTitle(text);
                                Log.d("Parse", "Found title " + info.getTitle());
                                count++;
                            } else if (name.equals("link")) {
                                info.setLink(text);
                                Log.d("Parse", "Found link " + info.getLink());
                                count++;
                            } else if (name.equals("description")) {
                                info.setDescription(text);
                                Log.d("Parse", "Found description " + info.getDescription());
                                count++;
                            }
                            if(count == 3) prelimfound = true;
                        }
                        if(name.equals("item")) { item = true; }
                        Log.d("Parse", "at end tag and name = " + name);
                        break;
                }

                event = myParser.next();
            }
        }
        catch (Exception e)
        {
            Log.d("Parse", "Caught an exeption: " + e.toString() );
            e.printStackTrace();
        }

        rssInfos.add(info);
    }

    public void fetchXML()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Log.d("fetchXML", urlString);
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    if (conn != null) {
                        Log.d("fetchXML","The connection is not null");
                    }
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /*milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);

                    /* Starts the query */
                    conn.connect();

                    InputStream stream = conn.getInputStream();

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();
                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, null);
                    parseXMLAndStoreIt(myparser);
                    stream.close();
                    parsingComplete = true;
                }
                catch (Exception e)
                {
                    /* Do nothing */
                }
            }
        });

        thread.start();
    }

    public ArrayList<RSSInfo> getRssInfos() { return rssInfos; }

}
