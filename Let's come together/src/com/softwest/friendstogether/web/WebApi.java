package com.softwest.friendstogether.web;

import java.util.List;

import org.apache.http.NameValuePair;

import com.softwest.friendstogether.LetIsGoTogetherAPP;

/** Web API integration helper class. */
public class WebApi
{
  // #region Constants
  /** Tag for logging. */
  private static final String TAG = LetIsGoTogetherAPP.TAG;
  
  /** List of all known to WEB API methods. */
  public interface Methods
  {
    /** */
    public final static String Example = "/example.php";
    
 
  }
  
  /** List of known query parameters for web methods. */
  public interface Query
  {
      
  }

  public static String makeUrl( String mMethod, List<NameValuePair> mValues )
  {
  
    return null;
  }

  public static String doRequest( String url, String mContent )
  {
   
    return null;
  }

  // #endregion 
}