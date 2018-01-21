package greek.dev.challenge.nasaapi;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;

/**
 * Created by programbench on 1/16/2018.
 */

public class MyApplication extends Application {
    //TODO  implement this with Dagger2
        private static Context context;

        public void onCreate() {
            super.onCreate();
            MyApplication.context = getApplicationContext();
            Timber.plant(new MyTimberTree());
        }

        public static Context getAppContext() {
            return MyApplication.context;
        }
}

