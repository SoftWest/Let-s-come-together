package com.softwest.friendstogether.fragment;

import java.util.Locale;

import com.softwest.friendstogether.LetIsGoTogetherAPP;
import com.softwest.friendstogether.activity.R;
import com.softwest.friendstogether.utils.JsonObjectType;
import com.softwest.friendstogether.utils.RangeSeekBar;
import com.softwest.friendstogether.utils.RangeSeekBar.OnRangeSeekBarChangeListener;
import com.softwest.friendstogether.web.handlers.HttpMethods;
import com.softwest.friendstogether.web.handlers.IResponse;
import com.softwest.friendstogether.web.responses.Primary;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class SettingFragment
  extends BaseFragment
  implements OnClickListener, IResponse
{
  @Override
  public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
  {
    View v = inflater.inflate( R.layout.fg_setting, null );
    
    Button btSave = ( Button )v.findViewById( R.id.bt_save );
    btSave.setOnClickListener( this );
    
    RangeSeekBar<Integer> seekBar = new RangeSeekBar<Integer>( 20, 75, getActivity() );
    seekBar.setOnRangeSeekBarChangeListener( new OnRangeSeekBarChangeListener<Integer>()
    {
      @Override
      public void onRangeSeekBarValuesChanged( RangeSeekBar<?> bar, Integer minValue, Integer maxValue )
      {
        // handle changed range values
        Log.i( LetIsGoTogetherAPP.TAG, "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue );
      }
    } );
    
    // add RangeSeekBar to pre-defined layout
    ViewGroup layout = ( ViewGroup )v.findViewById( R.id.fl_seekbar );
    layout.addView( seekBar );
    
    return v;
  }
  
  @Override
  public void onClick( View v )
  {
    switch( v.getId() )
    {
      case R.id.bt_save:
        String location = Locale.getDefault().getDisplayLanguage();
        
        HttpMethods.saveSearchSettings( getActivity(), 1, location, this, JsonObjectType.SAVE_SEARCH_SETTINGS );
        break;
      
      default:
        break;
    }
    
  }
  
  @Override
  public Primary process( String json, JsonObjectType classInfo )
  {
    Log.i( LetIsGoTogetherAPP.TAG, "response " + json );
    return null;
  }
}
