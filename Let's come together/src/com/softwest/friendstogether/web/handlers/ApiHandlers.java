package com.softwest.friendstogether.web.handlers;


import com.softwest.friendstogether.web.responses.CurrentUser;

public final class ApiHandlers
{
  public final static JsonHandler REGISTER_TOKEN = new ObjectHandler( "token_facebook", CurrentUser.class );
  
 
}
