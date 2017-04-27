package cs4720.cs.virginia.edu.hoowantsbrunch;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.Manifest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.maps.CameraUpdate;
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
    private static final int TAKE_PHOTO_PERMISSION = 1;
    TextView nearestLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_restaurant);

        nearestLocation = (TextView)findViewById(R.id.nearest);

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION }, TAKE_PHOTO_PERMISSION);
        }

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

    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double p = 0.017453292519943295;    // Math.PI / 180
        double a = 0.5 - Math.cos((lat2 - lat1) * p)/2 + Math.cos(lat1 * p) * Math.cos(lat2 * p) * (1 - Math.cos((lon2 - lon1) * p))/2;
        return 12742 * Math.asin(Math.sqrt(a)); // 2 * R; R = 6371 km
    }


    @Override
    public void onMapReady(GoogleMap map) {
        LatLng me = new LatLng(0,0);
        LatLng rotunda = new LatLng(38.05889, -78.503450);
        map.addMarker(new MarkerOptions().position(rotunda).title("UVA Rotunda"));
        LatLng aqui = new LatLng(38.023212, -78.470213);
        map.addMarker(new MarkerOptions().position(aqui).title("Aqui Es Mexico"));
        LatLng beer = new LatLng(38.024935, -78.468452);
        map.addMarker(new MarkerOptions().position(beer).title("Beer Run"));
        LatLng bizou = new LatLng(38.031271, -78.482017);
        map.addMarker(new MarkerOptions().position(bizou).title("Bizou"));
        LatLng blueMoon = new LatLng(38.031190, -78.488276);
        map.addMarker(new MarkerOptions().position(blueMoon).title("Blue Moon Diner"));
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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

            if (location == null) {
                Criteria criteria1 = new Criteria();
                criteria1.setAccuracy(Criteria.ACCURACY_COARSE);
                String provider = locationManager.getBestProvider(criteria, true);
                location = locationManager.getLastKnownLocation(provider);
            } else if (location!=null){
                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                me = userLocation;
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14), 1500, null);

                double aQUI = distance(location.getLatitude(), location.getLongitude(), 38.023212, -78.470213);
                double bEER = distance(location.getLatitude(), location.getLongitude(), 38.024935, -78.468452);
                double bIZOU = distance(location.getLatitude(), location.getLongitude(), 38.031271, -78.482017);
                double mOON = distance(location.getLatitude(), location.getLongitude(), 38.031190, -78.488276);
                double gRASS = distance(location.getLatitude(), location.getLongitude(), 38.028888, -78.481312);
                double cATURRA = distance(location.getLatitude(), location.getLongitude(), 38.034202, -78.499147);
                double sKYBAR = distance(location.getLatitude(), location.getLongitude(), 38.029953, -78.478603);
                double eSCAFE = distance(location.getLatitude(), location.getLongitude(), 38.030897, -78.482940);
                double fELLINIS = distance(location.getLatitude(), location.getLongitude(), 38.031996, -78.482026);
                double gRIT = distance(location.getLatitude(), location.getLongitude(), 38.031034, -78.481845);
                double hAMILTONS = distance(location.getLatitude(), location.getLongitude(), 38.031131, -78.481535);
                double lATAZA = distance(location.getLatitude(), location.getLongitude(), 38.025142, -78.475240);
                double mAYA = distance(location.getLatitude(), location.getLongitude(), 38.031801, -78.489460);
                double oLDMILL = distance(location.getLatitude(), location.getLongitude(), 38.048809, -78.541445);
                double pOINTE = distance(location.getLatitude(), location.getLongitude(), 38.031552, -78.483606);
                double pETIT = distance(location.getLatitude(), location.getLongitude(), 38.031071, -78.480476);
                double rHETTS = distance(location.getLatitude(), location.getLongitude(), 38.10056, -78.461124);
                double sHADWELLS = distance(location.getLatitude(), location.getLongitude(), 38.025749, -78.438291);
                double nOOK = distance(location.getLatitude(), location.getLongitude(), 38.030252, -78.478776);
                double vIRG = distance(location.getLatitude(), location.getLongitude(), 38.035638, -78.500665);
                double tRIN = distance(location.getLatitude(), location.getLongitude(), 38.035160, -78.500310);
                double wHISKEY = distance(location.getLatitude(), location.getLongitude(), 38.031391, -78.482762);
                double[] loc = {aQUI, bEER, bIZOU, mOON, gRASS, cATURRA, sKYBAR, eSCAFE, fELLINIS, gRIT, hAMILTONS, lATAZA, mAYA, oLDMILL, pOINTE, pETIT, rHETTS, sHADWELLS, nOOK, vIRG, tRIN, wHISKEY};
                double smallest = aQUI;

                for (int i = 0; i < 22; i++) {
                    if (loc[i] < smallest) {
                        smallest = loc[i];
                    } else if (loc[i] == smallest) {
                        smallest = loc[i];
                    }
                }

                nearestLocation = (TextView)findViewById(R.id.nearest);
                if (smallest == aQUI) {
                    nearestLocation.setText("Nearest Restaurant = Aqui Es Mexico");
                } else if (smallest == bEER) {
                    nearestLocation.setText("Nearest Restaurant = Beer Run");
                } else if (smallest == bIZOU) {
                    nearestLocation.setText("Nearest Restaurant = Bizou");
                } else if (smallest == mOON) {
                    nearestLocation.setText("Nearest Restaurant = Blue Moon Diner");
                } else if (smallest == gRASS) {
                    nearestLocation.setText("Nearest Restaurant = Bluegrass Grill and Bakery");
                } else if (smallest == cATURRA) {
                    nearestLocation.setText("Nearest Restaurant = Cafe Caturra");
                } else if (smallest == sKYBAR) {
                    nearestLocation.setText("Nearest Restaurant = Commonwealth Restaurant and Skybar");
                } else if (smallest == eSCAFE) {
                    nearestLocation.setText("Nearest Restaurant = Escafe");
                } else if (smallest == fELLINIS) {
                    nearestLocation.setText("Fellinis #9");
                } else if (smallest == gRIT) {
                    nearestLocation.setText("Nearest Restaurant = Grit Cafe");
                } else if (smallest == hAMILTONS) {
                    nearestLocation.setText("Nearest Restaurant = Hamiltons at First and Main");
                } else if (smallest == lATAZA) {
                    nearestLocation.setText("Nearest Restaurant = La Taza Coffeehouse");
                } else if (smallest == mAYA) {
                    nearestLocation.setText("Nearest Restaurant = Maya Restaurant");
                } else if (smallest == oLDMILL) {
                    nearestLocation.setText("Nearest Restaurant = Old Mill Room at Boars Head");
                } else if (smallest == pOINTE) {
                    nearestLocation.setText("Nearest Restaurant = Pointe Restaurant");
                } else if (smallest == pETIT) {
                    nearestLocation.setText("Nearest Restaurant = Petit Pois");
                } else if (smallest == rHETTS) {
                    nearestLocation.setText("Nearest Restaurant = Rhetts River Grill and Raw Bar");
                } else if (smallest == sHADWELLS) {
                    nearestLocation.setText("Nearest Restaurant = Shadwells Restaurant");
                } else if (smallest == nOOK) {
                    nearestLocation.setText("Nearest Restaurant = The Nook Restaurant");
                } else if (smallest == vIRG) {
                    nearestLocation.setText("Nearest Restaurant = The Virginian");
                } else if (smallest == tRIN) {
                    nearestLocation.setText("Nearest Restaurant = Trinity Irish Pub");
                } else if (smallest == wHISKEY) {
                    nearestLocation.setText("Nearest Restaurant = The Whiskey Jar");
                }
            }
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
            map.animateCamera(cameraUpdate);
        } else {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(rotunda, 14.0f));
        }
    }
}