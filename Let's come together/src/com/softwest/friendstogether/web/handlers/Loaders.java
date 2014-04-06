package com.softwest.friendstogether.web.handlers;

import android.content.Context;
import android.support.v4.content.Loader;

import com.softwest.friendstogether.web.WebApi;
import com.softwest.friendstogether.web.responses.Primary;

public final class Loaders
{
  // #region Static members
  private static String sToken;
  
  // #region Common loader call member
  public static void assignToken( String token )
  {
    sToken = token;
  }
  // #region Known loaders
  
//  public static Loader<Primary> example( final Context context, String applicationName )
//  {
//    JsonLoader loader = new JsonLoader( context, WebApi.Methods.Example, ApiHandlers.REGISTER_TOKEN );
////    loader.addParameters( WebApi.Query.EXAMPLE, applicationName );
////   
//    return loader;
//  }
  
  
  public static Loader<Primary> sendFacebookToken( final Context context, String facebookToken )
  {
    JsonLoader loader = new JsonLoader( context, WebApi.Methods.TOKEN_FACEOOK, ApiHandlers.REGISTER_TOKEN );
    loader.addParameters( WebApi.Query.TOKEN_ACCESS, facebookToken );
   
    return loader;
  }
}
