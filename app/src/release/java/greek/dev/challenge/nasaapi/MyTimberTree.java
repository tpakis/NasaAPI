package greek.dev.challenge.nasaapi;

import android.util.Log;

import timber.log.Timber;


public class MyTimberTree extends Timber.Tree {
    @Override
    protected void log(final int priority, final String tag, final String message, final Throwable throwable) {
        if (priority == Log.ERROR || priority == Log.WARN) {
            try {
                throw throwable;
            } catch (Throwable throwable1) {
                throwable1.printStackTrace();
            }
        }
    }
}