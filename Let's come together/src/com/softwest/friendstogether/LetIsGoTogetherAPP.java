package com.softwest.friendstogether;

import android.app.Application;

import com.softwest.friendstogether.web.responses.CurrentUser;

public class LetIsGoTogetherAPP
  extends Application
{
  /** Logger tag. */
  public static final String TAG = "let_is_come_together";
  
  /** info about current user */
  private CurrentUser mCurrentUser;
  /** server identificator */
  private String mServerToken;
  
  /** @param set current user */
  public void setCurrentUser( CurrentUser currentUser )
  {
    this.mCurrentUser = currentUser;
  }
  
  /**
   * @return derver identificator
   */
  public String getServerIdentity()
  {
    return mServerToken;
  }
  
  /**
   * @param serverToken set server identificator
   */
  public void setServerIdentity(String serverToken)
  {
    this.mServerToken = serverToken;
  }
  
  /** @return current user */
  public CurrentUser getCurrentUser()
  {
    return mCurrentUser;
  }
  
}
