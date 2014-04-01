package com.softwest.friendstogether.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.friendstogether.activity.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.softwest.friendstogether.utils.UserLocation;

public class MapActivity
  extends BaseActivity
{
  
  GoogleMap gMap;
  UserLocation gps;
  double latitude;
  double longitude;
  
  @Override
  protected void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.user_map );
    // google service = true
    int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable( getBaseContext() );
    if( status != ConnectionResult.SUCCESS )
    {
      int requestCode = 10;
      Dialog dialog = GooglePlayServicesUtil.getErrorDialog( status, this, requestCode );
      dialog.show();
      
    }
    else
    {
      // map fragment
      SupportMapFragment fmap = ( SupportMapFragment )getSupportFragmentManager().findFragmentById( R.id.map );
      gMap = fmap.getMap();
      
    }
    gps = new UserLocation( MapActivity.this );
   //get current user location
    gMap.setMyLocationEnabled(true);
    
    if( gps.canGetLocation() )
    {
      
      latitude = gps.getLatitude();
      longitude = gps.getLongitude();
      
    }
    // marker add
    MarkerOptions marker1 = new MarkerOptions().position( new LatLng( latitude, longitude ) ).title( "User Name" )
        .snippet( "my friend" ).icon( BitmapDescriptorFactory.fromResource( R.drawable.user_icon ) );
    gMap.addMarker( marker1 );
    
    // camera map
    CameraPosition cameraPosition = new CameraPosition.Builder().target( new LatLng( latitude, longitude ) ).zoom( 12 )
        .build();
    gMap.animateCamera( CameraUpdateFactory.newCameraPosition( cameraPosition ) );
    
    // click icon
    gMap.setOnInfoWindowClickListener( new GoogleMap.OnInfoWindowClickListener()
    {
      
      @Override
      public void onInfoWindowClick( Marker marker )
      {
        Toast.makeText( getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude,
            Toast.LENGTH_LONG ).show();
        
      }
    } );
    
  }
}
