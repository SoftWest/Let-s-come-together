package com.softwest.friendstogether.web.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.softwest.friendstogether.LetIsGoTogetherAPP;
import com.softwest.friendstogether.utils.JsonObjectType;
import com.softwest.friendstogether.web.WebApi;
import com.softwest.friendstogether.web.requests.Parameters;
import com.softwest.friendstogether.web.responses.ComeTogetherEror;

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
  
  /** @param listener callBack for JSON
   * @param T class for parsing data from JSON */
  public void postRequst( IResponse listener,JsonObjectType jsonObject )
  {
    mHandlerResponse = listener;
    InputStreamReader is = null;
    if( android.os.Build.VERSION.SDK_INT > 9 )
    {
      try
      {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy( policy );
        
        HttpClient httpclient = new DefaultHttpClient();
        final HttpPost httppost = new HttpPost( WebApi.WEB_HOST + WebApi.REQUEST + mMethod );
        // no idea what this does :)
        httppost.setEntity( new UrlEncodedFormEntity( mValues ) );
        
        Log.d( "uri", "uri " + httppost.getURI() );
        
        timeOut(2000);
        // This is the line that send the request
      
        final HttpResponse response = httpclient.execute( httppost );
       
        is = new InputStreamReader( response.getEntity().getContent(), "UTF-8" );
        
      
        if(jsonObject == JsonObjectType.CHECK_IN)
        {
        String output = WebApi.readUTF8( is );
        Log.w( LetIsGoTogetherAPP.TAG, "The Begining." );
        
        for( String line : split( output, 1024 ) )
        Log.i( LetIsGoTogetherAPP.TAG, "Response: " + line );
        
        Log.w( LetIsGoTogetherAPP.TAG, "The End." );
        }
        
        BufferedReader reader = new BufferedReader( is );
        String json = reader.readLine();
        
        if( null != mHandlerResponse )
          mHandlerResponse.process( json, jsonObject );
      }
      catch( Throwable ignore )
      {
        Log.w( LetIsGoTogetherAPP.TAG, "exception " + ignore );
      }
      finally
      {
        try
        {
          is.close();
        }
        catch( IOException e )
        {
         
          Log.e( LetIsGoTogetherAPP.TAG, "exception " + e );
        }
      }
      
    }
  }
  private void timeOut(final long sleep)
  {
    new Runnable()
    {
      public void run()
      {
      try
      {
        Thread.sleep( sleep );
      }
      catch( InterruptedException e )
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      }
    };
  }
  public static List<String> split( final String text, final int sliceSize )
  {
    final List<String> textList = new ArrayList<String>();
    
    String aux;
    int left = -1, right = 0;
    int charsLeft = text.length();
    
    while( charsLeft != 0 )
    {
      left = right;
      
      if( charsLeft >= sliceSize )
      {
        right += sliceSize;
        charsLeft -= sliceSize;
      }
      else
      {
        right = text.length();
        aux = text.substring( left, right );
        charsLeft = 0;
      }
      
      aux = text.substring( left, right );
      textList.add( aux );
    }
    
    return textList;
  }

  public ComeTogetherEror getRerror( final String root ) throws IOException
  {
    
    return null;
  }
}
