package cs4720.cs.virginia.edu.hoowantsbrunch;

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
                //do something
            }
        });
        viewRestaurants = (Button) findViewById(R.id.viewRestaurants);
        viewRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something
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

    public void findRestaurant(View view) {
        Intent intent = new Intent(this, FindRestaurant.class);
        startActivity(intent);
    }

}
