package com.softwest.friendstogether.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.friendstogether.activity.R;

public class MainActivity
  extends BaseActivity
{
  @Override
  protected void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    
    setContentView( R.layout.activity_base );
    
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    
    String access_token = preferences.getString( "access_token", null );
    
    if( null == access_token )
    {
      Intent intent = new Intent( this, LoginActivity.class );
      startActivity( intent );
    }
    else
    {
      Intent intent = new Intent( this, LoginFacebookActivity .class );
      startActivity( intent );
    }
  }
  
}
