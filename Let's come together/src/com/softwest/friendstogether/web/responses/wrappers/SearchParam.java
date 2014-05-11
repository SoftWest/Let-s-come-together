package com.softwest.friendstogether.web.responses.wrappers;

import com.softwest.friendstogether.web.responses.wrappers.filters.UserAgeMax;
import com.softwest.friendstogether.web.responses.wrappers.filters.UserAgeMin;
import com.softwest.friendstogether.web.responses.wrappers.filters.UserAstro;
import com.softwest.friendstogether.web.responses.wrappers.filters.UserLook;
import com.softwest.friendstogether.web.responses.wrappers.filters.UserSponsor;

public class SearchParam
{
  public UserLook user_search_look;
  public UserAgeMin user_search_age_min;
  public UserAgeMax user_search_age_max;
  public UserAstro user_search_astro;
  public UserSponsor user_search_sponsor;
  
}
