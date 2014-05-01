package com.softwest.friendstogether.adapters;

import java.util.ArrayList;
import java.util.List;

import com.softwest.friendstogether.activity.R;
import com.softwest.friendstogether.web.WebApi;
import com.softwest.friendstogether.web.responses.PeopleNearMe;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonAdapter
  extends BaseAdapter
{
  private Context mContext;
  private List<PeopleNearMe> mListPeople = new ArrayList<PeopleNearMe>();
  
  public PersonAdapter( Context context, List<PeopleNearMe> listPeople )
  {
    mContext = context;
    mListPeople = listPeople;
  }
  
  @Override
  public int getCount()
  {
    return mListPeople.size();
  }
  
  @Override
  public Object getItem( int position )
  {
    return mListPeople.get( position );
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
    
    PeopleNearMe people = mListPeople.get( position );
    
    if( null == view )
    {
      view = inflator.inflate( R.layout.row_person, null );
      holder = new ViewHolder();
      
      holder.icon = ( ImageView )view.findViewById( R.id.iv_person_icon );
      holder.city = ( TextView )view.findViewById( R.id.tv_city );
      holder.personName = ( TextView )view.findViewById( R.id.tv_name );
      holder.lastTimeCheckIn = ( TextView )view.findViewById( R.id.tv_last_time_check );
      
      holder.icon.setImageBitmap( WebApi.getImageFromUri( WebApi.WEB_HOST + people.user_pic ) );
      holder.personName.setText( people.user_nickname );
      holder.place = ( TextView )view.findViewById( R.id.tv_place );
      holder.place.setText( people.place_desc );
      holder.city.setText( people.user_birthday );
      holder.lastTimeCheckIn.setText( people.diff_tm );
      
      view.setTag( holder );
    }
    else
    {
      holder = ( ViewHolder )view.getTag();
      
      holder.icon.setImageBitmap( WebApi.getImageFromUri( WebApi.WEB_HOST + people.user_pic ) );
      holder.personName.setText( people.user_nickname );
      holder.place = ( TextView )view.findViewById( R.id.tv_place );
      holder.place.setText( people.place_desc );
      holder.city.setText( people.user_birthday );
      holder.lastTimeCheckIn.setText( people.diff_tm );
    }
    return view;
  }
  
  private class ViewHolder
  {
    public ImageView icon;
    public ImageView checkIn;
    
    public TextView personName;
    public TextView place;
    public TextView city;
    public TextView lastTimeCheckIn;
    
  }
}
