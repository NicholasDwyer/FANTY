package com.example.facta.myapplication;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;



import android.util.Log;
import android.widget.LinearLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;


/**
 * Created by facta on 10/31/2014.
 */
public class HandleXML {

    private String title = "title";
    private String link = "link";
    private String description = "description";

    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;

    public HandleXML(String url)
    {
        this.urlString = url;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public void parseXMLAndStoreIt(XmlPullParser myParser, LinearLayout resultsView)
    {
        Log.d("parseXMLAndStoreIt","We are in parse and store");
        int event;
        String text = null;
        boolean prelimfound = false;
        boolean item = false;
        int count = 0;

        try {
            event = myParser.getEventType();


            while(event != XmlPullParser.END_DOCUMENT && prelimfound == false)
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
                                title = text;
                                count++;
                            } else if (name.equals("link")) {
                                link = text;
                                count++;
                            } else if (name.equals("description")) {
                                description = text;
                                count++;
                            }
                            if(count == 3) prelimfound = true;
                        }
                        if(name.equals("item")) { item = true; }
                        break;
                }

                event = myParser.next();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void fetchXML(final LinearLayout viewById)
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
                    parseXMLAndStoreIt(myparser, viewById);
                    stream.close();
                    parsingComplete = false;
                }
                catch (Exception e)
                {
                    /* Do nothing */
                }
            }
        });

        thread.start();
    }

}
