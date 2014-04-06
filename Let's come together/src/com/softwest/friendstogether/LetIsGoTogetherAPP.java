package com.softwest.friendstogether;

import android.app.Application;

import com.softwest.friendstogether.web.responses.CurrentUser;


public class LetIsGoTogetherAPP
  extends Application
{
  /** Logger tag. */
  public static final String TAG = "let_is_come_together";

  private CurrentUser mCurrentUser;
  
  
  /**
   * @param set current user
   */
  public void setCurrentUser( CurrentUser currentUser )
  {
    this.mCurrentUser = currentUser;
  }


  /**
   * @return current user
   */
  public CurrentUser getCurrentUser()
  {
    return mCurrentUser;
  }
  
}
