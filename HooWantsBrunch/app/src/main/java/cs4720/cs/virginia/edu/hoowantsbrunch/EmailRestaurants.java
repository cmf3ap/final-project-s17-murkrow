package cs4720.cs.virginia.edu.hoowantsbrunch;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class EmailRestaurants extends AppCompatActivity {

    EditText editMessage;
    Button sendButton;
    String TO, SUBJECT, MESSAGE ;
    Intent intent ;
    Spinner spinner;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_restaurants);

        spinner = (Spinner) findViewById(R.id.emailSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.email_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        editMessage = (EditText)findViewById(R.id.message);

        sendButton = (Button)findViewById(R.id.send);
        sendButton.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send(view);
            }
        });

        backButton = (ImageButton)findViewById(R.id.emailBack);
        backButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back(view);
            }
        });
    }


    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void send(View view) {
        GetData();

        intent = new Intent(Intent.ACTION_SEND);

        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{TO});
        intent.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
        intent.putExtra(Intent.EXTRA_TEXT, MESSAGE);

        intent.setType("message/rfc822");

        startActivity(Intent.createChooser(intent, "Select Email Sending App :"));
    }

    public void GetData(){
        spinner = (Spinner)(findViewById(R.id.emailSpinner));
        String restaurant = spinner.getSelectedItem().toString();
        if (restaurant.equals("Beer Run")) {
            TO = "info@beerrun.com";
        } else if (restaurant.equals("Bizou")) {
            TO = "bizoudowntown@gmail.com";
        } else if (restaurant.equals("Blue Moon Diner")) {
            TO = "info@bluemoondiner.net";
        } else if (restaurant.equals("Bluegrass Grill and Bakery")) {
            TO = "chrissy@bluegrasscville.com";
        } else if (restaurant.equals("Cafe Caturra")) {
            TO = "info@cafecaturra.com";
        } else if (restaurant.equals("Commonwealth Restaurant and Skybar")) {
            TO = "info@commonwealthskybar.com";
        } else if (restaurant.equals("Escafe")) {
            TO = "out@escafe.com";
        } else if (restaurant.equals("Grit Cafe")) {
            TO = "connect@gritcoffee.com";
        } else if (restaurant.equals("Hamiltons at First and Main")) {
            TO = "mail@hamiltonsrestaurant.com";
        } else if (restaurant.equals("La Taza Coffeehouse")) {
            TO = "contact@latazacville.com";
        } else if (restaurant.equals("Maya Restaurant")) {
            TO = "maya.cville.restaurant@gmail.com";
        } else if (restaurant.equals("Petit Pois")) {
            TO = "info@petitpoisrestaurant.com";
        } else if (restaurant.equals("Rhetts River Grill and Raw Bar")) {
            TO = "rhett@rhettsrivergrill.com";
        } else if (restaurant.equals("Shadwells Restaurant")) {
            TO = "shadwells@shadwellsrestaurant.com";
        } else if (restaurant.equals("The Whiskey Jar")) {
            TO = "Thewhiskeyjar@gmail.com";
        } else {
            TO = "cmf3ap@gmail.com";
        }
        SUBJECT = "Message from HooWantsBrunch user";
        MESSAGE = editMessage.getText().toString();
    }


}
