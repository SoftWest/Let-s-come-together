package com.softwest.friendstogether.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.friendstogether.activity.R;

public class PersonAdapter
  extends BaseAdapter
{
  private Context mContext;
  
  public PersonAdapter( Context context )
  {
    mContext = context;
  }
  
  @Override
  public int getCount()
  {
    
    return 1;
  }
  
  @Override
  public Object getItem( int position )
  {
    
    return 1;
  }
  
  @Override
  public long getItemId( int position )
  {
    
    return position;
  }
  
  @Override
  public View getView( int position, View view, ViewGroup parent )
  {
    ViewHolder holder = null;
    LayoutInflater inflator = ( LayoutInflater )mContext.getSystemService( Activity.LAYOUT_INFLATER_SERVICE );
    
    if( null != view )
    {
      holder = new ViewHolder();
      
      inflator.inflate( R.layout.row_person, null );
    
    ImageView icon = (ImageView)view.findViewById( R.id.iv_person_icon );
    ImageView lastTimeCheckIn = (ImageView)view.findViewById( R.id.iv_check_in );
    
    TextView personName = (TextView)view.findViewById( R.id.tv_name );
    TextView place = (TextView)view.findViewById( R.id.tv_place );
    TextView city = (TextView)view.findViewById( R.id.tv_city );
    TextView signIn = (TextView)view.findViewById( R.id.tv_sign_in );
    
    view.setTag( holder );
    }else
    {
      holder = (ViewHolder)view.getTag();
      
      holder.icon.setImageResource( R.drawable.ic_launcher );
      holder.checkIn.setImageResource( R.drawable.ic_launcher );
      
      holder.personName.setText( "person name" );
      holder.place.setText( "place" );
      holder.city.setText( "city" );
      holder.lastTimeCheckIn.setText( "last time check in" );
    }
    
    return view;
  }
  private class ViewHolder
  {
  public  ImageView icon;
  public  ImageView checkIn;
  
  public  TextView personName;
  public  TextView place;
  public  TextView city;
  public  TextView lastTimeCheckIn;
    
  }
}
