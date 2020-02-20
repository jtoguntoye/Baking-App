package com.e.bakingtime.Model;

import com.google.gson.annotations.SerializedName;

public class Ingredients {

    @SerializedName("quantity")
    public int quantity;

    @SerializedName("measure")
    public String measure;

    @SerializedName("ingredient")
    public String ingredient;
}
