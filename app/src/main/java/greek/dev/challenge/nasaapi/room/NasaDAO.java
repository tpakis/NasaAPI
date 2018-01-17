package greek.dev.challenge.nasaapi.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomDatabase;

import java.util.List;

import greek.dev.challenge.nasaapi.model.MyNasaItem;

/**
 * Created by programbench on 1/15/2018.
 */

@Dao
public interface NasaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEntry(MyNasaItem entry);

    // Removes an entry from the database
    @Delete
    void delete(MyNasaItem entry);

    @Query("delete from NasaEntries where nasaId = :id")
    void deleteById(String id);

    // Gets all entries in the database
    @Query("SELECT * FROM NasaEntries")
    List<MyNasaItem> getAllEntries();

    // Gets all entries in the database
    @Query("SELECT * FROM NasaEntries WHERE description LIKE :queryString")
    List<MyNasaItem> getEntriesContaining(String queryString);

    @Query("SELECT COUNT(*) from NasaEntries")
    int countEntries();

}

