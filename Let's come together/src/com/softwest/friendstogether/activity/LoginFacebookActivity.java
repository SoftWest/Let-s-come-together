package com.softwest.friendstogether.activity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.MessageDigest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.softwest.friendstogether.web.responses.CurrentUser;
import com.softwest.friendstogether.web.responses.Primary;

@SuppressWarnings( "deprecation" )
public class LoginFacebookActivity
  extends Activity
  implements DialogListener, RequestListener

{
  private static String APP_ID = "1419097081679140";
  
  // Instance of Facebook Class
  private Facebook facebook = new Facebook( APP_ID );
  private AsyncFacebookRunner mAsyncRunner;
  
  String FILENAME = "AndroidSSO_data";
  private static SharedPreferences mPrefs;
  private static Activity mActivity;
  
  private String[] permissions = new String[]{ "read_stream", "email", "publish_actions", "read_friendlists",
      "user_location", "user_friends", "user_status", "friends_location" };
  
  @Override
  protected void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    
    mAsyncRunner = new AsyncFacebookRunner( facebook );
    mActivity = this;
    
    getNewFacebookKeyHash();
    
    loginFacebook();
    
    if( facebook.isSessionValid() )
    {
      Intent mapIntent = new Intent( mActivity, MapActivity.class );
      startActivity( mapIntent );
    }
  }
  
  /** empty constructor */
  public LoginFacebookActivity()
  {
  };
  
  public LoginFacebookActivity( Activity activity )
  {
    mActivity = activity;
    
    facebook = new Facebook( APP_ID );
    mAsyncRunner = new AsyncFacebookRunner( facebook );
  };
  
  /** get new facebook hashkey */
  private void getNewFacebookKeyHash()
  {
    try
    {
      PackageInfo info = mActivity.getPackageManager().getPackageInfo( mActivity.getPackageName(),
          PackageManager.GET_SIGNATURES );
      
      for( Signature signature : info.signatures )
      {
        MessageDigest md = MessageDigest.getInstance( "SHA" );
        md.update( signature.toByteArray() );
        Log.d( "KeyHash", Base64.encodeToString( md.digest(), Base64.DEFAULT ) );
      }
    }
    catch( Throwable ignored )
    {
      // do nothing
    }
  }
  
  /** login facebook */
  public void loginFacebook()
  {
    mPrefs = PreferenceManager.getDefaultSharedPreferences( mActivity );
    
    String access_token = mPrefs.getString( "access_token", null );
    long expires = mPrefs.getLong( "access_expires", 0 );
    
    if( access_token != null )
      facebook.setAccessToken( access_token );
    
    if( expires != 0 )
      facebook.setAccessExpires( expires );
    
    if( !facebook.isSessionValid() )
      facebook.authorize( mActivity, permissions, this );
  }
  
  /** @return facebook token */
  public String getAccessToken()
  {
    return facebook.getAccessToken();
  }
  
  /** log out from facebook */
  public void logoutFromFacebook()
  {
    mAsyncRunner.logout( this, this );
  }
  
  @Override
  public void onActivityResult( int requestCode, int resultCode, Intent data )
  {
    super.onActivityResult( requestCode, resultCode, data );
    
    facebook.authorizeCallback( requestCode, resultCode, data );
  }
  
  /** get profile information */
  public void getProfileInformation()
  {
    mAsyncRunner.request( "me", this );
  }
  
  @Override
  public void onComplete( String response, Object state )
  {
    if( !Boolean.parseBoolean( response ) == true )
    {
      // LetIsGoTogetherAPP app = ( LetIsGoTogetherAPP )getApplicationContext();
      Log.d( "Profile", response );
      
      String json = response;
      
      CurrentUser user = Primary.fromJson( json, CurrentUser.class );
      // app.setCurrentUser( user );
      
      user.facebookToken = getAccessToken();
    }
    else
    {
      Log.d( "Logout from Facebook", response );
      
      // User successfully Logged out
    }
  }
  
  @Override
  public void onComplete( Bundle values )
  {
    // Function to handle complete event
    // Edit Preferences and update facebook acess_token
    SharedPreferences.Editor editor = mPrefs.edit();
    editor.putString( "access_token", facebook.getAccessToken() );
    editor.putLong( "access_expires", facebook.getAccessExpires() );
    editor.commit();
  }
  
  @Override
  public void onFacebookError( FacebookError e )
  {
  }
  
  @Override
  public void onError( DialogError e )
  {
  }
  
  @Override
  public void onCancel()
  {
  }
  
  @Override
  public void onIOException( IOException e, Object state )
  {
  }
  
  @Override
  public void onFileNotFoundException( FileNotFoundException e, Object state )
  {
  }
  
  @Override
  public void onMalformedURLException( MalformedURLException e, Object state )
  {
  }
  
  @Override
  public void onFacebookError( FacebookError e, Object state )
  {
  }
  
}
