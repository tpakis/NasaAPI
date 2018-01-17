package greek.dev.challenge.nasaapi.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseCollection {

        @SerializedName("items")
        @Expose
        private List<NasaItem> items = null;

        public List<NasaItem> getItems() {
            return items;
        }

        public void setItems(List<NasaItem> items) {
            this.items = items;
        }


}
