package com.softwest.friendstogether.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.Toast;

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
import com.softwest.friendstogether.adapters.PersonAdapter;
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
import com.softwest.friendstogether.web.responses.list.ListPeople;
import com.softwest.friendstogether.web.responses.list.POI;

@SuppressWarnings( "deprecation" )
public class MapActivity
  extends BaseActivity
  implements IResponse, OnInfoWindowClickListener, OnClickListener, OnDrawerOpenListener, OnDrawerCloseListener,OnItemClickListener
{
  private String mFacebookToken;
  public final static String LIST_PERSON = "list-person";
  private GoogleMap mGMap;
  private UserLocation mGps;
  private AdView mAdView;
  private double mLatitude;
  private double mLongitude;
  private Dialog mDialog;
  private int mZoom;
  private Bitmap mFacebookIcon;
  private String mServerToken;
  private int mCheckInId;
  private ListView mListPersons;
  private ImageView mImageCheckIn;
  private List<PeopleNearMe> mPeopleList = new ArrayList<PeopleNearMe>();
  private SlidingDrawer mSlidingDrawer;
  private View mView;
  private ImageView mHeart;
  
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
    
    mSlidingDrawer = ( SlidingDrawer )findViewById( R.id.slider_drawer );
    mSlidingDrawer.setOnDrawerOpenListener( this );
    mSlidingDrawer.setOnDrawerCloseListener( this );
 
    mListPersons = ( ListView )findViewById( R.id.list_persons );
    mListPersons.setOnItemClickListener( this );
    
    mView = ( View )findViewById( R.id.handle );
    
    // get information about current user
    final CurrentUser user = ecstractUserPicture();
    
    mFacebookToken = user.facebookToken;
    
    HttpMethods.sendFacebookToken( this, mFacebookToken, this, FacebookToken.class );
    
    // google service = true
    settingMap( user );
    
    // get people near me
    String token = WebApi.getServerToken();
    
    HttpMethods.getPlaceNiarMe( this, mLatitude, mLongitude, mZoom, token, this, POI.class );
    
    HttpMethods.peopleNearMe( this, mLatitude, mLongitude, token, this, ListPeople.class );
    
  }
  
  private void settingMap( final CurrentUser user )
  {
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
    
    mZoom = ( int )mGMap.getCameraPosition().zoom;
    
    if( mGps.canGetLocation() )
    {
      mLatitude = mGps.getLatitude();
      mLongitude = mGps.getLongitude();
    }
    // marker add
    CameraPosition cameraPosition = markIcon( user );
    
    mGMap.animateCamera( CameraUpdateFactory.newCameraPosition( cameraPosition ) );
  }
  
  private CameraPosition markIcon( final CurrentUser user )
  {
    LatLng latitude = new LatLng( mLatitude, mLongitude );
    
    MarkerOptions marker1 = new MarkerOptions();
    
    marker1.position( latitude );
    marker1.title( user.username );
    // marker1.snippet( "my friend" );
    marker1.icon( BitmapDescriptorFactory.fromBitmap( mFacebookIcon ) );
    
    mGMap.addMarker( marker1 );
    
    // camera map
    CameraPosition cameraPosition = new CameraPosition.Builder().target( latitude ).zoom( 12 ).build();
    return cameraPosition;
  }
  
  private CurrentUser ecstractUserPicture()
  {
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
    return user;
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
    
    String peopleNearMe = ListPeople.class.getName();
    
    String poi = POI.class.getName();
    
    String checkIn = CheckIn.class.getName();
    
    if( json.contains( "error" ) || json.contains( "exception" ) )
    {
      ComeTogetherEror error = Primary.fromJson( json, ComeTogetherEror.class );
      
    }
    else if( classInfo.equals( facebook ) )
    {
      FacebookToken token = Primary.fromJson( json, FacebookToken.class );
      
      WebApi.setServerToken( token.data );
    }
    else if( classInfo.equals( peopleNearMe ) )
    {
      ListPeople peopleList = Primary.fromJson( json, ListPeople.class );
      
      mPeopleList = peopleList.result;
      
      PersonAdapter adapter = new PersonAdapter( this, mPeopleList );
      mListPersons.setAdapter( adapter );
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
        
        HttpMethods.checkIn( this, mCheckInId, WebApi.getServerToken(), this, CheckIn.class );
        
        break;
      case R.id.handle:
     
        break;
      case R.id.iv_heart:
        mHeart.setBackgroundColor( Color.RED );
        break;
    }
  }
  
  @Override
  public void onDrawerOpened()
  {
    int height = ( int )TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics() );
    
    LinearLayout layout = ( LinearLayout )findViewById( R.id.ll_map );
    layout.setVisibility( View.GONE );
    
    mImageCheckIn.setVisibility( View.GONE );
    
    mView.setLayoutParams( new LayoutParams( LayoutParams.MATCH_PARENT, height ) );
    
    mSlidingDrawer.setLayoutParams( new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT ) );
    
  }
  
  @Override
  public void onDrawerClosed()
  {
    int height = ( int )TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics() );
    int width = ( int )TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics() );
    
    LinearLayout layout = ( LinearLayout )findViewById( R.id.ll_map );
    layout.setVisibility( View.VISIBLE );
    
    mImageCheckIn.setVisibility( View.VISIBLE );
    
    mView.setLayoutParams( new LayoutParams( width, height ) );
    // set new params in slider drawer
    mSlidingDrawer.setLayoutParams( new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.2f ) );
  }

  @Override
  public void onItemClick( AdapterView<?> parent, View view, int position, long id )
  {
   ImageView heart = mHeart = (ImageView)view.findViewById( R.id.iv_heart );
 //  heart.setOnClickListener( this );
  }
  
}
