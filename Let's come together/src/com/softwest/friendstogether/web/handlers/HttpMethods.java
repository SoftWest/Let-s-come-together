package com.softwest.friendstogether.web.handlers;

import android.content.Context;

import com.softwest.friendstogether.web.WebApi;

public final class HttpMethods
{
  public static HttpRequest sendFacebookToken( final Context context, String facebookToken ,IResponse listener)
  {
    HttpRequest request = new HttpRequest( context, WebApi.Methods.Facebook_Token );
   
    request.addParameter( WebApi.Query.TOKEN, facebookToken );
    request.postRequst(listener);
   
    return request;
  }
}
