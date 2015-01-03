package com.kaixin001.zxing.result;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public abstract class ResultHandler
{
  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
  private final ParsedResult result;

  static
  {
    DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
  }

  ResultHandler(ParsedResult paramParsedResult)
  {
    this.result = paramParsedResult;
  }

  public boolean areContentsSecure()
  {
    return false;
  }

  public CharSequence getDisplayContents()
  {
    return this.result.getDisplayResult().replace("\r", "");
  }

  public abstract int getDisplayTitle();

  public ParsedResult getResult()
  {
    return this.result;
  }

  public final ParsedResultType getType()
  {
    return this.result.getType();
  }

  public abstract void handleButtonPress(int paramInt);

  final void scanAgain(String paramString)
  {
  }

  final void scanConfirm(String paramString)
  {
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.zxing.result.ResultHandler
 * JD-Core Version:    0.6.0
 */