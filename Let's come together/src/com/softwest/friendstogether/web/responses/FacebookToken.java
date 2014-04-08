package com.softwest.friendstogether.web.responses;

import com.softwest.friendstogether.web.responses.wrappers.ResponseBody;
import com.softwest.friendstogether.web.responses.wrappers.TextUserAutorizaton;

public class FacebookToken
extends ResponseBody
{
 public int status;
  
 public TextUserAutorizaton result;
  
 public String data;
}
