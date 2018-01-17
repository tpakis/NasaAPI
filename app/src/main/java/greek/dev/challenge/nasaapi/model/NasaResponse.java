package greek.dev.challenge.nasaapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NasaResponse{
    @SerializedName("collection")
    @Expose
    private ResponseCollection collection;

    public ResponseCollection getCollection() {
        return collection;
    }

    public void setCollection(ResponseCollection collection) {
        this.collection = collection;
    }

}
