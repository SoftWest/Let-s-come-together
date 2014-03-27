package com.softwest.friendstogether.web.responces;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwest.friendstogether.LetIsGoTogetherAPP;

/** @author Taras Matolinets */

@JsonIgnoreProperties( ignoreUnknown = true )
@JsonInclude( Include.NON_DEFAULT )
public abstract class Primary
{
  
  private static ObjectMapper mMapper;
  
  /** Converting class to it JSON serialized version
   * @param value
   * @return serialized instance to JSON.
   */
  public static String toJson( final Primary value )
  {
    final ObjectMapper mapper = configureMapper();
    
    String json = "{}";
    
    try
    {
      json = mapper.writeValueAsString( value );
    }
    catch( JsonProcessingException exception )
    {
      Log.d( LetIsGoTogetherAPP.TAG, "class: " + value.getClass().getSimpleName() + " " + exception );
    }
    
    Log.v( LetIsGoTogetherAPP.TAG, "class" + value.getClass().getSimpleName() + " json: " + json );
    
    return json;
  }
  
  /**
   * @param json JSON String
   * @param classinfo type information of class what we recover 
   * @return instance of deserialized class 
   */
  @SuppressWarnings( "unchecked" )
  public static <T extends Primary>T fromJson(final String json, Class<?> classinfo)
  {
    final ObjectMapper mapper = configureMapper();
    
    try
    {
      return ( T )mapper.readValue( json, classinfo );
    }
    catch( Throwable exception )
    {
      Log.v( LetIsGoTogetherAPP.TAG, "class: " + classinfo.getSimpleName() + " json: " + json + " " + exception );
    }
    return null;
  }
  
  private static ObjectMapper configureMapper()
  {
    if( null == mMapper )
    {
      final ObjectMapper mapper = new ObjectMapper();
      mapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
      
      mMapper = mapper;
    }
    
    return null;
  }
}
