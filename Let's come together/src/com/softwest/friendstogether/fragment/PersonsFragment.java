package com.softwest.friendstogether.fragment;

import com.friendstogether.activity.R;
import com.softwest.friendstogether.adapters.PersonAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class PersonsFragment
  extends BaseFragment
{
 private ListView mListView;
 private View mView;
 private PersonAdapter mAdapter;
 
  @Override
  public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
  {
  
    View v = mView = inflater.inflate( R.layout.user_map, null );
   
    mListView = (ListView)getActivity().findViewById( R.id.list_persons );
 
    mAdapter = new PersonAdapter(getActivity());
    
    mListView.setAdapter( mAdapter );
    
    return v;
  }
}
