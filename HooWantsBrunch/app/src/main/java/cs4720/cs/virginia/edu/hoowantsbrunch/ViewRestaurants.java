package cs4720.cs.virginia.edu.hoowantsbrunch;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;

import static cs4720.cs.virginia.edu.hoowantsbrunch.userReview.review;

public class ViewRestaurants extends AppCompatActivity {

    TextView reviewText;
    Spinner spinner;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_restaurants);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner = (Spinner) findViewById(R.id.spinner2);
        reviewText = (TextView)findViewById(R.id.ReviewContent);
        imageView1 = (ImageView)findViewById(R.id.imageView1);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView3 = (ImageView)findViewById(R.id.imageView3);
        backButton = (ImageButton)findViewById(R.id.backButton2);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.restaurants_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                spinner = (Spinner)(findViewById(R.id.spinner2));
                String restaurantName = spinner.getSelectedItem().toString();
                imageView1.setImageBitmap(null);
                imageView2.setImageBitmap(null);
                imageView3.setImageBitmap(null);
                reviewText.setText(getReview(restaurantName));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        // if we didn't come from submit review
        // if(userReview.review == null){
        /*
            DatabaseHelper mDbHelper = new DatabaseHelper(this);
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            String[] projection = {
                    "restaurantName",
                    "review"
            };


            Cursor c = db.query("restaurantReview", projection, null, null, null, null, null);

            c.moveToFirst();


            String currContent2 = "";
            while(c.moveToNext()) {
                // String currRestaurant2 = "<b>" + c.getString(c.getColumnIndexOrThrow("restaurantName")) +  "<b>";
                String currRestaurant2 = c.getString(c.getColumnIndexOrThrow("restaurantName"));

                if(currContent2.contains(currRestaurant2)){
                    int i = currContent2.indexOf(currRestaurant2) + currRestaurant2.length() + 4;
                    String beforeReview = currContent2.substring(0,i);
                    String afterReview = currContent2.substring(i);

                    final CharSequence[] restaurantArray = getResources().getTextArray(R.array.restaurants_array);

                    currContent2 = beforeReview + c.getString(c.getColumnIndexOrThrow("review")) +"\r\n" + afterReview;

                    //currContent2+= c.getString(c.getColumnIndexOrThrow("review")) +"\r\n" + "\r\n";

                }
                else {
                    currContent2 += c.getString(c.getColumnIndexOrThrow("restaurantName")).toUpperCase() +
                            "\r\n" + "\r\n" + c.getString(c.getColumnIndexOrThrow("review")) + "\r\n" + "\r\n";
                }
            }
            c.close();

            userReview.review = currContent2;
      //  }
*/

        //reviewText.setText(userReview.review);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWriteReview(view);
            }
        });

        backButton = (ImageButton) findViewById(R.id.backButton2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMain(view);
            }
        });
    }

    private String getReview(String restaurantName) {
        DatabaseHelper mDbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();



        String[] projection = {
                "restaurantName",
                "review",
                "picturePath"
        };


        //String sortOrder = "name" + " DESC";

        Cursor c = db.query("restaurantReview", projection, null, null, null, null, null);

        c.moveToFirst();

        int imageCount = 0;
        String currContent2 = restaurantName + "\r\n";
        while(c.moveToNext()) {
            String currRestaurant2 = c.getString(c.getColumnIndexOrThrow("restaurantName"));
            if (restaurantName.equals(currRestaurant2)) {
                currContent2 +=  c.getString(c.getColumnIndexOrThrow("review")) + "\r\n" + "\r\n";
                ImageView myImageView = null;
                switch (imageCount) {
                    case 0: myImageView = imageView1; break;
                    case 1: myImageView = imageView2; break;
                    case 2: myImageView = imageView3; break;
                    default: break;
                }
                if (myImageView != null) {
                    String pPath = c.getString(c.getColumnIndexOrThrow("picturePath"));
                    if ((pPath != null) && (pPath.length() > 0)) {
                        File imgFile = new File(pPath);

                        if (imgFile.exists()) {
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            myImageView.setImageBitmap(myBitmap);
                            imageCount++;

                        }
                    }
                    /*
                    if (imageData != null) {
                        Bitmap bitmap = DatabaseHelper.getImage(imageData);
                        myImageView.setImageBitmap(bitmap);
                        imageCount++;
                    } */
                }
            }
        }


        c.close();

        return currContent2;
    }

    public void openWriteReview(View view) {
        Intent intent = new Intent(this, WriteReview.class);
        startActivity(intent);
    }

    public void backToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
