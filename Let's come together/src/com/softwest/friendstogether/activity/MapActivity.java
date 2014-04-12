package com.softwest.friendstogether.activity;

import java.io.InputStream;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.friendstogether.activity.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.softwest.friendstogether.LetIsGoTogetherAPP;
import com.softwest.friendstogether.fragment.PersonsFragment;
import com.softwest.friendstogether.utils.UserLocation;
import com.softwest.friendstogether.web.WebApi;
import com.softwest.friendstogether.web.handlers.HttpMethods;
import com.softwest.friendstogether.web.handlers.IResponse;
import com.softwest.friendstogether.web.responses.ComeTogetherEror;
import com.softwest.friendstogether.web.responses.CurrentUser;
import com.softwest.friendstogether.web.responses.FacebookToken;
import com.softwest.friendstogether.web.responses.PeopleNearMe;
import com.softwest.friendstogether.web.responses.PlacesNiarMe;
import com.softwest.friendstogether.web.responses.Primary;
import com.softwest.friendstogether.web.responses.list.POI;

@SuppressLint( "NewApi" )
public class MapActivity
  extends BaseActivity
  implements IResponse, OnInfoWindowClickListener
{
  private String mFacebookToken;
  
  private GoogleMap mGMap;
  private UserLocation mGps;
  private AdView mAdView;
  private double mLatitude;
  private double mLongitude;
  private Dialog mDialog;
  private float mZoom;
  
  private String mServerToken;
  
  public static final String[] titles = new String[]{ "Strawberry", "Banana", "Orange", "Mixed" };
  
  public static final String[] descriptions = new String[]{ "It is an aggregate accessory fruit",
      "It is the largest herbaceous flowering plant", "Citrus Fruit", "Mixed Fruits" };
  
  public static final Integer[] images = { R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
      R.drawable.ic_launcher };
  
  @Override
  protected void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.user_map );
    Bitmap userPicture = null;
    
    // adMob
    AdView adView = ( AdView )this.findViewById( R.id.adMob );
    AdRequest adRequest = new AdRequest.Builder().build();
    adView.loadAd( adRequest );
    
    // get information about current user
    LetIsGoTogetherAPP app = ( LetIsGoTogetherAPP )getApplicationContext();
    
    final CurrentUser user = app.getCurrentUser();
   
    if (android.os.Build.VERSION.SDK_INT > 9) {
      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
      StrictMode.setThreadPolicy(policy);
 
     // userPicture = getUserPic(String.valueOf( user.id ));
    }
  
    mFacebookToken = user.facebookToken;
    
    HttpMethods.sendFacebookToken( this, mFacebookToken, this, FacebookToken.class );
    
    PersonsFragment persons = new PersonsFragment();
    
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
      mGMap = fmap.getMap();
      
    }
    // click icon
    mGMap.setOnInfoWindowClickListener( this );
    
    mGps = new UserLocation( MapActivity.this );
    // get current user location
    mGMap.setMyLocationEnabled( true );
    
    mZoom = mGMap.getCameraPosition().zoom;
    
    if( mGps.canGetLocation() )
    {
      mLatitude = mGps.getLatitude();
      mLongitude = mGps.getLongitude();
    }
    // marker add
    LatLng latitude = new LatLng( mLatitude, mLongitude );
    
    MarkerOptions marker1 = new MarkerOptions();
    
    marker1.position( latitude );
    marker1.title( "User Name" );
    marker1.snippet( "my friend" );
    marker1.icon( BitmapDescriptorFactory.fromBitmap( userPicture ) );
    
    mGMap.addMarker( marker1 );
    
    // camera map
    CameraPosition cameraPosition = new CameraPosition.Builder().target( latitude ).zoom( 12 ).build();
    
    mGMap.animateCamera( CameraUpdateFactory.newCameraPosition( cameraPosition ) );
    
    // get people near me
    HttpMethods.peopleNearMe( this, mLatitude, mLongitude, WebApi.getServerToken(), this, PeopleNearMe.class );
    
    HttpMethods.getPlaceNiarMe( this, mLatitude, mLongitude, mZoom, WebApi.getServerToken(), this, POI.class );
  }
  
  /**
   * @param userID user facebook id
   * @return
   */
//  private Bitmap getUserPic(String userID) {
//   
//    return bitmap;
// 
//}
  // ---------------------AdMob-----------------------------
  @Override
  public void onResume()
  {
    super.onResume();
    if( mAdView != null )
    {
      mAdView.resume();
    }
  }
  
  @Override
  public void onPause()
  {
    // hide dialog
    if( mAdView != null )
    {
      mAdView.pause();
    }
    super.onPause();
    
    // mDialog.dismiss();
  }
  
  /** Called before the activity is destroyed. */
  @Override
  public void onDestroy()
  {
    if( mAdView != null )
    {
      // Destroy the AdView.
      mAdView.destroy();
    }
    super.onDestroy();
  }
  
  /** Gets a string error reason from an error code. */
  @SuppressWarnings( "unused" )
  private String getErrorReason( int errorCode )
  {
    String errorReason = "";
    switch( errorCode )
    {
      case AdRequest.ERROR_CODE_INTERNAL_ERROR:
        errorReason = "Internal error";
        break;
      case AdRequest.ERROR_CODE_INVALID_REQUEST:
        errorReason = "Invalid request";
        break;
      case AdRequest.ERROR_CODE_NETWORK_ERROR:
        errorReason = "Network Error";
        break;
      case AdRequest.ERROR_CODE_NO_FILL:
        errorReason = "No fill";
        break;
    }
    return errorReason;
  }
  
  // ---------------------AdMob-----------------------------

  @Override
  public Primary process( String json, final Object classInfo )
  {
    Log.i( "response", "response " + json );
    
    parseResonse( json, classInfo );
    
    return null;
  }
  
  private void parseResonse( String json, Object classInfo )
  {
    String facebook = FacebookToken.class.getName();
    
    String peopleNearMe = FacebookToken.class.getName();
    
    String poi = POI.class.getName();
    if( json.contains( "error" ) || json.contains( "exception" ) )
    {
      ComeTogetherEror error = Primary.fromJson( json, ComeTogetherEror.class );
      
      Toast.makeText( this, error.fbToken, Toast.LENGTH_LONG ).show();
    }
    else if( classInfo.equals( facebook ) )
    {
      FacebookToken token = Primary.fromJson( json, FacebookToken.class );
      
      WebApi.setServerToken( token.data );
    }
    else if( classInfo.equals( peopleNearMe ) )
    {
      PeopleNearMe people = Primary.fromJson( json, PeopleNearMe.class );
    }
    else if(classInfo.equals( poi ))
    {
      POI places = Primary.fromJson( json, POI.class );
    }
    
  }
  
  @Override
  public void onInfoWindowClick( Marker marker )
  {
    Toast.makeText( getApplicationContext(), "Your Location is - \nLat: " + mLatitude + "\nLong: " + mLongitude,
        Toast.LENGTH_LONG ).show();
  }
  
  @Override
  public void onBackPressed()
  {
    // nothing to do
  }
  
}
