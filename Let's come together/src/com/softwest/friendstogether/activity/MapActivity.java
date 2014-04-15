package com.softwest.friendstogether.activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.softwest.friendstogether.fragment.ListPersonsFragment;
import com.softwest.friendstogether.utils.UserLocation;
import com.softwest.friendstogether.web.WebApi;
import com.softwest.friendstogether.web.handlers.HttpMethods;
import com.softwest.friendstogether.web.handlers.IResponse;
import com.softwest.friendstogether.web.responses.CheckIn;
import com.softwest.friendstogether.web.responses.ComeTogetherEror;
import com.softwest.friendstogether.web.responses.CurrentUser;
import com.softwest.friendstogether.web.responses.FacebookToken;
import com.softwest.friendstogether.web.responses.PeopleNearMe;
import com.softwest.friendstogether.web.responses.PlacesNiarMe;
import com.softwest.friendstogether.web.responses.Primary;
import com.softwest.friendstogether.web.responses.list.POI;

public class MapActivity
  extends BaseActivity
  implements IResponse, OnInfoWindowClickListener, OnClickListener
{
  private String mFacebookToken;
  public final static String LIST_PERSON = "list-person";
  private GoogleMap mGMap;
  private UserLocation mGps;
  private AdView mAdView;
  private double mLatitude;
  private double mLongitude;
  private Dialog mDialog;
  private float mZoom;
  private Bitmap mFacebookIcon;
  private String mServerToken;
  private int mCheckInId;
  
  private ImageView mImageCheckIn;
  
  @Override
  protected void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.act_map );
    
    // adMob
    AdView adView = ( AdView )this.findViewById( R.id.adMob );
    AdRequest adRequest = new AdRequest.Builder().build();
    adView.loadAd( adRequest );
    
    mImageCheckIn = ( ImageView )findViewById( R.id.iv_check_in );
    mImageCheckIn.setOnClickListener( this );
    
    View v = (View)findViewById( R.id.view_first );
    View v1 = (View)findViewById( R.id.view_second );
    
    v.setOnClickListener( this );
    v1.setOnClickListener( this );
    
    // get information about current user
    LetIsGoTogetherAPP app = ( LetIsGoTogetherAPP )getApplicationContext();
    
    final CurrentUser user = app.getCurrentUser();
    
    if( android.os.Build.VERSION.SDK_INT > 9 )
    {
      try
      {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy( policy );
        
        String newUri = WebApi.getRedirectUri( String.valueOf( user.id ) );
        mFacebookIcon = WebApi.getFacebookIcon( newUri );
      }
      catch( Throwable e )
      {
        Log.w( LetIsGoTogetherAPP.TAG, e.toString() );
      }
    }
    
    mFacebookToken = user.facebookToken;
    
    HttpMethods.sendFacebookToken( this, mFacebookToken, this, FacebookToken.class );
    
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
    marker1.title( user.username );
    // marker1.snippet( "my friend" );
    marker1.icon( BitmapDescriptorFactory.fromBitmap( mFacebookIcon ) );
    
    mGMap.addMarker( marker1 );
    
    // camera map
    CameraPosition cameraPosition = new CameraPosition.Builder().target( latitude ).zoom( 12 ).build();
    
    mGMap.animateCamera( CameraUpdateFactory.newCameraPosition( cameraPosition ) );
    
    // get people near me
    HttpMethods.peopleNearMe( this, mLatitude, mLongitude, WebApi.getServerToken(), this, PeopleNearMe.class );
    
    HttpMethods.getPlaceNiarMe( this, mLatitude, mLongitude, mZoom, WebApi.getServerToken(), this, POI.class );
    
    HttpMethods.checkIn( this, mCheckInId, WebApi.getServerToken(), this, CheckIn.class );
    
  }
  
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
      mAdView.destroy();
    
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
    
    parseResponse( json, classInfo );
    
    return null;
  }
  
  private void parseResponse( String json, Object classInfo )
  {
    String facebook = FacebookToken.class.getName();
    
    String peopleNearMe = FacebookToken.class.getName();
    
    String poi = POI.class.getName();
    
    String checkIn = CheckIn.class.getName();
    
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
    else if( classInfo.equals( poi ) )
    {
      POI places = Primary.fromJson( json, POI.class );
      mCheckInId = ( ( PlacesNiarMe )places.result ).poi_id;
    }
    else if( classInfo.equals( checkIn ) )
    {
      CheckIn check = Primary.fromJson( json, CheckIn.class );
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
  
  @Override
  public void onClick( View v )
  {
    switch( v.getId() )
    {
      case R.id.iv_check_in:
        Log.i( "check in click", "checkIn click" );
        break;
      case R.id.view_first:
      case R.id.view_second:
        LinearLayout layout = (LinearLayout)findViewById( R.id.ll_main );
        layout.setVisibility( View.GONE );
        
        ListPersonsFragment listPerson = new ListPersonsFragment();
        
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        
        tr.replace( R.id.fl_main_map, listPerson );
        tr.addToBackStack( LIST_PERSON );
        tr.commit();
        
        break;
    }
  }
  
}
