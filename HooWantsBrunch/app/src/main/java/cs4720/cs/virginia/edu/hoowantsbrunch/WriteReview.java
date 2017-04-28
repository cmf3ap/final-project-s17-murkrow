package cs4720.cs.virginia.edu.hoowantsbrunch;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.name;
import static cs4720.cs.virginia.edu.hoowantsbrunch.userReview.readFromFile;
import static cs4720.cs.virginia.edu.hoowantsbrunch.userReview.writeToFile;

public class WriteReview extends AppCompatActivity {

    static final int TAKE_PHOTO_PERMISSION = 1;
    static final int REQUEST_TAKE_PHOTO = 2;
    static final int PICK_IMAGE_REQUEST = 3;

    ImageView imageView;
    ImageButton takePictureButton;
    EditText reviewContent;
    Uri file;
    Spinner spinner;
    String currRestaurant;
    String currContent;
    String restaurant;
    String review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        setContentView(R.layout.activity_write_review);

        takePictureButton = (ImageButton) findViewById(R.id.takePictureButton);
        imageView = (ImageView) findViewById(R.id.imageView);
        reviewContent = (EditText) findViewById(R.id.ReviewText);

        spinner = (Spinner) findViewById(R.id.restaurants_spinner);

        // We are giving you the code that checks for permissions
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            takePictureButton.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE }, TAKE_PHOTO_PERMISSION);
        }

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.restaurants_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // This is called when permissions are either granted or not for the app
        // You do not need to edit this code.

        if (requestCode == TAKE_PHOTO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePictureButton.setEnabled(true);
            }
        }
    }

    public void takePicture(View view) {
        // Add code here to start the process of taking a picture
        // Note you can send an intent to the camera to take a picture...
        // So start by considering that!
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        file = Uri.fromFile(getOutputMediaFile());

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);


    }

    public void submitReview(View view) {
        userReview newReview = new userReview();
        reviewContent = (EditText) findViewById(R.id.ReviewText);
        newReview.review = reviewContent.getText().toString();

        saveToDatabase(view);
        loadFromDatabase(view);

        viewRestaurants(view);

        // Intent intent = new Intent(this, MainActivity.class);
        // startActivity(intent);
    }
    public void viewRestaurants(View view) {
        Intent intent = new Intent(this, ViewRestaurants.class);
        startActivity(intent);
    }
    public void getImageFromLibrary(View view) {
        // Add code here to start the process of getting a picture from the library

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        //photoPickerIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //photoPickerIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        photoPickerIntent.setType("image/*");

        photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(photoPickerIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Add code here to handle results from both taking a picture or pulling
        // from the image gallery.

        if (requestCode == REQUEST_TAKE_PHOTO) {
            /*
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);*/
            if(resultCode == RESULT_OK) {
                imageView.setImageURI(file);

            }

        }
        else if (requestCode == PICK_IMAGE_REQUEST) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                try {

                    Uri uri = data.getData();


                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    // Log.d(TAG, String.valueOf(bitmap));

                    ImageView imageView = (ImageView) findViewById(R.id.imageView);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }
    // Add other methods as necessary here


    public String getReview(String restaurant) {
        DatabaseHelper mDbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String[] projection = {
                "restaurantName",
                "review"
        };


        //String sortOrder = "name" + " DESC";

        Cursor c = db.query("restaurantReview", projection, null, null, null, null, null);

        c.moveToFirst();
//formatting to display to users!!
        //CORRECT CODE TO DISPLAY TO USERS!!!!!!!!!!!!!!!!!!
        /*
        String currContent2 = "";
        while(c.moveToNext()) {
            String currRestaurant2 = c.getString(c.getColumnIndexOrThrow("restaurantName"));
            if (restaurant.equals(currRestaurant2)) {
                currContent2 += "\r\n" + "\r\n" + c.getString(c.getColumnIndexOrThrow("review"));
            }
        }


*/

        String currContent2 = "";
        while(c.moveToNext()) {
            // String currRestaurant2 = "<b>" + c.getString(c.getColumnIndexOrThrow("restaurantName")) +  "<b>";
            String currRestaurant2 = c.getString(c.getColumnIndexOrThrow("restaurantName"));

            currContent2 += c.getString(c.getColumnIndexOrThrow("restaurantName")) +
                    "\r\n" + c.getString(c.getColumnIndexOrThrow("review")) +"\r\n" + "-----------------------" + "\r\n";
        }
        c.close();

        return currContent2;
    }

    public String getSpecificReview(String restaurant) {
        DatabaseHelper mDbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String[] projection = {
                "restaurantName",
                "review"
        };


        //String sortOrder = "name" + " DESC";

        Cursor c = db.query("restaurantReview", projection, null, null, null, null, null);

        c.moveToFirst();

        String currContent2 = restaurant + "\r\n";
        while(c.moveToNext()) {
            String currRestaurant2 = c.getString(c.getColumnIndexOrThrow("restaurantName"));
            if (restaurant.equals(currRestaurant2)) {
                currContent2 +=  c.getString(c.getColumnIndexOrThrow("review")) + "\r\n" + "\r\n";
            }
        }


        c.close();

        return currContent2;
    }

    public void saveToDatabase(View view) {
        // Add code here to save to the database

        DatabaseHelper mDbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        reviewContent = (EditText) findViewById(R.id.ReviewText);

        spinner = (Spinner)(findViewById(R.id.restaurants_spinner));
        restaurant = spinner.getSelectedItem().toString();
        review = reviewContent.getText().toString();
        values.put("restaurantName", restaurant);
        values.put("review", review);


        if(imageView.getDrawable() instanceof BitmapDrawable){
            Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            if (bitmap != null) {
                //byte[] imageData = DatabaseHelper.getBytes(bitmap);
                try {
                    File f = DatabaseHelper.savebitmap(bitmap);
                    String pPath = f.getAbsolutePath();
                    values.put("picturePath", pPath);
                }
                catch (Exception e) {

                }
            }

        }


        long newRowId;
        newRowId = db.insert("restaurantReview", null, values);

        userReview.review = getSpecificReview(restaurant);

        //db = mDbHelper.getReadableDatabase();
/*
        String[] projection = {
                "restaurantName",
                "review"
        };


        //String sortOrder = "name" + " DESC";

        Cursor c = db.query("restaurantReview", projection, null, null, null, null, null);

        c.moveToFirst();

        currContent = "";
        while(c.moveToNext()) {
            currRestaurant = c.getString(c.getColumnIndexOrThrow("restaurantName"));
            if (restaurant.equals(currRestaurant)) {
                currContent += "\r\n" + "\r\n" + c.getString(c.getColumnIndexOrThrow("review"));
            }
        }

        c.close();
*/

    }

    public void loadFromDatabase(View view) {




    }

}
