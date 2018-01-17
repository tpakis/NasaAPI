package greek.dev.challenge.nasaapi.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by programbench on 1/15/2018.
 */

public class NasaItem {
    @SerializedName("links")
    @Expose
    private List<Link> links = null;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}

