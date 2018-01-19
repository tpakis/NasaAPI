package greek.dev.challenge.nasaapi.webservice;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by programbench on 1/15/2018.
 */
public class RetrofitClient {

    private static Retrofit INSTANSE = null;

    public static Retrofit getClient(String baseUrl) {
        if (INSTANSE==null) {
            INSTANSE = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return INSTANSE;
    }
}
