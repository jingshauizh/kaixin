package com.google.zxing;

public final class NotFoundException extends ReaderException
{
  private static final NotFoundException instance = new NotFoundException();

  public static NotFoundException getNotFoundInstance()
  {
    return instance;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.NotFoundException
 * JD-Core Version:    0.6.0
 */