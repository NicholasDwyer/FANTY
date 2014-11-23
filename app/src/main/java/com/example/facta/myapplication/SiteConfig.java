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
        String attrName;

        try {
            parser.next();
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG
                        && parser.getName().equalsIgnoreCase("site" )) {

                    attrName = parser.getAttributeValue(null, "name");
                    
                    if(attrName.equals(provider + ".enabled"))
                    {
                        return null; //If it's not enabled don't include it in the config
                    }
                    else if(attrName.equals(provider + ".elementTag"))
                    {
                        info.setElementTag(parser.getAttributeValue(null, "value"));
                    }
                    else if(attrName.equals(provider + ".titleTag"))
                    {
                        info.setTitleTag(parser.getAttributeValue(null, "value"));
                    }
                    else if(attrName.equals(provider + ".linkTag"))
                    {
                        info.setLinkTag(parser.getAttributeValue(null, "value"));
                    }
                    else if(attrName.equals(provider + ".descriptionTag"))
                    {
                        info.setDescriptionTag(parser.getAttributeValue(null,"value"));
                    }
                    else if(attrName.equals(provider + ".url"))
                    {
                        info.addUrl(parser.getAttributeValue(null, "value"));
                    }
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
