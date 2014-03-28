package com.softwest.friendstogether.web.handlers;

import java.io.IOException;

import com.softwest.friendstogether.web.responces.Primary;

public class ObjectHandler
implements JsonHandler
{

  public ObjectHandler(final String path, Class<? extends Primary> classInfo )
  {
  
  }

  @Override
  public Primary procces( JsonLoader caller, String json ) throws IOException
  {
    
    return null;
  }
  
}
