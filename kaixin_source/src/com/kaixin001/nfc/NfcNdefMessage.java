package com.kaixin001.nfc;

import android.content.Intent;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.os.Parcelable;
import android.util.Log;
import com.kaixin001.util.KXLog;

public class NfcNdefMessage
{
  public static final String LOCATION = "location";
  private static final String LOGTAG = "KxnfcNdefMessage";
  public static final String POI_ID = "poiid";
  public static final String POI_NAME = "poiname";

  private static NdefMessage[] getNdefMessages(Intent paramIntent)
  {
    String str = paramIntent.getAction();
    Parcelable[] arrayOfParcelable2;
    NdefMessage[] arrayOfNdefMessage;
    int j;
    if ("android.nfc.action.TAG_DISCOVERED".equals(str))
    {
      arrayOfParcelable2 = paramIntent.getParcelableArrayExtra("android.nfc.extra.NDEF_MESSAGES");
      if (arrayOfParcelable2 != null)
      {
        arrayOfNdefMessage = new NdefMessage[arrayOfParcelable2.length];
        j = 0;
        if (j < arrayOfParcelable2.length);
      }
    }
    while (true)
    {
      return arrayOfNdefMessage;
      arrayOfNdefMessage[j] = ((NdefMessage)arrayOfParcelable2[j]);
      j++;
      break;
      byte[] arrayOfByte2 = new byte[0];
      return new NdefMessage[] { new NdefMessage(new NdefRecord[] { new NdefRecord(5, arrayOfByte2, arrayOfByte2, arrayOfByte2) }) };
      if (!"android.nfc.action.NDEF_DISCOVERED".equals(str))
        break label205;
      Parcelable[] arrayOfParcelable1 = paramIntent.getParcelableArrayExtra("android.nfc.extra.NDEF_MESSAGES");
      if (arrayOfParcelable1 == null)
        break label164;
      arrayOfNdefMessage = new NdefMessage[arrayOfParcelable1.length];
      for (int i = 0; i < arrayOfParcelable1.length; i++)
        arrayOfNdefMessage[i] = ((NdefMessage)arrayOfParcelable1[i]);
    }
    label164: byte[] arrayOfByte1 = new byte[0];
    return new NdefMessage[] { new NdefMessage(new NdefRecord[] { new NdefRecord(5, arrayOfByte1, arrayOfByte1, arrayOfByte1) }) };
    label205: Log.e("KxnfcNdefMessage", "Unknown intent " + paramIntent);
    return null;
  }

  public static String printNdefMessage(Intent paramIntent)
  {
    NdefMessage[] arrayOfNdefMessage = getNdefMessages(paramIntent);
    KXLog.d("KxnfcNdefMessage", "--------------- NdefMessage.length:" + arrayOfNdefMessage.length + " ---------------");
    String str = null;
    int i = arrayOfNdefMessage.length;
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return str;
      str = NdefMessageParser.parse(arrayOfNdefMessage[j]);
      KXLog.d("KxnfcNdefMessage", "--------------- message:" + str + " ---------------");
    }
  }

  static class NdefMessageParser
  {
    static String getRecords(NdefRecord[] paramArrayOfNdefRecord)
    {
      String str = null;
      int i = paramArrayOfNdefRecord.length;
      int j = 0;
      if (j >= i)
        return str;
      NdefRecord localNdefRecord = paramArrayOfNdefRecord[j];
      if (NfcTextRecord.isText(localNdefRecord))
        str = NfcTextRecord.parse(localNdefRecord);
      while (true)
      {
        j++;
        break;
        if (!NfcUriRecord.isUri(localNdefRecord))
          continue;
        str = NfcUriRecord.parse(localNdefRecord).getUri().toString();
      }
    }

    static String parse(NdefMessage paramNdefMessage)
    {
      return getRecords(paramNdefMessage.getRecords());
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.nfc.NfcNdefMessage
 * JD-Core Version:    0.6.0
 */