package com.softwest.friendstogether.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.softwest.friendstogether.LetIsGoTogetherAPP;
import com.softwest.friendstogether.activity.R;
import com.softwest.friendstogether.web.responses.CurrentUser;

public class FitBeakFragment
  extends BaseFragment implements OnClickListener,OnEditorActionListener

{
  private Button mBtnSend;
  private EditText mUserComment;
  private RatingBar mAppRate;
  
  @Override
  public final View onCreateView( final LayoutInflater inflater, final ViewGroup container,
      final Bundle savedInstanceState )
  {
    View v = inflater.inflate( R.layout.fg_fitbeak_page, null );
    
    mBtnSend = ( Button )v.findViewById( R.id.bt_app_send );
    mUserComment = ( EditText )v.findViewById( R.id.app_comment );
    mAppRate = ( RatingBar )v.findViewById( R.id.app_rating );
    
    LetIsGoTogetherAPP app = ( LetIsGoTogetherAPP )getActivity().getApplicationContext();
    final CurrentUser user = app.getCurrentUser();
    
    mBtnSend.setOnClickListener( this);
    mUserComment.setOnEditorActionListener(this);
    
    return v;
  }
  @Override
  public void onClick( final View v )
  {
    String comment  = null;
    String app_rate = String.valueOf( mAppRate.getRating() );
    
    comment = mUserComment.getText().toString();
  }
  @Override
  public boolean onEditorAction( TextView v, int keyCode, KeyEvent event )
  {
    if( ( event.getAction() == KeyEvent.ACTION_DOWN ) && ( keyCode == KeyEvent.KEYCODE_ENTER ) )
    {
      // hide virtual keyboard
      InputMethodManager imm = ( InputMethodManager )getActivity().getApplicationContext().getSystemService(
          Context.INPUT_METHOD_SERVICE );
      imm.hideSoftInputFromWindow( mUserComment.getWindowToken(), 0 );
      return true;
    }
    return false;
  }
}
