package cs4720.cs.virginia.edu.hoowantsbrunch;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Formatter;
import java.util.UUID;

import static cs4720.cs.virginia.edu.hoowantsbrunch.userReview.review;

/**

 Assignment Notes: This code is provided as part of the SQLite feature.
 You do not need to edit this code.  Note that it provides the functionality
 to create a blank database if one does not exist.

 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HooWantsBrunchC.db";

    private final Context tempContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        tempContext = context;
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table restaurant (restaurantID varchar(25), name varchar(25), street varchar(40), city varchar(25), state varchar(15), zip char(5))");
        db.execSQL("create table restaurantReview (restaurantName varchar(25), review varchar(144), picturePath varchar(255))");


        populateCannedReview(db);

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

    public boolean isPopulated(SQLiteDatabase db){
        Cursor mCursor = db.rawQuery("SELECT * FROM " + DATABASE_NAME, null);
        Boolean rowExists;

        if (mCursor.moveToFirst())
        {
            // DO SOMETHING WITH CURSOR
            rowExists = true;

        } else
        {
            // I AM EMPTY
            rowExists = false;
        }
        return rowExists;
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private void populateCannedReview(SQLiteDatabase db) {
        final CharSequence[] restaurantArray = tempContext.getResources().getTextArray(R.array.restaurants_array);
        final CharSequence[] reviewArray = tempContext.getResources().getTextArray(R.array.canned_review_array);
        for (int i = 0; i < restaurantArray.length; i++) {
            String restaurantName = restaurantArray[i].toString();
            for (int j = 0; j < reviewArray.length; j++) {
                ContentValues values = new ContentValues();
                values.put("restaurantName", restaurantName);
                String reviewText = String.format(reviewArray[j].toString(), restaurantName);
                values.put("review", reviewText);
                long newRowId;
                newRowId = db.insert("restaurantReview", null, values);
            }
        }
    }

    // convert from bitmap to byte array

    // these next two methods taken from http://stackoverflow.com/questions/11790104/how-to-storebitmap-image-and-retrieve-image-from-sqlite-database-in-android

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static File savebitmap(Bitmap bmp) throws IOException {
        // from http://stackoverflow.com/questions/649154/save-bitmap-to-location
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        File f = new File(Environment.getExternalStorageDirectory()
                + File.separator + UUID.randomUUID().toString());
        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
        bmp.compress(Bitmap.CompressFormat.JPEG, 85, fo);
        fo.flush();
        fo.close();
        return f;
    }

}
