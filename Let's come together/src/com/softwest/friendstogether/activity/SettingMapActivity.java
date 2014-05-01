package com.softwest.friendstogether.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.softwest.friendstogether.activity.R;
import com.softwest.friendstogether.utils.UserLocation;
import com.softwest.friendstogether.web.WebApi;
import com.softwest.friendstogether.web.responses.CurrentUser;
import com.softwest.friendstogether.web.responses.PeopleNearMe;
import com.softwest.friendstogether.web.responses.PlacesNiarMe;
import com.softwest.friendstogether.web.responses.Primary;
import com.softwest.friendstogether.web.responses.list.ListPeople;
import com.softwest.friendstogether.web.responses.list.POI;

public class SettingMapActivity
  extends BaseActivity
  implements OnMarkerClickListener
{
  private final static String IMAGE_NORMAL = "normal";
  private double mLatitude;
  private double mLongitude;
  private int mZoom;
  
  private Dialog mDialog = null;
  private GoogleMap mGMap = null;
  private UserLocation mGps = null;
  private Bitmap mFacebookIcon = null;
  private Context mContext = null;
  private CurrentUser mCurrentUser = null;
  private IMarkerClick mMarkerClick = null;
  private Object mJsonObject;
  
  public SettingMapActivity( Context context )
  {
    mContext = context;
  }
  
  public void settingMap( final CurrentUser user )
  {
    mCurrentUser = user;
    
    int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable( mContext );
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
    mGps = new UserLocation( mContext );
    // get current user location
    mGMap.setMyLocationEnabled( true );
    mGMap.setOnMarkerClickListener( this );
    mZoom = ( int )mGMap.getCameraPosition().zoom;
    
    if( mGps.canGetLocation() )
    {
      mLatitude = mGps.getLatitude();
      mLongitude = mGps.getLongitude();
    }
    // marker add
    CameraPosition cameraPosition = markIcon( ShowJsonObject.FACEBOOK );
    
    mGMap.animateCamera( CameraUpdateFactory.newCameraPosition( cameraPosition ) );
  }
  
  public CameraPosition markIcon( ShowJsonObject type )
  {
    MarkerOptions marker = new MarkerOptions();
    LatLng currLatitude = null;
    
    switch( type )
    {
      case FACEBOOK:
        currLatitude = showFacebookIcon( marker,currLatitude );
        break;
      case POI:
        List<PlacesNiarMe> listPOI = new ArrayList<PlacesNiarMe>();
        listPOI = ( ( POI )mJsonObject ).result;
        
        for( PlacesNiarMe lpoi : listPOI )
          currLatitude = showPOI( marker, lpoi,currLatitude );
       
        break;
      case PEOPLE:
        List<PeopleNearMe> listPeople = new ArrayList<PeopleNearMe>();
        listPeople = ( ( ListPeople )mJsonObject ).result;
        
        for( PeopleNearMe lPeople : listPeople )
          currLatitude = showPeople( marker, lPeople,currLatitude );
         
        break;
    }
    CameraPosition camPosition = new CameraPosition.Builder().target( currLatitude ).zoom( 12 ).build();
    
    return camPosition;
  }

  private LatLng showFacebookIcon( MarkerOptions marker,LatLng currLatitude )
  {
    currLatitude = new LatLng( mLatitude, mLongitude );
    marker.position( currLatitude );
    marker.title( mCurrentUser.username );
    
    String newUri = WebApi.getRedirectUri( String.valueOf( mCurrentUser.id ), IMAGE_NORMAL );
    mFacebookIcon = WebApi.getImageFromUri( newUri );
    
    marker.icon( BitmapDescriptorFactory.fromBitmap( mFacebookIcon ) );
    
    mGMap.addMarker( marker );
   
    return currLatitude;
  }

  private LatLng showPeople( MarkerOptions marker, PeopleNearMe listPeople,LatLng currLatitude )
  {
    currLatitude = new LatLng( listPeople.place_latitude, listPeople.place_longtitude );
    marker.position( currLatitude );
    marker.title( listPeople.user_nickname );
    marker.icon( BitmapDescriptorFactory.fromBitmap( WebApi.getImageFromUri( WebApi.WEB_HOST + listPeople.user_pic ) ) );
    
    mGMap.addMarker( marker );
   
    return currLatitude;
  }

  private LatLng showPOI( MarkerOptions marker, PlacesNiarMe listPlaces, LatLng currLatitude)
  {
    currLatitude = new LatLng( listPlaces.poi_latitude, listPlaces.poi_longtitude );
    marker.position( currLatitude );
    String id = String.valueOf( listPlaces.poi_id );
    marker.alpha( Float.parseFloat( id ) );
    marker.title( listPlaces.poi_name );
    marker.icon( BitmapDescriptorFactory.fromResource( R.drawable.ic_poi ) );
    
    mGMap.addMarker( marker );
    
    return currLatitude;
  }
  
  public <T extends Primary>void setJsonObject( T  jsonObj )
  {
    mJsonObject = jsonObj;
  }
  
  public double getLatitude()
  {
    return mLatitude;
  }
  
  public double getLongitude()
  {
    return mLongitude;
  }
  
  public int getZoom()
  {
    return mZoom;
  }
 
  @Override
  public boolean onMarkerClick( Marker marker )
  {
    if( null != mMarkerClick )
      mMarkerClick.getMarkerClick( marker );
    
    return false;
  }
  
  public void setMarkerClick( IMarkerClick mMarkerClick )
  {
    this.mMarkerClick = mMarkerClick;
  }
  public interface IMarkerClick
  {
    void getMarkerClick( Marker marker );
  }
  public enum ShowJsonObject
  {
    /** show facebook icon*/
    FACEBOOK,
    /** show poi icon*/
    POI,
    /** show people icon*/
    PEOPLE;
  }
}
