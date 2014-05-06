package com.softwest.friendstogether.adapters;

import com.softwest.friendstogether.fragment.SettingFragment;
import com.softwest.friendstogether.web.responses.SaveSearchFilter;
import com.softwest.friendstogether.web.responses.wrappers.SearchParam;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SettingAdapter
  extends BaseAdapter
{
  private static final int COUNT_VIEW = 2;
  private static final int ENUM_TYPE_VIEW = 0;
  private static final int INTEGER_TYPE_VIEW = 1;
  private static final int MULTIPART_TYPE_VIEW = 2;
  private Context mContext;
  private SearchParam mSearchParam;
  private static final String ENUM = "enum";
  private static final String MULTIPARAT = "multipart";
  private static final String INTEGER = "number";
  
  public SettingAdapter( Context context, SaveSearchFilter saveFilter )
  {
    mContext = context;
    mSearchParam = saveFilter.param;
    
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
   String integer = mSearchParam.user_age_max.type;
   
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
    ViewHolder holder = null;
    
    int type = getItemViewType( position );
    
    switch( type )
    {
      case ENUM_TYPE_VIEW:
        createEnumView(position,convertView,parent);
        break;
      case MULTIPART_TYPE_VIEW:
        createMultipartView(position,convertView,parent);
        break;
      case INTEGER_TYPE_VIEW:
        createIntegerView(position,convertView,parent);
        break;
    }
    return null;
  }
  private void createIntegerView( int position, View convertView, ViewGroup parent )
  {
    
  }

  private void createMultipartView( int position, View convertView, ViewGroup parent )
  {
    
  }

  private void createEnumView( int position, View convertView, ViewGroup parent )
  {
    
  }
  private class ViewHolder
  {
    
  }
}
