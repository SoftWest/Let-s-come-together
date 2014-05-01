package com.softwest.friendstogether.fragment;

import com.softwest.friendstogether.activity.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RegistrationFragment
  extends BaseFragment
{
  @Override
  public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
  {
   View v = inflater.inflate( R.layout.fg_registration, null );
   
    return v;
  }
  
}
