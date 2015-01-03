package com.google.zxing.client.result;

public final class TelParsedResult extends ParsedResult
{
  private final String number;
  private final String telURI;
  private final String title;

  public TelParsedResult(String paramString1, String paramString2, String paramString3)
  {
    super(ParsedResultType.TEL);
    this.number = paramString1;
    this.telURI = paramString2;
    this.title = paramString3;
  }

  public String getDisplayResult()
  {
    StringBuffer localStringBuffer = new StringBuffer(20);
    maybeAppend(this.number, localStringBuffer);
    maybeAppend(this.title, localStringBuffer);
    return localStringBuffer.toString();
  }

  public String getNumber()
  {
    return this.number;
  }

  public String getTelURI()
  {
    return this.telURI;
  }

  public String getTitle()
  {
    return this.title;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.TelParsedResult
 * JD-Core Version:    0.6.0
 */