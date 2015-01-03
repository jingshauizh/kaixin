package com.kaixin001.nfc;

import android.nfc.NdefRecord;
import com.google.common.base.Preconditions;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class NfcTextRecord
{
  private final String mLanguageCode;
  private final String mText;

  private NfcTextRecord(String paramString1, String paramString2)
  {
    this.mLanguageCode = ((String)Preconditions.checkNotNull(paramString1));
    this.mText = ((String)Preconditions.checkNotNull(paramString2));
  }

  public static boolean isText(NdefRecord paramNdefRecord)
  {
    try
    {
      parse(paramNdefRecord);
      return true;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
    }
    return false;
  }

  public static String parse(NdefRecord paramNdefRecord)
  {
    int i = 1;
    if (paramNdefRecord.getTnf() == i);
    while (true)
    {
      Preconditions.checkArgument(i);
      Preconditions.checkArgument(Arrays.equals(paramNdefRecord.getType(), NdefRecord.RTD_TEXT));
      try
      {
        byte[] arrayOfByte = paramNdefRecord.getPayload();
        if ((0x80 & arrayOfByte[0]) == 0);
        for (String str = "UTF-8"; ; str = "UTF-16")
        {
          int k = 0x3F & arrayOfByte[0];
          new String(arrayOfByte, 1, k, "US-ASCII");
          return new String(arrayOfByte, k + 1, -1 + (arrayOfByte.length - k), str);
        }
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        throw new IllegalArgumentException(localUnsupportedEncodingException);
      }
      int j = 0;
    }
  }

  public String getLanguageCode()
  {
    return this.mLanguageCode;
  }

  public String getText()
  {
    return this.mText;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.nfc.NfcTextRecord
 * JD-Core Version:    0.6.0
 */