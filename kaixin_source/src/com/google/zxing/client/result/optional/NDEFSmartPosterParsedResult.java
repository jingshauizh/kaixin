package com.google.zxing.client.result.optional;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;

public final class NDEFSmartPosterParsedResult extends ParsedResult
{
  public static final int ACTION_DO = 0;
  public static final int ACTION_OPEN = 2;
  public static final int ACTION_SAVE = 1;
  public static final int ACTION_UNSPECIFIED = -1;
  private final int action;
  private final String title;
  private final String uri;

  NDEFSmartPosterParsedResult(int paramInt, String paramString1, String paramString2)
  {
    super(ParsedResultType.NDEF_SMART_POSTER);
    this.action = paramInt;
    this.uri = paramString1;
    this.title = paramString2;
  }

  public int getAction()
  {
    return this.action;
  }

  public String getDisplayResult()
  {
    if (this.title == null)
      return this.uri;
    return this.title + '\n' + this.uri;
  }

  public String getTitle()
  {
    return this.title;
  }

  public String getURI()
  {
    return this.uri;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.optional.NDEFSmartPosterParsedResult
 * JD-Core Version:    0.6.0
 */