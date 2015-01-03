package com.tencent.record.debug;

public abstract interface TraceLevel
{
  public static final int ABOVE_DEBUG = 60;
  public static final int ABOVE_INFO = 56;
  public static final int ABOVE_VERBOSE = 62;
  public static final int ABOVE_WARN = 48;
  public static final int ALL = 63;
  public static final int ASSERT = 32;
  public static final int DEBUG = 2;
  public static final int DEBUG_AND_ABOVE = 62;
  public static final int ERROR = 16;
  public static final int INFO = 4;
  public static final int INFO_AND_ABOVE = 62;
  public static final int VERBOSE = 1;
  public static final int WARN = 8;
  public static final int WARN_AND_ABOVE = 56;
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.record.debug.TraceLevel
 * JD-Core Version:    0.6.0
 */