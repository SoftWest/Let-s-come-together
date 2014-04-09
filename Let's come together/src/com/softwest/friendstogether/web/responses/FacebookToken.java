package com.softwest.friendstogether.web.responses;

import com.softwest.friendstogether.web.responses.wrappers.ResponseBody;

public class FacebookToken
  extends ResponseBody
{
  public ComeTogetherEror error;
  public String info;
  public String data;
}
