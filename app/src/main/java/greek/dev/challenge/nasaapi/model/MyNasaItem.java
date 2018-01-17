package greek.dev.challenge.nasaapi.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by programbench on 1/15/2018.
 */
@Entity(tableName = "NasaEntries")
public class MyNasaItem implements Parcelable {

    @PrimaryKey
    @NonNull
    public String nasaId;
    public String description;
    public String title;
    public String center;
    public String dateCreated;
    public String imageLink;

    public MyNasaItem(@NonNull String nasaId, String description, String title, String center, String dateCreated, String imageLink) {
        this.nasaId = nasaId;
        this.description = description;
        this.title = title;
        this.center = center;
        this.dateCreated = dateCreated;
        this.imageLink = imageLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNasaId() {
        return nasaId;
    }

    public void setNasaId(String nasaId) {
        this.nasaId = nasaId;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nasaId);
        dest.writeString(this.description);
        dest.writeString(this.title);
        dest.writeString(this.center);
        dest.writeString(this.dateCreated);
        dest.writeString(this.imageLink);
    }

    protected MyNasaItem(Parcel in) {
        this.nasaId = in.readString();
        this.description = in.readString();
        this.title = in.readString();
        this.center = in.readString();
        this.dateCreated = in.readString();
        this.imageLink = in.readString();
    }

    public static final Parcelable.Creator<MyNasaItem> CREATOR = new Parcelable.Creator<MyNasaItem>() {
        @Override
        public MyNasaItem createFromParcel(Parcel source) {
            return new MyNasaItem(source);
        }

        @Override
        public MyNasaItem[] newArray(int size) {
            return new MyNasaItem[size];
        }
    };
}
