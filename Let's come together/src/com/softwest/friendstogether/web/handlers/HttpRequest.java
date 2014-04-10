package com.softwest.friendstogether.web.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonNode;
import com.softwest.friendstogether.LetIsGoTogetherAPP;
import com.softwest.friendstogether.web.WebApi;
import com.softwest.friendstogether.web.requests.Parameters;
import com.softwest.friendstogether.web.responses.ComeTogetherEror;
import com.softwest.friendstogether.web.responses.Primary;

public class HttpRequest

{
  // #region Constants
  /** Tag for logging. */
  private static final String TAG = LetIsGoTogetherAPP.TAG;
  /** delay after web response. */
  public static final long NO_DELAY = -1;
  // #endregion
  
  // #region Members
  /** web method name */
  private final String mMethod;
  
  /** list of parameters used for request */
  private final List<NameValuePair> mValues = new ArrayList<NameValuePair>();
  /** request content */
  private String mContent;
  /** extracted response */
  private String mResponse;
  private Context mContext;
  /** Delay before sending response to handler. */
  private long mDelay = NO_DELAY;
  
  private IResponse mHandlerResponse;
  
  // #endregion
  
  // #region Constructor
  /** Main constructor.
   * 
   * @param context application context
   * @param method Web method name
   * @param handler response handler. */
  public HttpRequest( final Context context, final String method )
  {
    mContext = context;
    mMethod = method;
  }
  
  // #endregion
  
  // #region Public methods
  /** Add parameter to the request queue.
   * 
   * @param name parameter name.
   * @param value parameter value. */
  public void addParameter( final String name, final String value )
  {
    mValues.add( new BasicNameValuePair( name, value ) );
  }
  
  /** set body in RAW set approach.
   * 
   * @param body new body of the request. */
  public void setBody( final String body )
  {
    mContent = body;
  }
  
  /** compose body from Params class serialization to JSON.
   * 
   * @param param class with parameters. */
  public void setBodyJson( final Parameters param )
  {
    mContent = Parameters.toJson( param );
  }
  
  /** Returns captured response.
   * 
   * @return captured response as string. */
  public String getResponse()
  {
    return mResponse;
  }
  
  /**
   * @param listener callBack for JSON
   * @param classInfo class for parsing data from JSON
   */
  public void postRequst( IResponse listener, Class<? extends Primary> classInfo )
  {
    mHandlerResponse = listener;
    
    if( android.os.Build.VERSION.SDK_INT > 9 )
    {
      try
      {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy( policy );
        
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost( WebApi.WEB_HOST + WebApi.REQUEST + mMethod );
        
        // no idea what this does :)
        httppost.setEntity( new UrlEncodedFormEntity( mValues ) );
        
        Log.d( "uri", "uri " + httppost.getURI() );
        // This is the line that send the request
        HttpResponse response = httpclient.execute( httppost );
        
        BufferedReader reader = new BufferedReader( new InputStreamReader( response.getEntity().getContent(), "UTF-8" ) );
        String json = reader.readLine();
        
        if( null != mHandlerResponse )
          mHandlerResponse.process( json, classInfo.getName() );
      }
      catch( Throwable ignore )
      {
        if( null != mHandlerResponse )
          mHandlerResponse.process( ignore.toString(), classInfo );
        
        Log.w( LetIsGoTogetherAPP.TAG, "exeption " + ignore );
      }
      
    }
  }
  
  public ComeTogetherEror getRerror( final String root ) throws IOException
  {
    
    return null;
  }
}
