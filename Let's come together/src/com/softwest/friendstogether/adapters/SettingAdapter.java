package com.softwest.friendstogether.adapters;

import com.softwest.friendstogether.LetIsGoTogetherAPP;
import com.softwest.friendstogether.activity.R;
import com.softwest.friendstogether.fragment.SettingFragment;
import com.softwest.friendstogether.utils.RangeSeekBar;
import com.softwest.friendstogether.utils.RangeSeekBar.OnRangeSeekBarChangeListener;
import com.softwest.friendstogether.web.responses.SaveSearchFilter;
import com.softwest.friendstogether.web.responses.wrappers.SearchParam;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;

public class SettingAdapter
  extends BaseAdapter
{
  private static final int COUNT_VIEW = 2;
  private static final int ENUM_TYPE_VIEW = 0;
  private static final int INTEGER_TYPE_VIEW = 1;
  private static final int MULTIPART_TYPE_VIEW = 2;
  
  private static final String ENUM = "enum";
  private static final String MULTIPARAT = "multipart";
  private static final String INTEGER = "number";
  
  private Context mContext;
  private SearchParam mSearchParam;
  private LayoutInflater mInflator;
  public SettingAdapter( Context context, SaveSearchFilter saveFilter )
  {
    mContext = context;
    mSearchParam = saveFilter.param;
    mInflator = (LayoutInflater)mContext.getSystemService( Activity.LAYOUT_INFLATER_SERVICE );
  }
  
  @Override
  public int getCount()
  {
    return 3;
  }
  
  @Override
  public int getViewTypeCount()
  {
    return COUNT_VIEW;
  }
  
  @Override
  public int getItemViewType( int position )
  {
   String enumtype = mSearchParam.user_search_look.type;
   String multiparttype = mSearchParam.user_search_astro.type;
   String integer = mSearchParam.user_search_age_max.type;
   
    if(enumtype.equalsIgnoreCase( ENUM ))
    return ENUM_TYPE_VIEW;
    if(multiparttype.equalsIgnoreCase( MULTIPARAT ))
      return MULTIPART_TYPE_VIEW;
    if(integer.equalsIgnoreCase( INTEGER ))
      return INTEGER_TYPE_VIEW;
    
  return position;
 
  }
  
  @Override
  public Object getItem( int position )
  {
   return 0;
  }
  
  @Override
  public long getItemId( int position )
  {
    
    return position;
  }
  
  @Override
  public View getView( int position, View convertView, ViewGroup parent )
  {
   int type = getItemViewType( position );
    
    switch( type )
    {
      case ENUM_TYPE_VIEW:
        convertView = createEnumView(position,convertView,parent);
        break;
      case MULTIPART_TYPE_VIEW:
        convertView = createMultipartView(position,convertView,parent);
        break;
      case INTEGER_TYPE_VIEW:
        convertView = createIntegerView(position,convertView,parent);
        break;
    }
    return convertView;
  }
  private View createIntegerView( int position, View convertView, ViewGroup parent )
  {
    ViewHolder holder = null;
    if(convertView == null)
    {
      holder = new ViewHolder();
    // add RangeSeekBar to pre-defined layout
      convertView = mInflator.inflate( R.layout.elm_renge_seek_bar, null );
      final long minAge = 2;
      final long maxAge = 99;
      RangeSeekBar<Long> seekBar = new RangeSeekBar<Long>(minAge , maxAge, mContext);
      seekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Long>() {
              @Override
              public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Long minValue, Long maxValue) {
                      // handle changed range values
                      Log.i(LetIsGoTogetherAPP.TAG, "User selected new date range: MIN=" +minAge + ", MAX=" + maxAge);
              }
      });
      
      ViewGroup layout = ( ViewGroup )convertView.findViewById( R.id.fl_seekbar );
      layout.addView( seekBar );
    
      convertView.setTag( holder );
    }
    else
    {
      holder = (ViewHolder)convertView.getTag();
      
    }
      
   return  convertView;
  }

  private View createMultipartView( int position, View convertView, ViewGroup parent )
  {
    ViewHolder holder = null;
    
    if(convertView == null)
    {
      holder = new ViewHolder();
      
      
      convertView.setTag( holder );
    }else
    {
      holder = (ViewHolder)convertView.getTag();
      
    }
    
    return convertView;
  }

  private View createEnumView( int position, View convertView, ViewGroup parent )
  {
    ViewHolder holder = null;
    if(convertView == null)
    {
     holder = new ViewHolder(); 
     convertView = mInflator.inflate( R.layout.elm_search_enum_view, null ); 
    
   // String[] looking = mSearchParam.user_search_look.options;
    holder.spinner_enum = (Spinner)convertView.findViewById( R.id.sp_enum );
    
 //   ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, );
//    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
//     holder.spinner_enum.setAdapter( spinnerArrayAdapter );
    convertView.setTag( holder );
    }
    else
    {
      holder = (ViewHolder)convertView.getTag();
    
    }
    
    return convertView;
  }
  private class ViewHolder
  {
   public ViewGroup layout;
   public Spinner spinner_enum;
  }
}
