package com.softwest.friendstogether.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.model.Marker;
import com.softwest.friendstogether.LetIsGoTogetherAPP;
import com.softwest.friendstogether.activity.SettingMapActivity.IMarkerClick;
import com.softwest.friendstogether.activity.SettingMapActivity.ShowJsonObject;
import com.softwest.friendstogether.adapters.PersonAdapter;
import com.softwest.friendstogether.fragment.FitBeakFragment;
import com.softwest.friendstogether.fragment.ProfileFragment;
import com.softwest.friendstogether.utils.JsonObjectType;
import com.softwest.friendstogether.utils.MenuListAdapter;
import com.softwest.friendstogether.utils.MenuNavDrawerItem;
import com.softwest.friendstogether.web.WebApi;
import com.softwest.friendstogether.web.handlers.HttpMethods;
import com.softwest.friendstogether.web.handlers.IResponse;
import com.softwest.friendstogether.web.responses.CheckIn;
import com.softwest.friendstogether.web.responses.ComeTogetherEror;
import com.softwest.friendstogether.web.responses.CurrentUser;
import com.softwest.friendstogether.web.responses.FacebookToken;
import com.softwest.friendstogether.web.responses.PeopleNearMe;
import com.softwest.friendstogether.web.responses.Primary;
import com.softwest.friendstogether.web.responses.list.ListPeople;
import com.softwest.friendstogether.web.responses.list.POI;

