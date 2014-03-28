package com.softwest.friendstogether.web.handlers;

import com.softwest.friendstogether.web.responces.Primary;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class JsonLoader
  extends AsyncTaskLoader<Primary>
{

  public JsonLoader( Context context ,String method, final JsonHandler handler)
  {
    super( context );
  
  }

  @Override
  public Primary loadInBackground()
  {
  
    return null;
  }
  
}
