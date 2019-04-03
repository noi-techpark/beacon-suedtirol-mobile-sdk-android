package it.bz.beacon.beaconsuedtirolsdk.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import it.bz.beacon.beaconsuedtirolsdk.data.dao.BeaconDao;
import it.bz.beacon.beaconsuedtirolsdk.data.entity.Beacon;

@Database(
        entities = {
                Beacon.class
        },
        version = 1, exportSchema = false)

public abstract class BeaconDatabase extends RoomDatabase {

    private static BeaconDatabase INSTANCE;
    public static String DB_NAME = "info_db";

    public abstract BeaconDao beaconDao();

    public static BeaconDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BeaconDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BeaconDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
