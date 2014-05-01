package com.softwest.friendstogether.fragment;

import com.softwest.friendstogether.LetIsGoTogetherAPP;
import com.softwest.friendstogether.activity.R;
import com.softwest.friendstogether.web.WebApi;
import com.softwest.friendstogether.web.responses.CurrentUser;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class ProfileFragment extends BaseFragment 
implements OnClickListener
{
  private ImageView user_Photo;
  private TextView  user_First_name;
  private TextView  user_Last_name;
  private TextView  user_Email;
  private TextView  user_Phone;
  private TextView  user_Gender;
  private TextView  user_Birthday;
  private Bitmap fFacebookPhoto;
  private String fFacebookToken;
  private Button btn_save_setting;
  private final static String IMAGE_AVATAR ="avatar";
  @Override
  public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
  {
   View v = inflater.inflate( R.layout.fg_user_profile, null );
   
   user_Photo = ( ImageView )v.findViewById( R.id.pf_user_photo );
   user_Last_name = ( TextView )v.findViewById( R.id.pf_last_name );
   user_First_name = ( TextView )v.findViewById( R.id.pf_first_name );
   user_Email = ( TextView )v.findViewById( R.id.tv_user_email );
   user_Phone = ( TextView )v.findViewById( R.id.tv_phone );
   user_Gender= ( TextView )v.findViewById( R.id.tv_user_gender );
   user_Birthday= ( TextView )v.findViewById( R.id.tv_birthday );
   // get information about current user

   LetIsGoTogetherAPP app = ( LetIsGoTogetherAPP )getActivity().getApplicationContext();
   
   final CurrentUser user = app.getCurrentUser();
 
   String redirectUri = WebApi.getRedirectUri(String.valueOf( user.id ),IMAGE_AVATAR);
   fFacebookPhoto = WebApi.getImageFromUri( redirectUri );
   
   fFacebookToken = user.facebookToken;

   UserInfo( user );
   
   btn_save_setting = (Button)v.findViewById(R.id.btn_save_setting);
   
   btn_save_setting.setOnClickListener(this);
   
   return v;
  }
  
  private void UserInfo( final CurrentUser user )
  {
    user_First_name.setText( user.first_name );
    
    user_Last_name.setText( user.last_name );
    
    user_Photo.setImageBitmap( fFacebookPhoto );
    
    user_Email.setText( user.email );
    
    user_Birthday.setText( user.birthday );
    
    user_Gender.setText( user.gender );
    
    //user_Phone.setText( user.phone );
  }

  @Override
  public void onClick( View v )
  {
    //do nothing
    
  }
 
}
