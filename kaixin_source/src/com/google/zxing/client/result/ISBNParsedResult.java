package com.google.zxing.client.result;

public final class ISBNParsedResult extends ParsedResult
{
  private final String isbn;

  ISBNParsedResult(String paramString)
  {
    super(ParsedResultType.ISBN);
    this.isbn = paramString;
  }

  public String getDisplayResult()
  {
    return this.isbn;
  }

  public String getISBN()
  {
    return this.isbn;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.ISBNParsedResult
 * JD-Core Version:    0.6.0
 */