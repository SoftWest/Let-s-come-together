package com.softwest.friendstogether.web.handlers;


import com.softwest.friendstogether.web.responses.CurrentUser;

public final class ApiHandlers
{
  public final static JsonHandler REGISTRATION_APP = new ObjectHandler( "registapp", CurrentUser.class );
  
 
}