@SuppressLint( "NewApi" )
@TargetApi( Build.VERSION_CODES.HONEYCOMB )
public class MapActivity
  extends BaseActivity
  implements IResponse, OnClickListener, OnItemClickListener, IMarkerClick
{
  private static final int PROFILE = 0;
  private static final int MAP_VIEW = 1;
  private static final int SEARCH = 2;
  private static final int FACEBOOK_LOG_OUT = 3;
  
  private String mFacebookToken = null;
  
  private boolean mCheckImage;
  private double mLatitude;
  private double mLongitude;
  private int mZoom;
  private int mCheckInId;
  
  private AdView mAdView = null;
  private ListView mListPerson = null;
  private ImageView mImageCheckIn = null;
  private ImageView mHeart = null;
  private CurrentUser mCurrentUser = null;
  private SettingMapActivity mSettingMap = null;
  private static IFacebookLogOut mFacebookLogOut = null;
  private List<PeopleNearMe> mPeopleList = new ArrayList<PeopleNearMe>();
  // slide menu
  private DrawerLayout mDrawerLayout = null;
  private ListView mDrawerList = null;
  private ActionBarDrawerToggle mDrawerToggle = null;
  private CharSequence mDrawerTitle = null;
  private CharSequence mTitle = null;
  private String[] navMenuTitles = null;
  private TypedArray navMenuIcons = null;
  private MenuListAdapter adapter = null;
  private ArrayList<MenuNavDrawerItem> menuNavDrawerItems = null;
  
  @SuppressLint( "NewApi" )
  @Override
  protected void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.act_map );
    // slide menu
    mTitle = mDrawerTitle = getTitle();
    
    navMenuTitles = getResources().getStringArray( R.array.nav_drawer_items );
    navMenuIcons = getResources().obtainTypedArray( R.array.nav_drawer_icons );
    
    mDrawerLayout = ( DrawerLayout )findViewById( R.id.drawer_layout );
    mDrawerList = ( ListView )findViewById( R.id.list_slidermenu );
    
    menuNavDrawerItems = new ArrayList<MenuNavDrawerItem>();
    menuNavDrawerItems.add( drawerItemOne() );
    menuNavDrawerItems.add( drawerItemTwo() );
    menuNavDrawerItems.add( drawerItemThree() );
    menuNavDrawerItems.add( drawerItemFour() );
    
    navMenuIcons.recycle();
    
    mDrawerList.setOnItemClickListener( new SlideMenuClickListener() );
    
    adapter = new MenuListAdapter( getApplicationContext(), menuNavDrawerItems );
    
    mDrawerList.setAdapter( adapter );
    
    getActionBar().setDisplayHomeAsUpEnabled( true );
    getActionBar().setHomeButtonEnabled( true );
    
    mDrawerToggle = new ActionBarDrawerToggle( this, mDrawerLayout, R.drawable.ic_drawer, R.string.app_name,
        R.string.app_name )
    {
      public void onDrawerClosed( View view )
      {
        getActionBar().setTitle( mTitle );
        invalidateOptionsMenu();
      }
      
      public void onDrawerOpened( View drawerView )
      {
        getActionBar().setTitle( mDrawerTitle );
        invalidateOptionsMenu();
      }
    };
    
    mDrawerLayout.setDrawerListener( mDrawerToggle );
    
    mSettingMap = new SettingMapActivity( this );
    mSettingMap.setMarkerClick( this );
    // adMob
    AdView adView = ( AdView )this.findViewById( R.id.adMob_bunner );
    AdRequest adRequest = new AdRequest.Builder().build();
    adView.loadAd( adRequest );
    
    mImageCheckIn = ( ImageView )findViewById( R.id.iv_check_in );
    mImageCheckIn.setOnClickListener( this );
    
    mListPerson = ( ListView )findViewById( R.id.list_person );
    mListPerson.setOnItemClickListener( this );
    
    LinearLayout llMap = ( LinearLayout )findViewById( R.id.ll_map );
    llMap.setOnClickListener( this );
    // get information about current user
    LetIsGoTogetherAPP app = ( LetIsGoTogetherAPP )getApplicationContext();
    
    final CurrentUser user = mCurrentUser = app.getCurrentUser();
    
    mSettingMap.settingMap( mCurrentUser );
    
    mFacebookToken = user.facebookToken;
    
    mLatitude = mSettingMap.getLatitude();
    mLongitude = mSettingMap.getLongitude();
    mZoom = mSettingMap.getZoom();
    
    jsonRequests();
  }
  
  private void jsonRequests()
  {
    HttpMethods.sendFacebookToken( this, mFacebookToken, this, JsonObjectType.FACEBOOK_TOKEN );
    
    String token = WebApi.getServerToken();
    
    HttpMethods.getPlaceNiarMe( this, mLatitude, mLongitude, mZoom, token, this, JsonObjectType.POI );
    
    HttpMethods.peopleNearMe( this, mLatitude, mLongitude, token, this, JsonObjectType.PEOPLE );
  }
  
  private MenuNavDrawerItem drawerItemFour()
  {
    return new MenuNavDrawerItem( navMenuTitles[ 3 ], navMenuIcons.getResourceId( 3, -1 ) );
  }
  
  private MenuNavDrawerItem drawerItemThree()
  {
    return new MenuNavDrawerItem( navMenuTitles[ 2 ], navMenuIcons.getResourceId( 2, -1 ) );
  }
  
  private MenuNavDrawerItem drawerItemTwo()
  {
    return new MenuNavDrawerItem( navMenuTitles[ 1 ], navMenuIcons.getResourceId( 1, -1 ) );
  }
  
  private MenuNavDrawerItem drawerItemOne()
  {
    return new MenuNavDrawerItem( navMenuTitles[ 0 ], navMenuIcons.getResourceId( 0, -1 ) );
  }
  
  @Override
  public boolean onOptionsItemSelected( MenuItem item )
  {
    if( mDrawerToggle.onOptionsItemSelected( item ) )
      return true;
    
    switch( item.getItemId() )
    {
      case R.id.action_settings:
        
        return true;
      case R.id.page_fitbeak:
        replace( FitBeakFragment.class, R.id.fl_main, null, true );
        return true;
        
      default:
        return super.onOptionsItemSelected( item );
    }
  }
  
  /* *
   * Called when invalidateOptionsMenu() is triggered
   */
  @Override
  public boolean onPrepareOptionsMenu( Menu menu )
  {
    // if nav drawer is opened, hide the action items
    boolean drawerOpen = mDrawerLayout.isDrawerOpen( mDrawerList );
    
    menu.findItem( R.id.action_settings ).setVisible( !drawerOpen );
    
    return super.onPrepareOptionsMenu( menu );
  }
  
  /** Diplaying view for selected nav drawer list item */
  private void displayView( int position )
  {
    switch( position )
    {
      case PROFILE:
        replace( ProfileFragment.class, R.id.fl_main, null, true );
        
        mDrawerList.setItemChecked( position, true );
        mDrawerList.setSelection( position );
        
        setTitle( navMenuTitles[ position ] );
        
        mDrawerLayout.closeDrawer( mDrawerList );
        break;
      case MAP_VIEW:
        Intent intent = new Intent( this, MapActivity.class );
        startActivity( intent );
        break;
      case SEARCH:
        
        break;
      case FACEBOOK_LOG_OUT:
        if( null != mFacebookLogOut )
          mFacebookLogOut.facebookLogOut();
        break;
    }
  }
  
  @Override
  public void setTitle( CharSequence title )
  {
    mTitle = title;
    getActionBar().setTitle( mTitle );
  }
  
  /** When using the ActionBarDrawerToggle, you must call it during onPostCreate() and
   * onConfigurationChanged()... */
  @Override
  protected void onPostCreate( Bundle savedInstanceState )
  {
    super.onPostCreate( savedInstanceState );
    // Sync the toggle state after onRestoreInstanceState has occurred.
    mDrawerToggle.syncState();
  }
  
  @Override
  public void onConfigurationChanged( Configuration newConfig )
  {
    super.onConfigurationChanged( newConfig );
    // Pass any configuration change to the drawer toggls
    mDrawerToggle.onConfigurationChanged( newConfig );
  }
  
  // ---------------------AdMob-----------------------------
  @Override
  public void onResume()
  {
    super.onResume();
    if( mAdView != null )
      mAdView.resume();
  }
  
  @Override
  public void onPause()
  {
    if( mAdView != null )
      mAdView.pause();
    
    super.onPause();
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
  public void onClick( View v )
  {
    switch( v.getId() )
    {
      case R.id.iv_check_in:
        HttpMethods.checkIn( this, mCheckInId, WebApi.getServerToken(), this, JsonObjectType.CHECK_IN );
        break;
      case R.id.iv_heart:
        mCheckImage = !mCheckImage;
        if( mCheckImage )
          mHeart.setBackgroundResource( R.drawable.ic_heart_clicked );
        else
          mHeart.setBackgroundResource( R.drawable.ic_heart );
        break;
      case R.id.ll_map:
        LinearLayout llMap = ( LinearLayout )findViewById( R.id.ll_map );
        llMap.setVisibility( View.VISIBLE );
        break;
    }
  }
  
  @Override
  public Primary process( String json, final JsonObjectType classInfo )
  {
    Log.i( "response", "response " + json );
    
    parseResponse( json, classInfo );
    
    return null;
  }
  
  private void parseResponse( String json, JsonObjectType jsonObject )
  {
    JsonObjectType type = JsonObjectType.switchJsonObjectType( jsonObject );
    
    if( json.contains( "error" ) || json.contains( "exception" ) )
      parseJsonError( json, jsonObject, type );
    else if( type == JsonObjectType.FACEBOOK_TOKEN )
      parseFacebookToken( json, jsonObject, type );
    else if( type == JsonObjectType.PEOPLE )
      parseJsonPeopleNearMe( json, jsonObject, type );
    else if( type == JsonObjectType.POI )
      parsePoiJson( json, jsonObject, type );
    else if( type == JsonObjectType.CHECK_IN )
      parseCheckIn( json, jsonObject, type );
  }
  
  private void parseJsonError( String json, JsonObjectType jsonObject, JsonObjectType type )
  {
    ComeTogetherEror error = Primary.fromJson( json, ComeTogetherEror.class );
    
    TextView tvError = ( TextView )findViewById( R.id.tv_error );
    ProgressBar progress = ( ProgressBar )findViewById( R.id.pr_upload );
    
    Log.d( "error", "error" + error );
    
    if( type != JsonObjectType.CHECK_IN )
    {
      tvError.setVisibility( View.VISIBLE );
      tvError.setText( String.valueOf( error.info ) );
      
      progress.setVisibility( View.VISIBLE );
    }
    else if( type == JsonObjectType.CHECK_IN )
      Toast.makeText( this, String.valueOf( error.info ), Toast.LENGTH_LONG ).show();
  }
  
  private void parseFacebookToken( String json, JsonObjectType jsonObject, JsonObjectType type )
  {
    FacebookToken token = Primary.fromJson( json, FacebookToken.class );
    
    WebApi.setServerToken( token.data );
  }
  
  private void parseJsonPeopleNearMe( String json, JsonObjectType jsonObject, JsonObjectType type )
  {
    ListPeople peopleList = Primary.fromJson( json, ListPeople.class );
    
    mSettingMap.setJsonObject( peopleList );
    
    mSettingMap.markIcon( ShowJsonObject.PEOPLE );
    
    mPeopleList = peopleList.result;
    
    PersonAdapter adapter = new PersonAdapter( this, mPeopleList );
    mListPerson.setAdapter( adapter );
  }
  
  private void parsePoiJson( String json, JsonObjectType jsonObject, JsonObjectType type )
  {
    POI places = Primary.fromJson( json, POI.class );
    
    mSettingMap.setJsonObject( places );
    
    mSettingMap.markIcon( ShowJsonObject.POI );
  }
  
  private void parseCheckIn( String json, JsonObjectType jsonObject, JsonObjectType type )
  {
    CheckIn check = Primary.fromJson( json, CheckIn.class );
  }
  
  @Override
  public void onBackPressed()
  {
    getSupportFragmentManager().popBackStackImmediate();
  }
  
  @Override
  public void onItemClick( AdapterView<?> parent, View view, int position, long id )
  {
    ImageView btCheckHide = ( ImageView )findViewById( R.id.iv_check_in );
    // btCheckHide.setVisibility( View.GONE );
    
    ImageView heart = mHeart = ( ImageView )view.findViewById( R.id.iv_heart );
    heart.setOnClickListener( this );
  }
  
  public static void setFacebookLogOut( IFacebookLogOut logout )
  {
    mFacebookLogOut = logout;
  }
  
  @Override
  public void getMarkerClick( Marker marker )
  {
    float f = marker.getAlpha();
    int id = ( int )f;
    
    mCheckInId = id;
  }
  
  private class SlideMenuClickListener
    implements ListView.OnItemClickListener
  {
    @Override
    public void onItemClick( AdapterView<?> parent, View view, int position, long id )
    {
      displayView( position );
    }
  }
  public interface IFacebookLogOut
  {
    public void facebookLogOut();
  }
}
