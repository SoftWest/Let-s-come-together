package com.softwest.friendstogether.activity;

import org.apache.http.HttpResponse;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
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
import com.softwest.friendstogether.LetIsGoTogetherAPP;
import com.softwest.friendstogether.utils.UserLocation;
import com.softwest.friendstogether.web.handlers.HttpMethods;
import com.softwest.friendstogether.web.handlers.IResponse;
import com.softwest.friendstogether.web.responses.CurrentUser;
import com.softwest.friendstogether.web.responses.FacebookToken;
import com.softwest.friendstogether.web.responses.Primary;

@SuppressLint("NewApi")
public class MapActivity
  extends BaseActivity
  implements IResponse
{
  private String mFacebookToken;
  
  private GoogleMap gMap;
  private UserLocation gps;
  private double mLatitude;
  private double mLongitude;
  private Dialog mDialog; 
  
  @Override
  protected void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.user_map );
    
    // get information about current user
    LetIsGoTogetherAPP app = ( LetIsGoTogetherAPP )getApplicationContext();
    
    CurrentUser user = app.getCurrentUser();
    mFacebookToken = user.facebookToken;
    
    HttpMethods.sendFacebookToken( this, mFacebookToken,this );
    
    // google service = true
    int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable( getBaseContext() );
    if( status != ConnectionResult.SUCCESS )
    {
      int requestCode = 10;
      mDialog = GooglePlayServicesUtil.getErrorDialog( status, this, requestCode );
      mDialog.show();
      
    }
    else
    {
      // map fragment
      SupportMapFragment fmap = ( SupportMapFragment )getSupportFragmentManager().findFragmentById( R.id.map );
      gMap = fmap.getMap();
      
    }
    gps = new UserLocation( MapActivity.this );
    // get current user location
    gMap.setMyLocationEnabled( true );
    
    if( gps.canGetLocation() )
    {
      mLatitude = gps.getLatitude();
      mLongitude = gps.getLongitude();
    }
    // marker add
    MarkerOptions marker1 = new MarkerOptions().position( new LatLng( mLatitude, mLongitude ) ).title( "User Name" )
        .snippet( "my friend" ).icon( BitmapDescriptorFactory.fromResource( R.drawable.user_icon ) );
    gMap.addMarker( marker1 );
    
    // camera map
    CameraPosition cameraPosition = new CameraPosition.Builder().target( new LatLng( mLatitude, mLongitude ) )
        .zoom( 12 ).build();
    gMap.animateCamera( CameraUpdateFactory.newCameraPosition( cameraPosition ) );
    
    // click icon
    gMap.setOnInfoWindowClickListener( new GoogleMap.OnInfoWindowClickListener()
    {
      
      @Override
      public void onInfoWindowClick( Marker marker )
      {
        Toast.makeText( getApplicationContext(), "Your Location is - \nLat: " + mLatitude + "\nLong: " + mLongitude,
            Toast.LENGTH_LONG ).show();
      }
    } );
    
  }
  @Override
  protected void onPause()
  {
    super.onPause();
   //hide dialog
    mDialog.dismiss();
  }
  @Override
  public void onBackPressed()
  {
    // nothing to do
  }

  @Override
  public Primary process( String json )
  {
   
    Log.i( "response", "response "+ json );
   
    FacebookToken user = Primary.fromJson( json, FacebookToken.class );
    return null;
  }

  
}
