package com.softwest.friendstogether.web.handlers;

import android.content.Context;

import com.softwest.friendstogether.web.WebApi;
import com.softwest.friendstogether.web.responses.Primary;

public final class HttpMethods
{
  
  public static HttpRequest sendFacebookToken( final Context context, String facebookToken, IResponse listener,
      Class<? extends Primary> classInfo )
  {
    HttpRequest request = new HttpRequest( context, WebApi.Methods.Facebook_Token );
    
    request.addParameter( WebApi.Query.TOKEN, facebookToken );
    request.postRequst( listener, classInfo );
    
    return request;
  }
  
  public static HttpRequest peopleNearMe( final Context context, double latitude, double longitude, String serverToken,
      IResponse listener, Class<? extends Primary> classInfo )
  {
    HttpRequest request = new HttpRequest( context, WebApi.Methods.People_Near_Me );
    
    request.addParameter( WebApi.Query.PLACE_LATITUDE, String.valueOf( latitude ) );
    request.addParameter( WebApi.Query.PLACE_LONGTITUDE, String.valueOf( longitude ) );
    request.addParameter( WebApi.Query.SERVER_TOKEN, serverToken );
    
    request.postRequst( listener, classInfo );
    
    return request;
  }
  
  public static HttpRequest getPlaceNiarMe( final Context context, double latitude, double longitude, float zoom,
      String serverToken, IResponse listener, Class<? extends Primary> classInfo )
  {
    HttpRequest request = new HttpRequest( context, WebApi.Methods.Get_Poi );
    
    request.addParameter( WebApi.Query.PLACE_LATITUDE, String.valueOf( latitude ) );
    request.addParameter( WebApi.Query.PLACE_LONGTITUDE, String.valueOf( longitude ) );
    request.addParameter( WebApi.Query.MAP_ZOOM, String.valueOf( zoom ) );
    request.addParameter( WebApi.Query.SERVER_TOKEN, serverToken );
    
    request.postRequst( listener, classInfo );
    
    return request;
  }
}
