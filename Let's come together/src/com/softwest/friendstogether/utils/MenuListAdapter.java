package com.softwest.friendstogether.utils;

import java.util.ArrayList;
import com.softwest.friendstogether.activity.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuListAdapter
  extends BaseAdapter
{
  private Context mContext;
  private ArrayList<MenuNavDrawerItem> mDrawerItems;
  
  public MenuListAdapter( Context context, ArrayList<MenuNavDrawerItem> mDrawerItems )
  {
    this.mContext = context;
    this.mDrawerItems = mDrawerItems;
  }
  
  @Override
  public int getCount()
  {
    return mDrawerItems.size();
  }
  
  @Override
  public Object getItem( int position )
  {
    return mDrawerItems.get( position );
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
    LayoutInflater mInflater = ( LayoutInflater )mContext.getSystemService( Activity.LAYOUT_INFLATER_SERVICE );
    
    if( convertView == null )
    {
      holder = new ViewHolder();
      
      convertView = mInflater.inflate( R.layout.drawer_list_item, null );
      
      holder.imgIcon = ( ImageView )convertView.findViewById( R.id.icon );
      holder.txtTitle = ( TextView )convertView.findViewById( R.id.title );
      holder.txtCount = ( TextView )convertView.findViewById( R.id.counter );
      
      holder.imgIcon.setImageResource( mDrawerItems.get( position ).getIcon() );
      holder.txtTitle.setText( mDrawerItems.get( position ).getTitle() );
      
      convertView.setTag( holder );
    }
    else
    {
    holder = (ViewHolder)convertView.getTag();
    
    holder.imgIcon.setImageResource( mDrawerItems.get( position ).getIcon() );
    holder.txtTitle.setText( mDrawerItems.get( position ).getTitle() );
    }
    // check whether it set visible or not
    if( mDrawerItems.get( position ).getCounterVisibility() )
      holder.txtCount.setText( mDrawerItems.get( position ).getCount() );
    else
      holder.txtCount.setVisibility( View.GONE );
   
    return convertView;
  }
 private class ViewHolder
 {
   ImageView imgIcon;
   TextView txtTitle;
   TextView txtCount;
 }
}
