package com.example.facta.myapplication;

import java.util.ArrayList;

/**
 * Created by facta on 11/21/2014.
 */
public class RSSProviderInfo {


    public String getLinkTag() {
        return linkTag;
    }

    public void setLinkTag(String linkTag) {
        this.linkTag = linkTag;
    }


    public String getDescriptionTag() {
        return descriptionTag;
    }

    public void setDescriptionTag(String descriptionTag) {
        this.descriptionTag = descriptionTag;
    }


    public ArrayList<String> getUrls() {
        return urls;
    }

    public void addUrl(String url)
    {
        if(!url.isEmpty())
        {
            urls.add(url);
        }
        //TODO else debug empty url
    }

    public String getTitleTag() {

        return titleTag;
    }

    public void setTitleTag(String titleTag) {
        this.titleTag = titleTag;
    }

    public String getElementTag() {

        return elementTag;
    }

    public void setElementTag(String elementTag) {
        this.elementTag = elementTag;
    }

    private String elementTag;
    private String titleTag;
    private String linkTag;
    private String descriptionTag;
    private ArrayList<String> urls;

    public RSSProviderInfo()
    {
        urls = new ArrayList<String>();
    }
}
