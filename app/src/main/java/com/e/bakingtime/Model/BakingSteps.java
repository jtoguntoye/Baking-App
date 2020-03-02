package com.e.bakingtime.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;


public class BakingSteps implements Parcelable {


        @SerializedName("id")
        @Expose
        private Integer id;

        @SerializedName("shortDescription")
        @Expose
        private String shortDescription;

        @SerializedName("description")
        @Expose
        private String description;

        @SerializedName("videoURL")
        @Expose
        private String videoURL;

        @SerializedName("thumbnailURL")
        @Expose
        private String thumbnailURL;

        public Integer getId() {
            return id;
        }

         public void setId(Integer id) {
            this.id = id;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVideoURL() {
            return videoURL;
        }

        public void setVideoURL(String videoURL) {
            this.videoURL = videoURL;
        }

        public String getThumbnailURL() {
            return thumbnailURL;
        }

        public void setThumbnailURL(String thumbnailURL) {
            this.thumbnailURL = thumbnailURL;
        }


        public static final Creator<BakingSteps> CREATOR = new Creator<BakingSteps>() {
            @Override
            public BakingSteps createFromParcel(Parcel parcel) {

                return new BakingSteps(parcel);
            }

            @Override
            public BakingSteps[] newArray(int size) {
                return new BakingSteps[size];
            }
        };



        protected BakingSteps(Parcel parcel){

            id = parcel.readInt();
            shortDescription = parcel.readString();
            description  = parcel.readString();
            videoURL = parcel.readString();
            thumbnailURL = parcel.readString();
        }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(id);
    parcel.writeString(shortDescription);
    parcel.writeString(description);
    parcel.writeString(videoURL);
    parcel.writeString(thumbnailURL);
    }
}
