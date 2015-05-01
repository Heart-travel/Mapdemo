package com.example.mapdemo;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private TextView positionTextView;
	private LocationManager locationManager;
	private String provider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		positionTextView = (TextView) findViewById(R.id.position_text_view);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		// Get all the available providers
		List<String> providerList = locationManager.getProviders(true);
		if (providerList.contains(LocationManager.GPS_PROVIDER)) {
			Log.d("Map", "Provider is GPS!");
			provider = LocationManager.GPS_PROVIDER;
		} else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
			Log.d("Map", "Provider is Network!");
			provider = LocationManager.NETWORK_PROVIDER;
		} else {
			// Notice to the user that there are no available provider
			Log.e("Map", "No Provider!");
			Toast.makeText(this, "No location provider to use", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null) {
			// Show the current location
			Log.d("Map", "Location success!");
			showLocation(location);
		} else {
			Log.e("Map", "Location is Null!");
		}
		locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
	}

	protected void onDestroy() {
		super.onDestroy();
		
		Log.d("Map", "program destroy!");
		if (locationManager != null) {
			// ¹Ø±Õ³ÌÐòÊ±½«¼àÌýÆ÷ÒÆ³ý
			locationManager.removeUpdates(locationListener);
		}
	}

	LocationListener locationListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
		
		@Override
		public void onProviderEnabled(String provider) {
		}
		
		@Override
		public void onProviderDisabled(String provider) {
		}
		
		@Override
		public void onLocationChanged(Location location) {
			// Update the current localtion
			showLocation(location);
		}
	};
	
	private void showLocation(Location location) {
		Log.d("Map", "In Showloacation");
		String currentPosition = "latitude is " + location.getLatitude() + "\n"
		+ "longitude is " + location.getLongitude();
		positionTextView.setText(currentPosition);
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
