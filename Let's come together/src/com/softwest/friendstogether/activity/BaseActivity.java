package com.softwest.friendstogether.activity;

import java.lang.ref.WeakReference;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public abstract class BaseActivity
  extends FragmentActivity
{
  private static WeakReference<BaseActivity> activity = null;
  
  protected String mCurrentFragmentClassName;
  
  @Override
  protected void onCreate( Bundle saved )
  {
    activity = new WeakReference<BaseActivity>( this );
    
    super.onCreate( saved );
    
  }
  
  public Fragment getCurrentFragment( int fragment_hold )
  {
    return getSupportFragmentManager().findFragmentById( fragment_hold );
  }
  
  public FragmentManager getSupportFragmentManager()
  {
    // To avoid bug for fragments: Step 3 of 3
    if( this == activity.get() )
    {
      return super.getSupportFragmentManager();
    }
    
    return activity.get().getSupportFragmentManager();
  }
  
  /** Replace in activity current fragment by another.
   * 
   * @param classInfo class info of new fragment */
  public void replace( final Class<? extends Fragment> classInfo )
  {
    replace( classInfo, null );
  }
  
  /** Replace in activity current fragment by another.
   * 
   * @param classInfo class info of new fragment.
   * @param args arguments for newly created fragment. */
  public void replace( final Class<? extends Fragment> classInfo, final Bundle args )
  {
    replace( classInfo, 0, args, false );
  }
  
  /** Replace in activity current fragment by another.
   * 
   * @param classInfo class info of new fragment.
   * @param useStack true - add to "back Stack", otherwise false. */
  public void replace( final Class<? extends Fragment> classInfo, final boolean useStack )
  {
    replace( classInfo, 0, null, useStack );
  }
  
  /** Replace in activity current fragment by another.
   * 
   * @param classInfo class info of new fragment.
   * @param args arguments for newly created fragment.
   * @param useStack true - add to "back Stack", otherwise false. */
  public void replace( Class<? extends Fragment> classInfo, int fragmentPlace, Bundle args, boolean useStack )
  {
    if( null != classInfo )
    {
      final Fragment frgCurrent = getCurrentFragment( fragmentPlace );
      final String classCurr = ( null == frgCurrent ) ? null : frgCurrent.getClass().getName();
      final String className = classInfo.getName();
      
      if( !className.equals( mCurrentFragmentClassName ) || !className.equals( classCurr ) )
      {
        final Fragment fragment = Fragment.instantiate( this, classInfo.getName() );
        
        replace( fragment, args, useStack, fragmentPlace );
      }
    }
  }
  
  /** Replace in activity current fragment by another.
   * 
   * @param fragment the object that extends the class Fragment */
  public void replace( final Fragment fragment,int fragmentPlace )
  {
    replace( fragment, null ,fragmentPlace);
    
  }
  /** Replace in activity current fragment by another.
   * 
   * @param fragment the object that extends the class Fragment.
   * @param args arguments for fragment. */
  public void replace( final Fragment fragment, final Bundle args, int fragmentPlase )
  {
    replace( fragment, args, false, fragmentPlase );
  }
  /** Replace in activity current fragment by another.
   * 
   * @param fragment the object that extends the class Fragment.
   * @param args arguments for fragment.
   * @param useStack true - add to "back Stack", otherwise false. */
  public void replace( final Fragment fragment, final Bundle args, final boolean useStack, int fragmentPlase )
  {
    if( null != fragment && !isFinishing() )
    {
      final Fragment frgCurrent = getCurrentFragment( fragmentPlase );
      final String classCurr = ( null == frgCurrent ) ? null : frgCurrent.getClass().getName();
      final String className = fragment.getClass().getCanonicalName();
      
      if( !className.equals( mCurrentFragmentClassName ) || !className.equals( classCurr ) )
      {
        // replace fragment now
        final FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        
        // update activity set fragment
        mCurrentFragmentClassName = className;
        
        // specify replace parameters for fragment
        if( null != args )
          fragment.setArguments( args );
        
        ft.replace( fragmentPlase, fragment, mCurrentFragmentClassName );
        
        if( useStack )
          ft.addToBackStack( className );
        
        ft.commit();
        
      }
    }
  }
}
