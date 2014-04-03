package com.softwest.friendstogether.activity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
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
 
{
  private String APP_ID = "1419097081679140";
  // Instance of Facebook Class
  private Facebook facebook;
  
  private AsyncFacebookRunner mAsyncRunner;
  String FILENAME = "AndroidSSO_data";
  private SharedPreferences mPrefs;
  private Activity mActivity;
  
  // private CurrentUser mUser;
public LoginFacebookActivity(Activity activity)
{
  mActivity = activity;
  
  getNewFacebookKeyHash();
  
  facebook = new Facebook( APP_ID );
  mAsyncRunner = new AsyncFacebookRunner( facebook );

  loginFacebook();
}  
  
  private void getNewFacebookKeyHash()
  {
    try
    {
      PackageInfo info = mActivity.getPackageManager().getPackageInfo( mActivity.getPackageName(), PackageManager.GET_SIGNATURES );
      
      for( Signature signature : info.signatures )
      {
        MessageDigest md = MessageDigest.getInstance( "SHA" );
        md.update( signature.toByteArray() );
        Log.d( "KeyHash", Base64.encodeToString( md.digest(), Base64.DEFAULT ) );
      }
    }
    catch( NameNotFoundException e )
    {
      e.printStackTrace();
    }
    catch( NoSuchAlgorithmException e )
    {
      e.printStackTrace();
    }
  }
  
  public void loginFacebook()
  {
    mPrefs = mActivity.getPreferences( mActivity.MODE_PRIVATE );
    String access_token = mPrefs.getString( "access_token", null );
    long expires = mPrefs.getLong( "access_expires", 0 );
    
    if( access_token != null )
      facebook.setAccessToken( access_token );
    
    if( expires != 0 )
      facebook.setAccessExpires( expires );
    
    if( !facebook.isSessionValid() )
      facebook.authorize( mActivity, new String[]{ "email", "publish_stream" }, new DialogListener()
      {
        @Override
        public void onCancel()
        {
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
        
      } );
    getProfileInformation();
  }
  
  public void logoutFromFacebook()
  {
    mAsyncRunner.logout( mActivity, new RequestListener()
    {
      @Override
      public void onComplete( String response, Object state )
      {
        Log.d( "Logout from Facebook", response );
        if( Boolean.parseBoolean( response ) == true )
        {
          // User successfully Logged out
        }
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
    } );
  }
  
//  @Override
//  public void onActivityResult( int requestCode, int resultCode, Intent data )
//  {
//    super.onActivityResult( requestCode, resultCode, data );
//    facebook.authorizeCallback( requestCode, resultCode, data );
//  }
  
  public void getProfileInformation()
  {
    mAsyncRunner.request( "me", new RequestListener()
    {
      @Override
      public void onComplete( String response, Object state )
      {
        Log.d( "Profile", response );
        
        String json = response;
        
        CurrentUser user = Primary.fromJson( json, CurrentUser.class );
       
           Intent mapIntent = new Intent( mActivity, MapActivity.class );
            mActivity.startActivity( mapIntent );
        
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
    } );
  }
}
