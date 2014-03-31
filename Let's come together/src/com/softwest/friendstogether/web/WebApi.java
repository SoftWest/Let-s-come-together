package com.softwest.friendstogether.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.softwest.friendstogether.LetIsGoTogetherAPP;

/** Web API integration helper class. */
public class WebApi
{
  // #region Constants
  /** Tag for logging. */
  private static final String TAG = LetIsGoTogetherAPP.TAG;
  
  /** UTF-8 encoding name. */
  public final static String UTF8 = "UTF-8";
  private static final String CONTENT_TYPE_JSON = "application/json";
  /** Connection establishing timeout. Default: 7.5 seconds. */
  public static final int Timeout = 7 * 1000 + 500;
  /** Read timeout. Default: 5 seconds. */
  public static final int ReadTimeout = 5 * 1000;
  /** WEB Host name*/
  private static final String WEB_HOST = "host name";
  
  /** Compose Web API call URL with digital signature of parameters.
   * 
   * @param method method name
   * @param params list of parameters
   * @return composed URL as string. */
  public static String makeUrl( String method, List<NameValuePair> params )
  {
    final int size = ( null == params ) ? 0 : params.size();
    String separate = "";
    
    StringBuilder urlBuilder = new StringBuilder( 2048 );
    
    urlBuilder.append( WEB_HOST ).append( method );
    
    if( size > 0 )
    {
      separate = "?";
      for( int i = 0; i < size; i++ )
      {
        NameValuePair pair = params.get( i );
        
        urlBuilder.append( separate );
        urlBuilder.append( Uri.encode( pair.getName() ) );
        urlBuilder.append( '=' );
        urlBuilder.append( Uri.encode( pair.getValue() ) );
        
        separate = "&"; // after first parameter all other should start from &amp;
      }
    }
    
    return urlBuilder.toString();
  }
  
  /** Do Web API call by specified URL, content and content type.
   * 
   * @param url URL for call
   * @param content POST content
   * @param contentTypeJson
   * @return response as a string
   * @throws MalformedURLException
   * @throws IOException
   * @throws NoSuchAlgorithmException
   * @throws KeyManagementException
   * @throws ProtocolException */
  public static String doRequest( final String url, final String content ) throws MalformedURLException, IOException,
      NoSuchAlgorithmException, KeyManagementException
  {
    return doRequest( url, content, CONTENT_TYPE_JSON );
  }
  /** Do Web API call by specified URL, content and content type.
   * 
   * @param url URL for call
   * @param content POST content
   * @param contentType redefine body content type
   * @return response as a string
   * @throws MalformedURLException
   * @throws IOException
   * @throws NoSuchAlgorithmException
   * @throws KeyManagementException */
  public static String doRequest( String url, String content, String contentType ) throws MalformedURLException,
      IOException, NoSuchAlgorithmException, KeyManagementException
  {
    final HttpURLConnection connection = ( HttpURLConnection )new URL( url ).openConnection();
    
    // configure timeout options
    connection.setConnectTimeout( Timeout );
    connection.setReadTimeout( ReadTimeout );
    
    connection.setUseCaches( true );
    
    connection.setRequestMethod( "GET" );
    connection.setDoInput( true );
    
    if( !TextUtils.isEmpty( content ) )
      doPostRequest( connection, contentType, content );
    
    Log.i( TAG, "Request Method: " + connection.getRequestMethod() );
    connection.connect();
    
    Log.i( TAG, "Response Code: " + connection.getResponseCode() );
    
    final InputStream input = connection.getInputStream();
    
    final String result = readUTF8( input );
    
    return result;
  }
  
  private static void doPostRequest( HttpURLConnection connection, String contentType, String content )
      throws java.net.ProtocolException, IOException, UnsupportedEncodingException
  {
    Log.i( TAG, "Request Content-Type: " + contentType );
    
    connection.setDoOutput( true );
    connection.setRequestMethod( "POST" );
    connection.addRequestProperty( "Content-Type", contentType );
    
    // place call parameters into body
    final OutputStream output = connection.getOutputStream();
    output.write( content.getBytes( UTF8 ) );
  }
  
  private static String readUTF8( InputStream stream ) throws IOException
  {
    final InputStreamReader reader = new InputStreamReader( stream, UTF8 );
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
  
  /** List of all known to WEB API methods. */
  public interface Methods
  {
    /** */
    public final static String Example = "/example.php";
    
  }
  
  /** List of known query parameters for web methods. */
  public interface Query
  {
    public final static String EXAMPLE = "example";
  }
  // #endregion
}