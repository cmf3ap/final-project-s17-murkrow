package cs4720.cs.virginia.edu.hoowantsbrunch;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**

 Assignment Notes: This code is provided as part of the SQLite feature.
 You do not need to edit this code.  Note that it provides the functionality
 to create a blank database if one does not exist.

 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HooWantsBrunch3.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table restaurant (restaurantID varchar(25), name varchar(25), street varchar(40), city varchar(25), state varchar(15), zip char(5))");
        db.execSQL("create table restaurantReview (restaurantName varchar(25), review varchar(144))");
        //db.execSQL("create table person (compid varchar(25), name varchar(25))");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("delete table restaurant");
        db.execSQL("delete table restaurantReview");
        //db.execSQL("delete table person");
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
