package com.kaixin001.zxing.encode;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.common.BitMatrix;
import com.kaixin001.util.KXLog;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

final class QRCodeEncoder
{
  private static final int BLACK = -16777216;
  private static final String TAG = QRCodeEncoder.class.getSimpleName();
  private static final int WHITE = -1;
  private final Activity activity;
  private String contents;
  private final int dimension;
  private String displayContents;
  private BarcodeFormat format;
  private String title;

  QRCodeEncoder(Activity paramActivity, Intent paramIntent, int paramInt)
  {
    this.activity = paramActivity;
    if (paramIntent == null)
      throw new IllegalArgumentException("No valid data to encode.");
    String str = paramIntent.getAction();
    if (str.equals("com.google.zxing.client.android.ENCODE"))
    {
      if (!encodeContentsFromZXingIntent(paramIntent))
        throw new IllegalArgumentException("No valid data to encode.");
    }
    else if ((str.equals("android.intent.action.SEND")) && (!encodeContentsFromShareIntent(paramIntent)))
      throw new IllegalArgumentException("No valid data to encode.");
    this.dimension = paramInt;
  }

  private boolean encodeContentsFromShareIntent(Intent paramIntent)
  {
    if (paramIntent.hasExtra("android.intent.extra.TEXT"))
      return encodeContentsFromShareIntentPlainText(paramIntent);
    return encodeContentsFromShareIntentDefault(paramIntent);
  }

  private boolean encodeContentsFromShareIntentDefault(Intent paramIntent)
  {
    this.format = BarcodeFormat.QR_CODE;
    ParsedResult localParsedResult;
    try
    {
      Uri localUri = (Uri)paramIntent.getExtras().getParcelable("android.intent.extra.STREAM");
      InputStream localInputStream = this.activity.getContentResolver().openInputStream(localUri);
      int i = localInputStream.available();
      if (i <= 0)
      {
        KXLog.d(TAG, "Content stream is empty");
        return false;
      }
      arrayOfByte = new byte[i];
      j = localInputStream.read(arrayOfByte, 0, i);
      if (j < i)
      {
        KXLog.e(TAG, "Unable to fully read available bytes from content stream");
        return false;
      }
    }
    catch (IOException localIOException)
    {
      byte[] arrayOfByte;
      int j;
      KXLog.e(TAG, "encodeContentsFromShareIntentDefault", localIOException);
      return false;
      String str1 = new String(arrayOfByte, 0, j, "UTF-8");
      KXLog.d(TAG, "Encoding share intent content:");
      KXLog.d(TAG, str1);
      localParsedResult = ResultParser.parseResult(new Result(str1, arrayOfByte, null, BarcodeFormat.QR_CODE));
      if (!(localParsedResult instanceof AddressBookParsedResult))
      {
        KXLog.d(TAG, "Result was not an address");
        return false;
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      KXLog.e(TAG, "encodeContentsFromShareIntentDefault", localNullPointerException);
      return false;
    }
    if (!encodeQRCodeContents((AddressBookParsedResult)localParsedResult))
    {
      KXLog.d(TAG, "Unable to encode contents");
      return false;
    }
    String str2 = this.contents;
    int k = 0;
    if (str2 != null)
    {
      int m = this.contents.length();
      k = 0;
      if (m > 0)
        k = 1;
    }
    return k;
  }

  private boolean encodeContentsFromShareIntentPlainText(Intent paramIntent)
  {
    this.contents = paramIntent.getStringExtra("android.intent.extra.TEXT");
    if (this.contents == null);
    do
    {
      return false;
      this.contents = this.contents.trim();
    }
    while (this.contents.length() == 0);
    this.format = BarcodeFormat.QR_CODE;
    if (paramIntent.hasExtra("android.intent.extra.SUBJECT"))
      this.displayContents = paramIntent.getStringExtra("android.intent.extra.SUBJECT");
    while (true)
    {
      this.title = this.activity.getString(2131428326);
      return true;
      if (paramIntent.hasExtra("android.intent.extra.TITLE"))
      {
        this.displayContents = paramIntent.getStringExtra("android.intent.extra.TITLE");
        continue;
      }
      this.displayContents = this.contents;
    }
  }

  private boolean encodeContentsFromZXingIntent(Intent paramIntent)
  {
    String str1 = paramIntent.getStringExtra("ENCODE_FORMAT");
    while (true)
    {
      try
      {
        this.format = BarcodeFormat.valueOf(str1);
        if ((this.format == null) || (BarcodeFormat.QR_CODE.equals(this.format)))
        {
          str2 = paramIntent.getStringExtra("ENCODE_TYPE");
          if ((str2 == null) || (str2.length() == 0))
            return false;
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        String str2;
        this.format = null;
        continue;
        this.format = BarcodeFormat.QR_CODE;
        encodeQRCodeContents(paramIntent, str2);
      }
      while ((this.contents != null) && (this.contents.length() > 0))
      {
        return true;
        String str3 = paramIntent.getStringExtra("ENCODE_DATA");
        if ((str3 == null) || (str3.length() <= 0))
          continue;
        this.contents = str3;
        this.displayContents = str3;
        this.title = this.activity.getString(2131428326);
      }
    }
  }

  private void encodeQRCodeContents(Intent paramIntent, String paramString)
  {
    if (paramString.equals("TEXT_TYPE"))
    {
      String str = paramIntent.getStringExtra("ENCODE_DATA");
      if ((str != null) && (str.length() > 0))
      {
        this.contents = str;
        this.displayContents = str;
        this.title = this.activity.getString(2131428326);
      }
    }
  }

  private boolean encodeQRCodeContents(AddressBookParsedResult paramAddressBookParsedResult)
  {
    StringBuilder localStringBuilder1 = new StringBuilder(100);
    StringBuilder localStringBuilder2 = new StringBuilder(100);
    localStringBuilder1.append("MECARD:");
    String[] arrayOfString1 = paramAddressBookParsedResult.getNames();
    if ((arrayOfString1 != null) && (arrayOfString1.length > 0))
    {
      String str5 = trim(arrayOfString1[0]);
      if (str5 != null)
      {
        localStringBuilder1.append("N:").append(escapeMECARD(str5)).append(';');
        localStringBuilder2.append(str5);
      }
    }
    String[] arrayOfString2 = paramAddressBookParsedResult.getAddresses();
    int i1;
    String[] arrayOfString3;
    int m;
    label130: String[] arrayOfString4;
    int i;
    if (arrayOfString2 != null)
    {
      int n = arrayOfString2.length;
      i1 = 0;
      if (i1 < n);
    }
    else
    {
      arrayOfString3 = paramAddressBookParsedResult.getPhoneNumbers();
      if (arrayOfString3 != null)
      {
        int k = arrayOfString3.length;
        m = 0;
        if (m < k)
          break label308;
      }
      arrayOfString4 = paramAddressBookParsedResult.getEmails();
      if (arrayOfString4 != null)
        i = arrayOfString4.length;
    }
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        String str1 = trim(paramAddressBookParsedResult.getURL());
        if (str1 != null)
        {
          localStringBuilder1.append("URL:").append(escapeMECARD(str1)).append(';');
          localStringBuilder2.append('\n').append(str1);
        }
        if (localStringBuilder2.length() <= 0)
          break label419;
        localStringBuilder1.append(';');
        this.contents = localStringBuilder1.toString();
        this.displayContents = localStringBuilder2.toString();
        this.title = this.activity.getString(2131428325);
        return true;
        String str4 = trim(arrayOfString2[i1]);
        if (str4 != null)
        {
          localStringBuilder1.append("ADR:").append(escapeMECARD(str4)).append(';');
          localStringBuilder2.append('\n').append(str4);
        }
        i1++;
        break;
        label308: String str3 = trim(arrayOfString3[m]);
        if (str3 != null)
        {
          localStringBuilder1.append("TEL:").append(escapeMECARD(str3)).append(';');
          localStringBuilder2.append('\n').append(PhoneNumberUtils.formatNumber(str3));
        }
        m++;
        break label130;
      }
      String str2 = trim(arrayOfString4[j]);
      if (str2 == null)
        continue;
      localStringBuilder1.append("EMAIL:").append(escapeMECARD(str2)).append(';');
      localStringBuilder2.append('\n').append(str2);
    }
    label419: this.contents = null;
    this.displayContents = null;
    return false;
  }

