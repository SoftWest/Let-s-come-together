package com.softwest.friendstogether.web.handlers;

import android.content.Context;

import com.softwest.friendstogether.utils.JsonObjectType;
import com.softwest.friendstogether.web.WebApi;

public final class HttpMethods
{
  
  public static HttpRequest sendFacebookToken( final Context context, String facebookToken, IResponse listener,
      JsonObjectType jsonObject )
  {
    HttpRequest request = new HttpRequest( context, WebApi.Methods.Facebook_Token );
    
    request.addParameter( WebApi.Query.TOKEN, facebookToken );
    request.postRequst( listener, jsonObject );
    
    return request;
  }
  
  public static HttpRequest peopleNearMe( final Context context, double latitude, double longitude, String serverToken,
      IResponse listener, JsonObjectType jsonObject)
  {
    HttpRequest request = new HttpRequest( context, WebApi.Methods.People_Near_Me );
    
    request.addParameter( WebApi.Query.PLACE_LATITUDE_PLACE, /* String.valueOf( latitude ) */"50.7517092" );
    request.addParameter( WebApi.Query.PLACE_LONGTITUDE_PLACE, /* String.valueOf( longitude ) */"25.3301825" );
    request.addParameter( WebApi.Query.SERVER_TOKEN, serverToken );
    
    request.postRequst( listener, jsonObject );
    
    return request;
  }
  
  public static HttpRequest getPlaceNiarMe( final Context context, double latitude, double longitude, int zoom,
      String serverToken, IResponse listener, JsonObjectType jsonObject  )
  {
    HttpRequest request = new HttpRequest( context, WebApi.Methods.Get_Poi );
    
    request.addParameter( WebApi.Query.PLACE_LATITUDE_POI, String.valueOf( 50.4716403 ) );
    request.addParameter( WebApi.Query.PLACE_LONGTITUDE_POI, String.valueOf( 30.5181315 ) );
    request.addParameter( WebApi.Query.ZOOM, String.valueOf( zoom ) );
    request.addParameter( WebApi.Query.SERVER_TOKEN, serverToken );
    
    request.postRequst( listener, jsonObject );
    
    return request;
  }
  
  public static HttpRequest checkIn( final Context context, int checkIn, String serverToken, IResponse listener,
      JsonObjectType jsonObject )
  {
    HttpRequest request = new HttpRequest( context, WebApi.Methods.Check_In );
    
    request.addParameter( WebApi.Query.PLACE_LATITUDE_POI, String.valueOf( checkIn ) );
    request.addParameter( WebApi.Query.SERVER_TOKEN, serverToken );
    
    request.postRequst( listener, jsonObject );
    
    return request;
  }
  
  public static HttpRequest saveSearchSettings( final Context context, int param, String language, IResponse listener,
      JsonObjectType jsonObject )
  {
    HttpRequest request = new HttpRequest( context, WebApi.Methods.Save_Search_Setting );
    
    request.addParameter( WebApi.Query.GET_PARAM, String.valueOf( param ) );
    request.addParameter( WebApi.Query.LANG, language );
    
    request.postRequst( listener, jsonObject );
    
    return request;
  }
}
