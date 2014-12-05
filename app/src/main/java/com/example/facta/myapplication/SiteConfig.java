package com.example.facta.myapplication;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by facta on 11/21/2014.
 */
public class SiteConfig {
    private XmlResourceParser configParser;
    ArrayList<RSSProviderInfo> RSSProviderInfos;
    ArrayList<String> RSSProviderNames;

    public SiteConfig()
    {
        RSSProviderInfos = new ArrayList<RSSProviderInfo>();
        RSSProviderNames = new ArrayList<String>();
    }

    public ArrayList<RSSProviderInfo> getProvierInfos()
    {
        return RSSProviderInfos;
    }
    public ArrayList<String> getProvierNames()
    {
        return RSSProviderNames;
    }

    public void loadConfig(Context context, int resourceId)
    {
        configParser = context.getResources().getXml(resourceId);
        RSSProviderInfos.clear();

        RSSProviderInfo providerInfo = new RSSProviderInfo();

        ArrayList<String> providerNames = loadProviders(configParser);

        for(int i=0; i < providerNames.size(); i++)
        {
            providerInfo = LoadProviderInfo(configParser, providerNames.get(i));
            if(providerInfo != null && providerInfo.isComplete()) {
                RSSProviderInfos.add(providerInfo);
            }
        }
    }

    private RSSProviderInfo LoadProviderInfo(XmlResourceParser parser, String provider) {

        RSSProviderInfo info = new RSSProviderInfo();
        String attrName = new String();

        try {
            parser.next();
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG
                        && parser.getName().equalsIgnoreCase("site" )) {

                    attrName = parser.getAttributeValue(null, "name");
                    
                    if(attrName.equalsIgnoreCase(provider + ".enabled"))
                    {
                        String value = parser.getAttributeValue(null, "value");
                        if(value.equalsIgnoreCase("no")) {
                            Log.d("LoadProviderInfo", "value does equal no");
                            return null; //If it's not enabled don't include it in the config
                        }
                    }
                    else if(attrName.equalsIgnoreCase(provider + ".elementTag"))
                    {
                        info.setElementTag(parser.getAttributeValue(null, "value"));
                    }
                    else if(attrName.equalsIgnoreCase(provider + ".titleTag"))
                    {
                        info.setTitleTag(parser.getAttributeValue(null, "value"));
                    }
                    else if(attrName.equalsIgnoreCase(provider + ".linkTag"))
                    {
                        info.setLinkTag(parser.getAttributeValue(null, "value"));
                    }
                    else if(attrName.equalsIgnoreCase(provider + ".descriptionTag"))
                    {
                        info.setDescriptionTag(parser.getAttributeValue(null,"value"));
                    }
                    else if(attrName.equalsIgnoreCase(provider + ".url"))
                    {
                        info.addUrl(parser.getAttributeValue(null, "value"));
                    }
                 //   break;
                }
                eventType = parser.next();
            }
        }
        catch (Exception e)
        {
            Log.d("loadProviderInfo", "Caught an exeption: " + e.toString() + e.getMessage());
            e.printStackTrace();
            StackTraceElement st[] = e.getStackTrace();
            for(int i=0; i < st.length; i++)
            {
                Log.d("loadProviderInfo", "StackTraceElement[" + i + "] " + st[i].getLineNumber());

            }
        }



        return null;
    }

    private ArrayList<String> loadProviders(XmlResourceParser parser) {

        ArrayList<String> names = new ArrayList<String>();

        try {
            parser.next();
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG
                        && parser.getName().equalsIgnoreCase("siteproviders")) {

                   String[] parsedNames = parser.getAttributeValue(null, "value").split(",");

                    for (int i=0; i < parsedNames.length; i++)
                    {
                        names.add(i, parsedNames[i]);
                    }
                    break;
                }
                eventType = parser.next();
            }
        }
        catch (Exception e)
        {
            Log.d("loadProviderInfo", "Caught an exeption: " + e.toString() + e.getMessage());
            e.printStackTrace();
            StackTraceElement st[] = e.getStackTrace();
            for(int i=0; i < st.length; i++)
            {
                Log.d("loadProviderInfo", "StackTraceElement[" + i + "] " + st[i].getLineNumber());

            }

        }

        return names;
    }


}