  private static String escapeMECARD(String paramString)
  {
    if ((paramString == null) || ((paramString.indexOf(':') < 0) && (paramString.indexOf(';') < 0)))
      return paramString;
    int i = paramString.length();
    StringBuilder localStringBuilder = new StringBuilder(i);
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return localStringBuilder.toString();
      char c = paramString.charAt(j);
      if ((c == ':') || (c == ';'))
        localStringBuilder.append('\\');
      localStringBuilder.append(c);
    }
  }

  private static String guessAppropriateEncoding(CharSequence paramCharSequence)
  {
    for (int i = 0; ; i++)
    {
      if (i >= paramCharSequence.length())
        return null;
      if (paramCharSequence.charAt(i) > 'ÿ')
        return "UTF-8";
    }
  }

  private static String trim(String paramString)
  {
    if (paramString == null)
      return null;
    String str = paramString.trim();
    if (str.length() == 0)
      str = null;
    return str;
  }

  Bitmap encodeAsBitmap()
    throws WriterException
  {
    String str = guessAppropriateEncoding(this.contents);
    Hashtable localHashtable = null;
    if (str != null)
    {
      localHashtable = new Hashtable(2);
      localHashtable.put(EncodeHintType.CHARACTER_SET, str);
    }
    BitMatrix localBitMatrix = new MultiFormatWriter().encode(this.contents, this.format, this.dimension, this.dimension, localHashtable);
    int i = localBitMatrix.getWidth();
    int j = localBitMatrix.getHeight();
    int[] arrayOfInt = new int[i * j];
    int m;
    int n;
    for (int k = 0; ; k++)
    {
      if (k >= j)
      {
        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
        localBitmap.setPixels(arrayOfInt, 0, i, 0, 0, i, j);
        return localBitmap;
      }
      m = k * i;
      n = 0;
      if (n < i)
        break;
    }
    int i1 = m + n;
    if (localBitMatrix.get(n, k));
    for (int i2 = -16777216; ; i2 = -1)
    {
      arrayOfInt[i1] = i2;
      n++;
      break;
    }
  }

  public String getContents()
  {
    return this.contents;
  }

  public String getDisplayContents()
  {
    return this.displayContents;
  }

  public String getTitle()
  {
    return this.title;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.zxing.encode.QRCodeEncoder
 * JD-Core Version:    0.6.0
 */