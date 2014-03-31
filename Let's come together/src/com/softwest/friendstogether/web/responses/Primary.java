package com.softwest.friendstogether.web.responses;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwest.friendstogether.LetIsGoTogetherAPP;

  /** Empty class that used as a base for all response objects returned by Shahid web service. */
  @JsonIgnoreProperties( ignoreUnknown = true )
  @JsonInclude( Include.NON_DEFAULT )
  public abstract class Primary
  {
    // #region Static members
    /** Empty instance. */
    public static final Primary Empty = new Primary()
    {
      // no implementation, just keep it empty
    };
    
    private static ObjectMapper sMapper;
    
    // #endregion
    
    // #region Helper methods
    /** Convert class to it JSON serialized version.
     * 
     * @param value instance to process.
     * @return serialized instance to JSON. */
    public static String toJson( final Primary value )
    {
      final ObjectMapper mapper = cachedMapper();
      String json = "{}";
      
      try
      {
        json = mapper.writeValueAsString( value );
      }
      catch( JsonProcessingException ignored )
      {
        Log.d( LetIsGoTogetherAPP.TAG, "class: " + value.getClass().getSimpleName() + " " +  ignored );
      }
      
      Log.v( LetIsGoTogetherAPP.TAG, "class: " + value.getClass().getSimpleName() + " json: " + json );
      
      return json;
    }
    
    /** Recover instance from JSON.
     * 
     * @param json JSON string.
     * @param classinfo type information of class that we would like to recover.
     * @return instance of the deserialized class. */
    @SuppressWarnings( "unchecked" )
    public static <T extends Primary>T fromJson( final String json, Class<?> classinfo )
    {
      final ObjectMapper mapper = cachedMapper();
      
      try
      {
        return ( T )mapper.readValue( json, classinfo );
      }
      catch( Throwable ignored )
      {
        Log.v( LetIsGoTogetherAPP.TAG, "class: " + classinfo.getSimpleName() + " json: " + json + " " +  ignored  );
      }
      
      return null;
    }
    
    public synchronized static ObjectMapper cachedMapper()
    {
      if( null == sMapper )
      {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
  
        sMapper = mapper;
      }
      
      return sMapper;
    }
    // #endregion
}
