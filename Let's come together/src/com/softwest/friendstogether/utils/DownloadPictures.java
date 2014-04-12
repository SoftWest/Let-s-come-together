package com.softwest.friendstogether.utils;

import java.io.InputStream;
import java.net.URL;

import com.softwest.friendstogether.LetIsGoTogetherAPP;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadPictures
  extends AsyncTask<String, Object, Bitmap>
{
  
  @Override
  protected Bitmap doInBackground( String ... params )
  {
    String imageURL;
    Bitmap bitmap = null;
    Log.d(LetIsGoTogetherAPP.TAG, "Loading Picture");
   imageURL = "http://graph.facebook.com/"+"userId"+"/picture?type=small";
    try {
        bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageURL).getContent());
    } catch (Exception e) {
        Log.d("TAG", "Loading Picture FAILED");
        e.printStackTrace();
    }
    return null;
  }
  
}
