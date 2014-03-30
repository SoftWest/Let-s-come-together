package com.softwest.friendstogether.web.handlers;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwest.friendstogether.web.responses.Primary;

public class ObjectHandler
  implements JsonHandler
{
  // #region Constants
  /** Set this name as a initial for handler */
  public static final String NO_PATH = "--no-path-for-json-internal-data--";
  // #endregion
  
  // #region Static members
  /** JSON mapper instance. */
  private final static ObjectMapper sMapper = new ObjectMapper();
  // #endregion
  
  // #region Members
  /** Path to important data in JSON object. */
  public final String mPath;
  
  /** Type information of class that we should recover from JSON. */
  public final Class<?> mClassInfo;
  // #endregion
  
  // #region Constructors
  static
  {
    sMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
    sMapper.configure( DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false );
  }
  
  /** Initiate class with type info, root JSON object will be deserialized.
   * 
   * @param classInfo class type for deserialization. */
  public ObjectHandler( Class<? extends Primary> classInfo )
  {
    this( NO_PATH, classInfo );
  }
  
  /** Initiate class with path and type info.
   * 
   * @param path path to important JSON tag.
   * @param classInfo class type for deserialization. */
  public ObjectHandler( final String path, Class<? extends Primary> classInfo )
  {
    mPath = path;
    mClassInfo = classInfo;
  }
  
  // #endregion
  
  // #region Overrides
  /** {@inheritDoc} */
  @Override
  public Primary process( JsonLoader caller, String json ) throws IOException
  {
    JsonNode item = null;
    final JsonNode root = sMapper.readTree( json );
      
      // enable Jackson to handle the unquoted field name
      
    return ( Primary )sMapper.readValue( item.toString(), mClassInfo );
  }
  
  // #endregion
  
}
