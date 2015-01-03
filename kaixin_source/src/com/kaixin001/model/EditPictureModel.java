package com.kaixin001.model;

import android.graphics.Bitmap;

public class EditPictureModel
{
  private static Bitmap bimap;
  private static Bitmap bimapCanvas;
  private static Bitmap[] frameChips;
  private static String picFrom;
  private static String picSDPath = "";

  static
  {
    picFrom = "";
    bimapCanvas = null;
    bimap = null;
    frameChips = null;
  }

  public static void clear()
  {
    picSDPath = "";
    bimap = null;
    frameChips = null;
  }

  public static Bitmap getBimap()
  {
    return bimap;
  }

  public static Bitmap getBimapCanvas()
  {
    return bimapCanvas;
  }

  public static String getBitmapPath()
  {
    return picSDPath;
  }

  public static Bitmap[] getFrameClip()
  {
    return frameChips;
  }

  public static String getPicFrom()
  {
    return picFrom;
  }

  public static void setBimap(Bitmap paramBitmap)
  {
    bimap = paramBitmap;
  }

  public static void setBimapCanvas(Bitmap paramBitmap)
  {
    bimapCanvas = paramBitmap;
  }

  public static void setBitmapPath(String paramString)
  {
    picSDPath = paramString;
  }

  public static void setFrameClip(Bitmap[] paramArrayOfBitmap)
  {
    frameChips = paramArrayOfBitmap;
  }

  public static void setPicFrom(String paramString)
  {
    picFrom = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.EditPictureModel
 * JD-Core Version:    0.6.0
 */