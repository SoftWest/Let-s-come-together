package com.softwest.friendstogether.web.handlers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.softwest.friendstogether.web.responses.Primary;

/** JSON response handler - converts JSON to business objects. */
public interface JsonHandler
{
  /** Convert JSON to business object instance.
   * 
   * @param caller instance of caller
   * @param json JSON response to parse.
   * @return deserialized instance of the business object.
   * @throws JsonProcessingException */
  public Primary process( final JsonLoader caller, final String json ) throws IOException;
}
