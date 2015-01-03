package com.google.zxing;

public final class ChecksumException extends ReaderException
{
  private static final ChecksumException instance = new ChecksumException();

  public static ChecksumException getChecksumInstance()
  {
    return instance;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.ChecksumException
 * JD-Core Version:    0.6.0
 */