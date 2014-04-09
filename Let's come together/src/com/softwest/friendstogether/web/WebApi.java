package com.softwest.friendstogether.web;

import com.softwest.friendstogether.LetIsGoTogetherAPP;



/** Web API integration helper class. */
public class WebApi
{
  // #region Constants
  /** Tag for logging. */
  private static final String TAG = LetIsGoTogetherAPP.TAG;
  /** UTF-8 encoding name. */
  public final static String UTF8 = "UTF-8";
  /** HTTP response cache size. Default: 10Mb. */
  public static final long Http_Cache_Size = 10 * 1024 * 1024;
  /** Connection establishing timeout. Default: 7.5 seconds. */
  public static final int Timeout = 7 * 1000 + 500;
  /** Read timeout. Default: 5 seconds. */
  public static final int ReadTimeout = 5 * 1000;
 
  public final static String WEB_HOST = "http://come2gether.softwest.net";
  public final static String REQUEST = "/req";
  /** Default content type for calls. */
  public final static String CONTENT_TYPE_JSON = "application/json";
  // #endregion
  
  // #region Nested declarations
  /** List of all known to WEB API methods. */
  public interface Methods
  {
    /** */
    public final static String Facebook_Token = "/authappfb";
  }
  
  /** List of known query parameters for web methods. */
  public interface Query
  {
    public final static String TOKEN = "user_fb_token";
  }
  // #endregion
 
}