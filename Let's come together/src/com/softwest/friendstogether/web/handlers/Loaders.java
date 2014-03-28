package com.softwest.friendstogether.web.handlers;

import com.softwest.friendstogether.web.WebApi;
import com.softwest.friendstogether.web.WebApi.Methods;
import com.softwest.friendstogether.web.responces.Primary;

import android.content.Context;
import android.content.Loader;

public final class Loaders
{
  //Static member
 private static String mToken;
 
 public static void signToken(String token)
 {
   mToken = token;
 }
  
 public static Loader<Primary> someExample(final Context context,int number)
 {
   JsonLoader loader = new JsonLoader( context , WebApi.Methods.Example,ApiHandler.EXAMPLE);
   
  return null;
 }
}
