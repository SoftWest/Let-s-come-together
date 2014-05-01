package com.softwest.friendstogether.utils;

public enum JsonObjectType
{
  FACEBOOK_USER,
  FACEBOOK_TOKEN,
  POI,
  PEOPLE,
  CHECK_IN;
  
  public static JsonObjectType switchJsonObjectType(JsonObjectType type)
  {
    switch( type )
    {
      case FACEBOOK_USER:
       return JsonObjectType.FACEBOOK_USER;
      case FACEBOOK_TOKEN:
        return JsonObjectType.FACEBOOK_TOKEN;
      case POI:
        return JsonObjectType.POI;
      case PEOPLE:
        return JsonObjectType.PEOPLE;
      case CHECK_IN:
        return JsonObjectType.CHECK_IN;
    }
    return type;
  }
}
