package cs4720.cs.virginia.edu.hoowantsbrunch;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v4.app.FragmentActivity;

public class FindRestaurant extends FragmentActivity implements OnMapReadyCallback {

    private GoogleApiClient mGoogleApiClient;
    private ImageButton backToMain;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_restaurant);

        /* int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if (status != ConnectionResult.SUCCESS) { // Google Play Services are
            // not available
            GoogleApiAvailability.getInstance().getErrorDialog(this, status, 1).show();

        } else { // Google Play Services are available
            MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } */

        SupportMapFragment mapFragment = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map));
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        backToMain = (ImageButton) findViewById(R.id.backButton);
        backToMain.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng rotunda = new LatLng(38.05889, -78.503450);
        map.addMarker(new MarkerOptions().position(rotunda).title("UVA Rotunda"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(rotunda, 14.0f));
        LatLng aqui = new LatLng(38.023212, -78.470213);
        map.addMarker(new MarkerOptions().position(aqui).title("Aqui Es Mexico"));
        LatLng beer = new LatLng(38.024935, -78.468452);
        map.addMarker(new MarkerOptions().position(beer).title("Beer Run"));
        LatLng bizou = new LatLng(38.031271, -78.482017);
        map.addMarker(new MarkerOptions().position(bizou).title("Bizou"));
        LatLng blueMoon = new LatLng(38.031190, -78.488276);
        map.addMarker(new MarkerOptions().position(blueMoon).title("Blue Moon Dinder"));
        LatLng bluegrass = new LatLng(38.028888, -78.481312);
        map.addMarker(new MarkerOptions().position(bluegrass).title("Bluegrass Grill and Bakery"));
        LatLng caturra = new LatLng(38.034202, -78.499147);
        map.addMarker(new MarkerOptions().position(caturra).title("Cafe Caturra"));
        LatLng skybar = new LatLng(38.029953, -78.478603);
        map.addMarker(new MarkerOptions().position(skybar).title("Commonwealth Restaurant and Skybar"));
        LatLng escafe = new LatLng(38.030897, -78.482940);
        map.addMarker(new MarkerOptions().position(escafe).title("Escafe"));
        LatLng fellinis = new LatLng(38.031996, -78.482026);
        map.addMarker(new MarkerOptions().position(fellinis).title("Fellini's #9"));
        LatLng grit = new LatLng(38.031034, -78.481845);
        map.addMarker(new MarkerOptions().position(grit).title("Grit Cafe"));
        LatLng hamiltons = new LatLng(38.031131, -78.481535);
        map.addMarker(new MarkerOptions().position(hamiltons).title("Hamilton's at First and Main"));
        LatLng laTaza = new LatLng(38.025142, -78.475240);
        map.addMarker(new MarkerOptions().position(laTaza).title("La Taza Coffeehouse"));
        LatLng maya = new LatLng(38.031801, -78.489460);
        map.addMarker(new MarkerOptions().position(maya).title("Maya Restaurant"));
        LatLng oldMill = new LatLng(38.048809, -78.541445);
        map.addMarker(new MarkerOptions().position(oldMill).title("Old Mill Room at Boars Head"));
        LatLng pointe = new LatLng(38.031552, -78.483606);
        map.addMarker(new MarkerOptions().position(pointe).title("Pointe Restaurant"));
        LatLng petit = new LatLng(38.031071, -78.480476);
        map.addMarker(new MarkerOptions().position(petit).title("Petit Pois"));
        LatLng rhetts = new LatLng(38.10056, -78.461124);
        map.addMarker(new MarkerOptions().position(rhetts).title("Rhett's River Grill and Raw Bar"));
        LatLng shadwells = new LatLng(38.025749, -78.438291);
        map.addMarker(new MarkerOptions().position(shadwells).title("Shadwells Restaurant"));
        LatLng nook = new LatLng(38.030252, -78.478776);
        map.addMarker(new MarkerOptions().position(nook).title("The Nook Restaurant"));
        LatLng virginian = new LatLng(38.035638, -78.500665);
        map.addMarker(new MarkerOptions().position(virginian).title("The Virginian"));
        LatLng trinity = new LatLng(38.035160, -78.500310);
        map.addMarker(new MarkerOptions().position(trinity).title("Trinity Irish Pub"));
        LatLng whiskey = new LatLng(38.031391, -78.482762);
        map.addMarker(new MarkerOptions().position(whiskey).title("The Whiskey Jar"));

    }
}