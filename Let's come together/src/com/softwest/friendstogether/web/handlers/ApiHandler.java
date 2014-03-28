package com.softwest.friendstogether.web.handlers;

import com.softwest.friendstogether.web.responces.CurrentUser;

public final class ApiHandler
{
  public final static JsonHandler EXAMPLE = new  ObjectHandler("letscometogether", CurrentUser.class);
}
