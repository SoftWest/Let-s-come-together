package com.softwest.friendstogether.web.handlers;

import org.apache.http.HttpResponse;

import com.softwest.friendstogether.web.responses.Primary;


/** JSON response handler - converts JSON to business objects. */
public interface IResponse
{
  /** Convert JSON to business object instance.
   * 
   * @param caller instance of caller
   */
  public Primary process( final String json );
}
