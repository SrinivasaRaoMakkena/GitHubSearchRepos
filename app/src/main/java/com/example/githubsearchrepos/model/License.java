
package com.example.githubsearchrepos.model;


import com.google.gson.annotations.SerializedName;


public class License {

    @SerializedName("key")
    private String mKey;
    @SerializedName("name")
    private String mName;
    @SerializedName("spdx_id")
    private String mSpdxId;
    @SerializedName("url")
    private String mUrl;

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSpdxId() {
        return mSpdxId;
    }

    public void setSpdxId(String spdxId) {
        mSpdxId = spdxId;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

}
