package com.google.zxing.client.result;

public abstract class ParsedResult
{
  private final ParsedResultType type;

  protected ParsedResult(ParsedResultType paramParsedResultType)
  {
    this.type = paramParsedResultType;
  }

  public static void maybeAppend(String paramString, StringBuffer paramStringBuffer)
  {
    if ((paramString != null) && (paramString.length() > 0))
    {
      if (paramStringBuffer.length() > 0)
        paramStringBuffer.append('\n');
      paramStringBuffer.append(paramString);
    }
  }

  public static void maybeAppend(String[] paramArrayOfString, StringBuffer paramStringBuffer)
  {
    if (paramArrayOfString != null);
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfString.length)
        return;
      if ((paramArrayOfString[i] == null) || (paramArrayOfString[i].length() <= 0))
        continue;
      if (paramStringBuffer.length() > 0)
        paramStringBuffer.append('\n');
      paramStringBuffer.append(paramArrayOfString[i]);
    }
  }

  public abstract String getDisplayResult();

  public ParsedResultType getType()
  {
    return this.type;
  }

  public String toString()
  {
    return getDisplayResult();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.ParsedResult
 * JD-Core Version:    0.6.0
 */