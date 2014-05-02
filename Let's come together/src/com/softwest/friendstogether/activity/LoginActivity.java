package com.softwest.friendstogether.activity;

import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.widget.LoginButton;
import com.softwest.friendstogether.fragment.RegistrationFragment;
import com.softwest.friendstogether.utils.NetworkUtil;

public class LoginActivity
  extends BaseActivity
  implements OnClickListener
{
  private LoginButton mLoginFacebook;
  private Button mLoginProgram;
  private TextView mSigIn;
  
  private String[] permissions = new String[]{ "read_stream", "email", "publish_actions", "read_friendlists",
      "user_location", "user_friends", "user_status", "friends_location" };
  
  @Override
  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    
    setContentView( R.layout.fg_login );
    
    mLoginFacebook = ( LoginButton )findViewById( R.id.btn_facebook );
    mLoginFacebook.setReadPermissions( permissions );
    
    mLoginProgram = ( Button )findViewById( R.id.btn_twitter );
    mSigIn = ( TextView )findViewById( R.id.tv_sign_in );
    
    mLoginFacebook.setOnClickListener( this );
    mLoginProgram.setOnClickListener( this );
    mSigIn.setOnClickListener( this );
  
  }
  @Override
  public void onClick( View v )
  {
    switch( v.getId() )
    {
      case R.id.btn_facebook:
        if( NetworkUtil.isConnectingToInternet( this ) )
        {
          Intent intent = new Intent( this, LoginFacebookActivity.class );
          startActivity( intent );
        }
        else
          Toast.makeText( this, R.string.no_itnernet_connect, Toast.LENGTH_LONG ).show();
        break;
      
      case R.id.btn_twitter:
        
        break;
      case R.id.tv_sign_in:
        replace( RegistrationFragment.class, R.id.fl_main, null, true );
        break;
    
    }
  }
  @Override
  public void onBackPressed()
  {
    getSupportFragmentManager().popBackStackImmediate();
   
   // super.onBackPressed();
  }
}
