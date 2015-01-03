package com.kaixin001.nfc;

import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableBiMap.Builder;
import com.google.common.primitives.Bytes;
import java.nio.charset.Charset;
import java.util.Arrays;

public class NfcUriRecord
{
  public static final NdefMessage[] EMPTY_TAG;
  public static final String RECORD_TYPE = "UriRecord";
  public static final BiMap<Byte, String> URI_PREFIX_MAP = ImmutableBiMap.builder().put(Byte.valueOf(0), "").put(Byte.valueOf(1), "http://www.").put(Byte.valueOf(2), "https://www.").put(Byte.valueOf(3), "http://").put(Byte.valueOf(4), "https://").put(Byte.valueOf(5), "tel:").put(Byte.valueOf(6), "mailto:").put(Byte.valueOf(7), "ftp://anonymous:anonymous@").put(Byte.valueOf(8), "ftp://ftp.").put(Byte.valueOf(9), "ftps://").put(Byte.valueOf(10), "sftp://").put(Byte.valueOf(11), "smb://").put(Byte.valueOf(12), "nfs://").put(Byte.valueOf(13), "ftp://").put(Byte.valueOf(14), "dav://").put(Byte.valueOf(15), "news:").put(Byte.valueOf(16), "telnet://").put(Byte.valueOf(17), "imap:").put(Byte.valueOf(18), "rtsp://").put(Byte.valueOf(19), "urn:").put(Byte.valueOf(20), "pop:").put(Byte.valueOf(21), "sip:").put(Byte.valueOf(22), "sips:").put(Byte.valueOf(23), "tftp:").put(Byte.valueOf(24), "btspp://").put(Byte.valueOf(25), "btl2cap://").put(Byte.valueOf(26), "btgoep://").put(Byte.valueOf(27), "tcpobex://").put(Byte.valueOf(28), "irdaobex://").put(Byte.valueOf(29), "file://").put(Byte.valueOf(30), "urn:epc:id:").put(Byte.valueOf(31), "urn:epc:tag:").put(Byte.valueOf(32), "urn:epc:pat:").put(Byte.valueOf(33), "urn:epc:raw:").put(Byte.valueOf(34), "urn:epc:").put(Byte.valueOf(35), "urn:nfc:").build();
  private final Uri mUri;

  static
  {
    byte[] arrayOfByte = new byte[0];
    EMPTY_TAG = new NdefMessage[] { new NdefMessage(new NdefRecord[] { new NdefRecord(5, arrayOfByte, arrayOfByte, arrayOfByte) }) };
  }

  private NfcUriRecord(Uri paramUri)
  {
    this.mUri = ((Uri)Preconditions.checkNotNull(paramUri));
  }

  public static boolean isUri(NdefRecord paramNdefRecord)
  {
    try
    {
      parse(paramNdefRecord);
      return true;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
    }
    return false;
  }

  public static NfcUriRecord parse(NdefRecord paramNdefRecord)
  {
    switch (paramNdefRecord.getTnf())
    {
    case 2:
    default:
      throw new IllegalArgumentException("Unknown TNF " + paramNdefRecord.getTnf());
    case 1:
      return parseWellKnown(paramNdefRecord);
    case 3:
      return parseAbsolute(paramNdefRecord);
    case 4:
    }
    return parseAbsolute(paramNdefRecord);
  }

  private static NfcUriRecord parseAbsolute(NdefRecord paramNdefRecord)
  {
    return new NfcUriRecord(Uri.parse(new String(paramNdefRecord.getPayload(), Charset.forName("UTF-8"))));
  }

  private static NfcUriRecord parseWellKnown(NdefRecord paramNdefRecord)
  {
    Preconditions.checkArgument(Arrays.equals(paramNdefRecord.getType(), NdefRecord.RTD_URI));
    byte[] arrayOfByte = paramNdefRecord.getPayload();
    String str = (String)URI_PREFIX_MAP.get(Byte.valueOf(arrayOfByte[0]));
    byte[][] arrayOfByte1 = new byte[2][];
    arrayOfByte1[0] = str.getBytes(Charset.forName("UTF-8"));
    arrayOfByte1[1] = Arrays.copyOfRange(arrayOfByte, 1, arrayOfByte.length);
    return new NfcUriRecord(Uri.parse(new String(Bytes.concat(arrayOfByte1), Charset.forName("UTF-8"))));
  }

  public Uri getUri()
  {
    return this.mUri;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.nfc.NfcUriRecord
 * JD-Core Version:    0.6.0
 */