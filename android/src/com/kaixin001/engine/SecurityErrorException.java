package com.kaixin001.engine;

public class SecurityErrorException extends Exception
{
  private static final long serialVersionUID = -2970941968669254137L;
  public String errMessage;
  public int errorNumber;
  public int ret;
  public String uid;
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.SecurityErrorException
 * JD-Core Version:    0.6.0
 */