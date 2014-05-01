package com.softwest.friendstogether.activity;

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

import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.softwest.friendstogether.LetIsGoTogetherAPP;
import com.softwest.friendstogether.activity.MapActivity.IFacebookLogOut;
import com.softwest.friendstogether.web.responses.CurrentUser;
import com.softwest.friendstogether.web.responses.Primary;

@SuppressWarnings( "deprecation" )
public class LoginFacebookActivity
  extends BaseActivity
  implements StatusCallback, GraphUserCallback,IFacebookLogOut

{
  // Instance of Facebook Class
  String FILENAME = "AndroidSSO_data";
  private static Activity mActivity;
  
  @Override
  protected void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    
    setContentView( R.layout.act_login_facebook );
    
    mActivity = this;
    
    getNewFacebookKeyHash();
    
    loginFacebook();
    
  }
  
  /** empty constructor */
  public LoginFacebookActivity()
  {
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
    Session.openActiveSession( mActivity, true, this );
  }
  
  @Override
  public void onActivityResult( int requestCode, int resultCode, Intent data )
  {
    super.onActivityResult( requestCode, resultCode, data );
    Session.getActiveSession().onActivityResult( mActivity, requestCode, resultCode, data );
  }
  
  @Override
  public void call( Session session, SessionState state, Exception exception )
  {
    if( session.isOpened() )
      Request.executeMeRequestAsync( session, this );
  }
  
  @Override
  public void onCompleted( GraphUser user, Response response )
  {
    if( null != user )
    {
      SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences( mActivity );
      SharedPreferences.Editor editor = preferences.edit();
      editor.putString( "access_token", getFacebookToken() );
      editor.commit();
      
      GraphObject object = user;
      String json = object.getInnerJSONObject().toString();
      
      Log.d( LetIsGoTogetherAPP.TAG, "response " + "user information" + json );
      
      CurrentUser currentUser = Primary.fromJson( json, CurrentUser.class );
      currentUser.facebookToken = getFacebookToken();
      
      LetIsGoTogetherAPP app = ( LetIsGoTogetherAPP )mActivity.getApplicationContext();
      app.setCurrentUser( currentUser );
      
      Intent intent = new Intent( mActivity, MapActivity.class);
      mActivity.startActivity( intent );
      MapActivity.setFacebookLogOut( this );
    }
  }
  
  private String getFacebookToken()
  {
    Session session = Session.getActiveSession();
    
    if( session.isOpened() && null != session )
      return session.getAccessToken();
    
    return null;
  }

  @Override
  public void facebookLogOut()
  {
    if( Session.getActiveSession() != null )
    {
      Session.getActiveSession().closeAndClearTokenInformation();
    }
    Session.setActiveSession( null );
    
    Intent intentLogOut = new Intent( this, LoginActivity.class );
    startActivity( intentLogOut );
  }
  
}
