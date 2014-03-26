package com.softwest.friendstogether.activity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.friendstogether.activity.R;
import com.softwest.friendstogether.web.responces.CurrentUser;

public class LoginFacebookActivity
  extends Activity
{
  private String APP_ID  = "1419097081679140";
  // Instance of Facebook Class
  private Facebook facebook;
  
  @SuppressWarnings( "deprecation" )
  private AsyncFacebookRunner mAsyncRunner;
  String FILENAME = "AndroidSSO_data";
  private SharedPreferences mPrefs;
  
  @Override
  protected void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
  
    getNewFacebookKeyHash();
   
    loginFacebook();
    CurrentUser user = new CurrentUser();
    
    getProfileInformation();
  }
  
  private void getNewFacebookKeyHash()
  {
    try
    {
      PackageInfo info = getPackageManager().getPackageInfo( this.getPackageName(), PackageManager.GET_SIGNATURES );
   
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

  @SuppressWarnings( "deprecation" )
  private void loginFacebook()
  {
    facebook = new Facebook( APP_ID );
    mAsyncRunner = new AsyncFacebookRunner( facebook );
    
    mPrefs = getPreferences( MODE_PRIVATE );
    String access_token = mPrefs.getString( "access_token", null );
    long expires = mPrefs.getLong( "access_expires", 0 );
    
    if( access_token != null )
    {
      facebook.setAccessToken( access_token );
    }
    
    if( expires != 0 )
    {
      facebook.setAccessExpires( expires );
    }
    
    if( !facebook.isSessionValid() )
    {
      facebook.authorize( this, new String[]{ "email", "publish_stream" }, new DialogListener()
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
    }
  }
  @SuppressWarnings( "deprecation" )
  public void getProfileInformation() {
    mAsyncRunner.request("me", new RequestListener() {
        @Override
        public void onComplete(String response, Object state) {
            Log.d("Profile", response);
            String json = response;
            try {
                JSONObject profile = new JSONObject(json);
                // getting name of the user
                final String name = profile.getString("name");
                // getting email of the user
                final String email = profile.getString("email");
 
                runOnUiThread(new Runnable() {
 
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Name: " + name + "\nEmail: " + email, Toast.LENGTH_LONG).show();
                    }
 
                });
 
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
 
        @Override
        public void onIOException(IOException e, Object state) {
        }
 
        @Override
        public void onFileNotFoundException(FileNotFoundException e,
                Object state) {
        }
 
        @Override
        public void onMalformedURLException(MalformedURLException e,
                Object state) {
        }
 
        @Override
        public void onFacebookError(FacebookError e, Object state) {
        }
    });
}
}
