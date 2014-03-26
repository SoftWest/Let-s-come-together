package com.softwest.friendstogether.activity;

import android.content.Intent;
import android.os.Bundle;

import com.friendstogether.activity.R;

public class MainActivity
  extends BaseActivity
{
  @Override
  protected void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    
    setContentView( R.layout.activity_base );
    
    Intent intent = new Intent( this, LoginActivity.class );
    startActivity( intent );
    
  }
  
}
