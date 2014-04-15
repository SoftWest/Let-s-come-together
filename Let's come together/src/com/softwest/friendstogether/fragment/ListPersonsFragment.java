package com.softwest.friendstogether.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.friendstogether.activity.R;
import com.softwest.friendstogether.adapters.PersonAdapter;

public class ListPersonsFragment
  extends ListFragment implements OnClickListener
{
 private ListView mListView;
 private View mView;
 private PersonAdapter mAdapter;

 
  @Override
  public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
  {
 
    View v = mView = inflater.inflate( R.layout.fg_person_list, null );
   
    mListView = (ListView)getActivity().findViewById( R.id.list_persons );
    
   // mAdapter = new PersonAdapter(getActivity());
    
   // mListView.setAdapter( adapter );
    
    return v;
  }

  @Override
  public void onClick( View v )
  {
   switch( v.getId() )
  {
    case R.id.iv_check_in:
      Log.i( "check in click", "checkIn click" );
      break;
   }
    
  }
}
