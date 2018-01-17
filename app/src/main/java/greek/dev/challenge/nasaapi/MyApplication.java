package greek.dev.challenge.nasaapi;

import android.app.Application;
import android.content.Context;

/**
 * Created by programbench on 1/16/2018.
 */

public class MyApplication extends Application {
    //TODO  implement this with Dagger2
        private static Context context;

        public void onCreate() {
            super.onCreate();
            MyApplication.context = getApplicationContext();
        }

        public static Context getAppContext() {
            return MyApplication.context;
        }
}

