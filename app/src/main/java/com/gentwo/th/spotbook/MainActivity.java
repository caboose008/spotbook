package com.gentwo.th.spotbook;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gentwo.th.spotbook.drawer.NavigationView;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    private final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1011;
    private LocationManager locationManager;
    private double lon;
    private double lat;

    public static final String LOG = "SPOTBOOK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) this.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if(!isLocationSettingsEnabled()){
            showDialogForLocationSettings();
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        } else {
            startListening();
        }
        final Intent navigateActivity = new Intent(this, NavigateActivity.class);
        Button button = findViewById(R.id.startNavigation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getApplicationContext().startActivity(navigateActivity);
            }
        });
    }

    private void startListening() {
        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, android.os.Process.myPid(), android.os.Process.myUid());
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, locationListener);
    }

    private void showExplanation() {
        Toast.makeText(getApplicationContext(), "Spotbook needs your location access permission to function. Please grand the access before using it.", Toast.LENGTH_LONG
        ).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startListening();
                } else {
                    showExplanation();
                }
            }
        }
    }

    // Define a listener that responds to location updates
    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
            lon = location.getLongitude();
            lat = location.getLatitude();
            location.getAccuracy();
            Log.i(LOG, lon + " " + lat);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
            showDialogForLocationSettings();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }
    // Register the listener with the Location Manager to receive location updates
    private boolean isLocationSettingsEnabled(){
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            return true;
        } else {
            return false;
        }
    }
    private void showDialogForLocationSettings(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Important")
                .setMessage("App needs your location. Will you enable the location settings? Otherwise the app will exit.")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getApplicationContext().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);
                    }
                })						//Do nothing on no
                .show();

    }
}
