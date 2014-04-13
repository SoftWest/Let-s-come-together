package com.softwest.friendstogether.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

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
   
    public final static String PLACE_LATITUDE_PLACE = "place_latitude";
  
    public final static String PLACE_LONGTITUDE_PLACE = "place_longtitude";
    
    public final static String PLACE_LATITUDE_POI = "poi_latitude";
  
    public final static String PLACE_LONGTITUDE_POI = "poi_longtitude";
    
    public final static String SERVER_TOKEN = "sever_id";
  
    public final static String MAP_ZOOM = "map_zoom";
  }
  // #endregion
  /** read input stream into string.
   * 
   * @param stream input stream
   * @return extracted text */
  public static String readUTF8( InputStreamReader reader ) throws IOException
  {
   // final InputStreamReader reader = new InputStreamReader( stream, UTF8 );
    StringWriter writer = new StringWriter();
    
    int len = 0;
    char[] buffer = new char[ 2048 ];
    
    while( ( len = reader.read( buffer ) ) > 0 )
    {
      writer.write( buffer, 0, len );
    }
    
    // close reader after all
    reader.close();
    
    return writer.toString();
  }
}