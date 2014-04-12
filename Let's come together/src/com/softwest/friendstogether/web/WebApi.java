package com.softwest.friendstogether.web;

import com.softwest.friendstogether.LetIsGoTogetherAPP;

 
/** Web API integration helper class. */
/**
 * @author Taras Matolinets
 *
 */
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
  /** Default content type for calls. */
  public final static String WEB_HOST = "http://come2gether.softwest.net";
  /** Default content type for calls. */
  public final static String REQUEST = "/req"; 
  public final static String CONTENT_TYPE_JSON = "application/json";
  /** server token*/
  public static String ServerToken;
  // #endregion
  
  /**
   * @return server token
   */
  public static String getServerToken()
  {
    return ServerToken;
  }

  // #region Nested declarations
  /** List of all known to WEB API methods. */
  
  /**
   * @param set server token
   */
  public static void setServerToken(String token)
  {
    ServerToken = token;
  }
  public interface Methods
  {
    /** */
    public final static String Facebook_Token = "/authappfb";
    /** */
    public final static String People_Near_Me = "/getpeopleplaces";
    /** */
    public final static String Get_Poi = "/getpoi";
   
  }
  
  /** List of known query parameters for web methods. */
  public interface Query
  {
    public final static String TOKEN = "user_fb_token";
    
    public final static String PLACE_LATITUDE = "place_latitude";
  
    public final static String PLACE_LONGTITUDE = "place_longtitude";
    
    public final static String SERVER_TOKEN = "sever_token";
    
    public final static String MAP_ZOOM = "map_zoom";
  }
  // #endregion
 
}