package com.example.facta.myapplication;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by facta on 11/21/2014.
 */
public class SiteConfig {
    private XmlResourceParser configParser;
    ArrayList<RSSProviderInfo> RSSProviderInfos;

    public SiteConfig()
    {
        RSSProviderInfos = new ArrayList<RSSProviderInfo>();
    }

    public ArrayList<RSSProviderInfo> getProviers()
    {
        return RSSProviderInfos;
    }

    public void loadConfig(Context context, int resourceId)
    {
        configParser = context.getResources().getXml(resourceId);
        RSSProviderInfos.clear();

        RSSProviderInfo providerInfo = new RSSProviderInfo();

        ArrayList<String> providerNames = loadProviders(configParser);
    }

    private ArrayList<String> loadProviders(XmlResourceParser parser) {

        ArrayList<String> names = new ArrayList<String>();

        try {
            parser.next();
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG
                        && parser.getName().equalsIgnoreCase("siteproviders")) {
                    String attrValue = parser.getAttributeValue(null,
                            "attribute");
                    names = new ArrayList<String>(Arrays.asList(parser.getAttributeValue(null, "providers").split(",")));
                    break;
                }
                eventType = parser.next();
            }
        }
        catch (Exception e)
        {
            Log.d("loadProviders", "Caught an exeption: " + e.toString());
            e.printStackTrace();
        }

        return names;
    }


}
