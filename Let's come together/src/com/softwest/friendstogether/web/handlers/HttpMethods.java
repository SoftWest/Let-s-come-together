package com.softwest.friendstogether.web.handlers;

import android.content.Context;

import com.softwest.friendstogether.web.WebApi;
import com.softwest.friendstogether.web.responses.Primary;

public final class HttpMethods
{
  public static HttpRequest sendFacebookToken( final Context context, String facebookToken ,IResponse listener,Class<? extends Primary> classInfo)
  {
    HttpRequest request = new HttpRequest( context, WebApi.Methods.Facebook_Token );
   
    request.addParameter( WebApi.Query.TOKEN, facebookToken );
    request.postRequst(listener, classInfo);
   
    return request;
  }
}
