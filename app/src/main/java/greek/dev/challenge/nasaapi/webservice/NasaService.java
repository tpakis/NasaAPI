package greek.dev.challenge.nasaapi.webservice;

import greek.dev.challenge.nasaapi.model.NasaResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by programbench on 1/15/2018.
 */

public interface NasaService {
    @GET("search?media_type=image")
    Call<NasaResponse> getItems(@Query("q") String nasaQuery);
}
