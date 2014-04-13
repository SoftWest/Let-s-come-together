package com.softwest.friendstogether.web.responses;

import com.softwest.friendstogether.web.responses.wrappers.ResponseBody;


public class PeopleNearMe
  extends ResponseBody
{
  public int place_id;
  public int place_user_id;
  public int place_poi_id;
  
  public String place_desc;
  
  public double place_latitude;
  public double place_longtitude;
  
  public Object place_datetime;
  
  public String place_status;
  public String user_nickname;
  
  public String user_sex;
  
}
