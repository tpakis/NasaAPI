package greek.dev.challenge.nasaapi.webservice;

/**
 * Created by programbench on 1/15/2018.
 */

public class NasaUtils {
    final static String NASA_BASE_URL =
            "https://images-api.nasa.gov/";

    public static NasaService getNasaService() {
        return RetrofitClient.getClient(NASA_BASE_URL).create(NasaService.class);
    }
}
