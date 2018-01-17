package greek.dev.challenge.nasaapi.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import greek.dev.challenge.nasaapi.MyApplication;
import greek.dev.challenge.nasaapi.model.MyNasaItem;

/**
 * Created by programbench on 1/15/2018.
 */

//is important to make the class and method as abstract, Room will return the right DAO implementation

@Database(entities = {MyNasaItem.class}, version = 1)
public abstract class NasaDB extends RoomDatabase {

    public static final String DB_NAME = "app_db";
    private static NasaDB INSTANCE;

    public static NasaDB getDatabase() {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(MyApplication.getAppContext(), NasaDB.class, DB_NAME)
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract NasaDAO getNasaDAO();
}
