package com.softwest.friendstogether.web.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.softwest.friendstogether.LetIsGoTogetherAPP;
import com.softwest.friendstogether.web.WebApi;
import com.softwest.friendstogether.web.requests.Params;
import com.softwest.friendstogether.web.responses.Primary;

public class JsonLoader
  extends AsyncTaskLoader<Primary>
{
  // #region Constants
  /** Tag for logging. */
  private static final String TAG = LetIsGoTogetherAPP.TAG;
  /** Should JSON loader by default use cookies in calls or not. */
   // #region Members
  /** web method name */
  private final String mMethod;
  /** response handler */
  private final JsonHandler mHandler;
  /** list of parameters used for request */
  private final List<NameValuePair> mValues = new ArrayList<NameValuePair>();
  /** request content */
  private String mContent;
  /** extracted response */
  private String mResponse;
  /** True - use cookies, otherwise False. */
  
  // #endregion
  
  // #region Constructor
  /** Main constructor.
   * 
   * @param context application context
   * @param method Web method name
   * @param handler response handler. */
  public JsonLoader( final Context context, final String method, final JsonHandler handler )
  {
    super( context );
    
    mHandler = handler;
    mMethod = method;
  }
  
 public void addParameters(final String name,String value)
 {
   mValues.add( new BasicNameValuePair( name, value ) );
 }
 
 public void setBodyJson(final Params param)
 {
   mContent = Params.toJson( param );
 }
  // #region Worker
  /** {@inheritDoc} */
  @Override
  public Primary loadInBackground()
  {
    Primary result = null;
 
      final long startTime = System.nanoTime();
      try
      {
      String url = WebApi.makeUrl(mMethod,mValues);
    
      Log.i( TAG, "-------------------------------------------" );
      Log.i( TAG, "Request Url: " + url );
      Log.i( TAG, "Request Body: " + mContent );
      
      String response = mResponse = WebApi.doRequest( url, mContent );
      
    if(null != mHandler)
     
        result = mHandler.process( this, response );
    
       Log.i( TAG, "Mapping Result: " + result.toString() );
      }
      catch( IOException e )
      {
        // TODO Auto-generated catch block
       Log.w( TAG, e.toString() );
      }
      
    return result;
  }
  
  // #endregion
}
