package com.softwest.friendstogether.web.responses.wrappers;

import com.softwest.friendstogether.web.responses.CheckIn;
import com.softwest.friendstogether.web.responses.Primary;

public abstract class ResponseBody
extends Primary
{
  public int status;
  
  public CheckIn result;
  
  public String error;
  
  
}
