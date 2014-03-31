package com.softwest.friendstogether.web.requests;

import android.util.Log;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.softwest.friendstogether.LetIsGoTogetherAPP;
import com.softwest.friendstogether.web.responses.Primary;

/** Base class for parameters send to web service side during method calls. */
@JsonIgnoreProperties( ignoreUnknown = true )
@JsonInclude( Include.NON_DEFAULT )
public abstract class Parameters
{
  /** Serialize any Parameters instance to JSON.
   * 
   * @param value instance to serialize.
   * @return JSON string. */
  public static String toJson(final Parameters value)
  {
    final ObjectMapper mapper = Primary.cachedMapper();
    
    String json = "{}";
    
    try
    {
      json = mapper.writeValueAsString( value );
    }
    catch( JsonProcessingException ignored )
    {
      Log.d( LetIsGoTogetherAPP.TAG, "class: " + value.getClass().getSimpleName() + " exception: " + ignored.getMessage() );
    }
    
    Log.v( LetIsGoTogetherAPP.TAG, "class: " + value.getClass().getSimpleName() + " json: " + json );
    
    return json;
  }
}
