package com.softwest.friendstogether.web.responses;

import java.util.ArrayList;
import java.util.List;

import com.softwest.friendstogether.web.responses.list.Education;
import com.softwest.friendstogether.web.responses.list.FavoriteTerms;
import com.softwest.friendstogether.web.responses.list.Work;
import com.softwest.friendstogether.web.responses.wrappers.HomeTown;
import com.softwest.friendstogether.web.responses.wrappers.Location;

public class CurrentUser
  extends Primary
{
  public long id;
 
  public String name;
  public String first_name;
  public String last_name;
  
  public String link;
  
  public String facebookToken;
  
  public HomeTown hometown;
  
  public Location location;
  
  public List<Work> work = new ArrayList<Work>();

  public List<FavoriteTerms> favorite_terms = new ArrayList<FavoriteTerms>();

  public List<Education> education = new ArrayList<Education>();
  
  public String gender;
  
  public String email;
  
  public int timezone;

  public String locale;
  
  public boolean verified;
  
  public Object updated_time;
  
  public String username;
  
  public String birthday;
}

