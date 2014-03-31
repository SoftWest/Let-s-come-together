package com.softwest.friendstogether.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.friendstogether.activity.R;

public class LoginActivity
  extends BaseActivity
  implements OnClickListener
{
  private Button mLoginFacebook;
  private Button mLoginProgram;
  
  @Override
  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    
    setContentView( R.layout.login_activity );
    
    mLoginFacebook = ( Button )findViewById( R.id.login_facebook );
    mLoginProgram = ( Button )findViewById( R.id.login_twitter );
    
    mLoginFacebook.setOnClickListener( this );
    mLoginProgram.setOnClickListener( this );
    
   
  }
  
  @Override
  public void onClick( View v )
  {
    switch( v.getId() )
    {
      case R.id.login_facebook:
        Intent intent = new Intent( this, LoginFacebookActivity.class );
        startActivity( intent );
        break;
      
      case R.id.login_twitter:
        
        break;
    
    }
  }
  
}
