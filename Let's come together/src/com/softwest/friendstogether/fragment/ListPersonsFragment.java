package com.softwest.friendstogether.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.softwest.friendstogether.activity.R;
import com.softwest.friendstogether.adapters.PersonAdapter;
import com.softwest.friendstogether.web.responses.PeopleNearMe;

@SuppressLint( "ValidFragment" )
public class ListPersonsFragment
  extends ListFragment
  implements OnClickListener
{
  private ListView mListView;
  private View mView;
  private PersonAdapter mAdapter;
  private List<PeopleNearMe> mListPeople = new ArrayList<PeopleNearMe>();
  
  public ListPersonsFragment( List<PeopleNearMe> peopleList )
  {
   mListPeople = peopleList; 
  }
  public ListPersonsFragment()
  {
   //empty constructor
  }
  
  @Override
  public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
  {
    mView = inflater.inflate( R.layout.fg_person_list, null );
    
    mAdapter = new PersonAdapter( getActivity(),mListPeople );
   
    setListAdapter( mAdapter );
    
    return mView;
  }
  
  @Override
  public void onClick( View v )
  {
    switch( v.getId() )
    {
    // case R.id.iv_check_in:
    // Log.i( "check in click", "checkIn click" );
    // break;
    }
    
  }
}
