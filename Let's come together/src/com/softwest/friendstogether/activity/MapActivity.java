package com.softwest.friendstogether.activity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
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
import com.softwest.friendstogether.web.handlers.Loaders;
import com.softwest.friendstogether.web.responses.CurrentUser;
import com.softwest.friendstogether.web.responses.Primary;

@SuppressLint("NewApi")
public class MapActivity
  extends BaseActivity
  implements LoaderCallbacks<Primary>
{
  
  private static final int LOADER_FACEBOOK_TOKEN = 5;
  private String mFacebookToken;
  
  private GoogleMap gMap;
  private UserLocation gps;
  private double mLatitude;
  private double mLongitude;
  
  @Override
  protected void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.user_map );
    
    // get information about current user
    LetIsGoTogetherAPP app = ( LetIsGoTogetherAPP )getApplicationContext();
    
    CurrentUser user = app.getCurrentUser();
    mFacebookToken = user.facebookToken;
    
    getLoaderManager().restartLoader( LOADER_FACEBOOK_TOKEN, null, this );
    
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
  public void onBackPressed()
  {
    // nothing to do
  }
  
  @Override
  public Loader<Primary> onCreateLoader( int id, Bundle args )
  {
    if( id == LOADER_FACEBOOK_TOKEN )
    {
       Loaders.sendFacebookToken( this, mFacebookToken );
    }
    
    return null;
  }
  
  @Override
  public void onLoadFinished( Loader<Primary> loader, Primary data )
  {
    
  }
  
  @Override
  public void onLoaderReset( Loader<Primary> loader )
  {
    // do nothing
  }
 
}
