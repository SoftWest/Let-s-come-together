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
    
    request.addParameter( WebApi.Query.PLACE_LATITUDE_PLACE, /*String.valueOf( latitude )*/"50.4716403" );
    request.addParameter( WebApi.Query.PLACE_LONGTITUDE_PLACE, /*String.valueOf( longitude )*/"30.5181315" );
    request.addParameter( WebApi.Query.SERVER_TOKEN, serverToken );
    
    request.postRequst( listener, classInfo );
    
    return request;
  }
  
  public static HttpRequest getPlaceNiarMe( final Context context, double latitude, double longitude, float zoom,
      String serverToken, IResponse listener, Class<? extends Primary> classInfo )
  {
    HttpRequest request = new HttpRequest( context, WebApi.Methods.Get_Poi );
    
    request.addParameter( WebApi.Query.PLACE_LATITUDE_POI, String.valueOf( latitude ) );
    request.addParameter( WebApi.Query.PLACE_LONGTITUDE_POI, String.valueOf( longitude ) );
    request.addParameter( WebApi.Query.MAP_ZOOM, String.valueOf( zoom ) );
    request.addParameter( WebApi.Query.SERVER_TOKEN, serverToken );
    
    request.postRequst( listener, classInfo );
    
    return request;
  }
  
  public static HttpRequest checkIn( final Context context, int checkIn,
      String serverToken, IResponse listener, Class<? extends Primary> classInfo )
  {
    HttpRequest request = new HttpRequest( context, WebApi.Methods.Get_Poi );
    
    request.addParameter( WebApi.Query.PLACE_LATITUDE_POI, String.valueOf( checkIn ) );
    request.addParameter( WebApi.Query.SERVER_TOKEN, serverToken );
    
    request.postRequst( listener, classInfo );
    
    return request;
  }
}
