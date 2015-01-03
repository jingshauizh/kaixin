package com.google.zxing.client.result;

public final class CalendarParsedResult extends ParsedResult
{
  private final String attendee;
  private final String description;
  private final String end;
  private final double latitude;
  private final String location;
  private final double longitude;
  private final String start;
  private final String summary;

  public CalendarParsedResult(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    this(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, (0.0D / 0.0D), (0.0D / 0.0D));
  }

  public CalendarParsedResult(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, double paramDouble1, double paramDouble2)
  {
    super(ParsedResultType.CALENDAR);
    if (paramString2 == null)
      throw new IllegalArgumentException();
    validateDate(paramString2);
    if (paramString3 == null)
      paramString3 = paramString2;
    while (true)
    {
      this.summary = paramString1;
      this.start = paramString2;
      this.end = paramString3;
      this.location = paramString4;
      this.attendee = paramString5;
      this.description = paramString6;
      this.latitude = paramDouble1;
      this.longitude = paramDouble2;
      return;
      validateDate(paramString3);
    }
  }

  private static void validateDate(String paramString)
  {
    if (paramString != null)
    {
      int i = paramString.length();
      if ((i != 8) && (i != 15) && (i != 16))
        throw new IllegalArgumentException();
      int j = 0;
      while (true)
      {
        if (j >= 8)
        {
          if (i <= 8)
            break;
          if (paramString.charAt(8) != 'T')
            throw new IllegalArgumentException();
        }
        else
        {
          if (!Character.isDigit(paramString.charAt(j)))
            throw new IllegalArgumentException();
          j++;
          continue;
        }
        for (int k = 9; ; k++)
        {
          if (k >= 15)
          {
            if ((i != 16) || (paramString.charAt(15) == 'Z'))
              break;
            throw new IllegalArgumentException();
          }
          if (Character.isDigit(paramString.charAt(k)))
            continue;
          throw new IllegalArgumentException();
        }
      }
    }
  }

  public String getAttendee()
  {
    return this.attendee;
  }

  public String getDescription()
  {
    return this.description;
  }

  public String getDisplayResult()
  {
    StringBuffer localStringBuffer = new StringBuffer(100);
    maybeAppend(this.summary, localStringBuffer);
    maybeAppend(this.start, localStringBuffer);
    maybeAppend(this.end, localStringBuffer);
    maybeAppend(this.location, localStringBuffer);
    maybeAppend(this.attendee, localStringBuffer);
    maybeAppend(this.description, localStringBuffer);
    return localStringBuffer.toString();
  }

  public String getEnd()
  {
    return this.end;
  }

  public double getLatitude()
  {
    return this.latitude;
  }

  public String getLocation()
  {
    return this.location;
  }

  public double getLongitude()
  {
    return this.longitude;
  }

  public String getStart()
  {
    return this.start;
  }

  public String getSummary()
  {
    return this.summary;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.CalendarParsedResult
 * JD-Core Version:    0.6.0
 */