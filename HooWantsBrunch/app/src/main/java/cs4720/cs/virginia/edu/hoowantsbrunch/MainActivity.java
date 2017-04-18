package cs4720.cs.virginia.edu.hoowantsbrunch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    private Button addReview;
    private Button findRestaurants;
    private Button viewRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addReview = (Button) findViewById(R.id.addReview);
        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openWriteReview(view);
            }
        });
        viewRestaurants = (Button) findViewById(R.id.viewRestaurants);
        viewRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewRestaurants(view);
            }
        });
        findRestaurants = (Button) findViewById(R.id.findRestaurant);
        findRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findRestaurant(view);
            }
        });

    }
    public void openWriteReview(View view) {
        Intent intent = new Intent(this, WriteReview.class);
        startActivity(intent);
    }

    public void viewRestaurants(View view) {
        Intent intent = new Intent(this, ViewRestaurants.class);
        startActivity(intent);
    }

    public void findRestaurant(View view) {
        Intent intent = new Intent(this, FindRestaurant.class);
        startActivity(intent);
    }

    // did the apk upload?

}
