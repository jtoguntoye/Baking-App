package com.e.bakingtime.Model;

import com.google.gson.annotations.SerializedName;

public class Steps {

    @SerializedName("id")
    public int stepsId;

    @SerializedName("shortDescription")
    public String ShortDescription;

    @SerializedName("description")
    public String description;

    @SerializedName("videoURL")
    public String videoURL;

    @SerializedName("thumbnailURL")
    public String thumbnailURL;
}
