package com.softwest.friendstogether.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.friendstogether.activity.R;
import com.softwest.friendstogether.fragment.RegistrationFragment;

public class LoginActivity
  extends BaseActivity
  implements OnClickListener
{
  private Button mLoginFacebook;
  private Button mLoginProgram;
  private TextView mSigIn;
  
  @Override
  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    
    setContentView( R.layout.fg_login );
    
    mLoginFacebook = ( Button )findViewById( R.id.btn_facebook );
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
        Intent intent = new Intent(this,LoginFacebookActivity.class);
        startActivity( intent );
//        LoginFacebookActivity login = new LoginFacebookActivity( this );
//        login.loginFacebook();
  //      login.getProfileInformation();
       
        break;
      
      case R.id.btn_twitter:
        
        break;
      case R.id.tv_sign_in:
        replace( RegistrationFragment.class, R.id.fl_main, null, true );
        break;
    
    }
  }
 
}
