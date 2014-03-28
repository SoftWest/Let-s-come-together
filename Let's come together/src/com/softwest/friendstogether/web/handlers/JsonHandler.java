package com.softwest.friendstogether.web.handlers;

import java.io.IOException;

import com.softwest.friendstogether.web.responces.Primary;

public interface JsonHandler
{

  /**
   * @param caller instance of caller
   * @param json JSON response to parse
   * @return desirialized instance of the business object
   * @throws JsonProcessingException
   */
  public Primary procces(final JsonLoader caller,final String json)throws IOException;
}
