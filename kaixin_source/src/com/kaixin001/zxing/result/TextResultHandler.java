package com.kaixin001.zxing.result;

import com.google.zxing.client.result.ParsedResult;

public final class TextResultHandler extends ResultHandler
{
  public TextResultHandler(ParsedResult paramParsedResult)
  {
    super(paramParsedResult);
  }

  public int getDisplayTitle()
  {
    return 2131428324;
  }

  public void handleButtonPress(int paramInt)
  {
    String str = getResult().getDisplayResult();
    switch (paramInt)
    {
    default:
      return;
    case 0:
      scanAgain(str);
      return;
    case 1:
    }
    scanConfirm(str);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.zxing.result.TextResultHandler
 * JD-Core Version:    0.6.0
 */