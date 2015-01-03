package com.kaixin001.network;

import android.text.TextUtils;
import com.kaixin001.model.Setting;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class XAuth
{
  public static String CONSUMER_KEY = "87247717949570179fa41c43e20ed289";
  public static String CONSUMER_SECRET = "8207525c8aa35c89b29385057f5905c9";

  private static String constructRequestURL(String paramString)
  {
    int i = paramString.indexOf("?");
    if (-1 != i)
      paramString = paramString.substring(0, i);
    int j = paramString.indexOf("/", 8);
    String str = paramString.substring(0, j).toLowerCase();
    int k = str.indexOf(":", 8);
    if (-1 != k)
      if ((!str.startsWith("http://")) || (!str.endsWith(":80")))
        break label103;
    for (str = str.substring(0, k); ; str = str.substring(0, k))
      label103: 
      do
        return str + paramString.substring(j);
      while ((!str.startsWith("https://")) || (!str.endsWith(":443")));
  }

  public static String encode(String paramString)
  {
    if (paramString == null)
      return "";
    String str;
    StringBuffer localStringBuffer;
    int i;
    try
    {
      str = URLEncoder.encode(paramString, "UTF-8");
      localStringBuffer = new StringBuffer(str.length());
      i = 0;
      if (i >= str.length())
        return localStringBuffer.toString();
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      return null;
    }
    char c = str.charAt(i);
    if (c == '*')
      localStringBuffer.append("%2A");
    while (true)
    {
      i++;
      break;
      if (c == '+')
      {
        localStringBuffer.append("%20");
        continue;
      }
      if ((c == '%') && (i + 1 < str.length()) && (str.charAt(i + 1) == '7') && (str.charAt(i + 2) == 'E'))
      {
        localStringBuffer.append('~');
        i += 2;
        continue;
      }
      localStringBuffer.append(c);
    }
  }

  public static String encodeUrl(HashMap<String, Object> paramHashMap)
  {
    if (paramHashMap == null)
      return "";
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 1;
    Iterator localIterator = paramHashMap.keySet().iterator();
    if (!localIterator.hasNext())
      return localStringBuilder.toString();
    String str = (String)localIterator.next();
    if (i != 0)
      i = 0;
    while (true)
    {
      localStringBuilder.append(URLEncoder.encode(str) + "=" + URLEncoder.encode(String.valueOf(paramHashMap.get(str))));
      break;
      localStringBuilder.append("&");
    }
  }

  private static String generateSignature(String paramString1, String paramString2, String paramString3)
  {
    try
    {
      Mac localMac = Mac.getInstance("HmacSHA1");
      StringBuilder localStringBuilder = new StringBuilder(String.valueOf(encode(paramString2))).append("&");
      if (paramString3 != null);
      for (String str = encode(paramString3); ; str = "")
      {
        localMac.init(new SecretKeySpec(str.getBytes(), "HmacSHA1"));
        byte[] arrayOfByte2 = localMac.doFinal(paramString1.getBytes());
        arrayOfByte1 = arrayOfByte2;
        return new BASE64Encoder().encode(arrayOfByte1);
      }
    }
    catch (InvalidKeyException localInvalidKeyException)
    {
      while (true)
      {
        localInvalidKeyException.printStackTrace();
        arrayOfByte1 = null;
      }
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      while (true)
        byte[] arrayOfByte1 = null;
    }
  }

  public static Map<String, Object> generateURLParams(String paramString1, String paramString2, Map<String, Object> paramMap, String paramString3, String paramString4, String paramString5)
  {
    if (paramMap == null)
      paramMap = new HashMap();
    long l = System.currentTimeMillis() / 1000L;
    String str1 = md5(String.valueOf(l + new Random().nextInt()));
    paramMap.put("oauth_consumer_key", paramString3);
    paramMap.put("oauth_signature_method", "HMAC-SHA1");
    String str2 = String.valueOf(l);
    paramMap.put("oauth_timestamp", str2);
    paramMap.put("oauth_nonce", str1);
    paramMap.put("oauth_version", "1.0");
    String str3 = Setting.getInstance().getManufacturerName() + "$!" + Setting.getInstance().getDeviceName();
    paramMap.put("device_name", str3);
    StringBuffer localStringBuffer1 = new StringBuffer();
    Iterator localIterator = new TreeSet(paramMap.keySet()).iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        StringBuffer localStringBuffer2 = new StringBuffer(paramString2).append("&").append(encode(constructRequestURL(paramString1)));
        if (localStringBuffer1.length() > 0)
          localStringBuffer2.append("&").append(encode(localStringBuffer1.toString()));
        String str5 = generateSignature(localStringBuffer2.toString(), paramString4, paramString5);
        paramMap.put("oauth_signature", str5);
        return paramMap;
      }
      String str4 = (String)localIterator.next();
      if ((paramMap.get(str4) instanceof File))
        continue;
      if (localStringBuffer1.length() != 0)
        localStringBuffer1.append("&");
      localStringBuffer1.append(encode(str4));
      localStringBuffer1.append("=");
      localStringBuffer1.append(encode((String)paramMap.get(str4)));
    }
  }

  public static Map<String, Object> getParams(String paramString1, String paramString2, String paramString3, String[] paramArrayOfString, String paramString4, String paramString5)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("ctype", Setting.getInstance().getCType());
    localHashMap.put("x_auth_mode", "client_auth");
    localHashMap.put("x_auth_username", paramString2);
    localHashMap.put("x_auth_password", paramString3);
    localHashMap.put("getsimi", "1");
    localHashMap.put("device_name", Setting.getInstance().getManufacturerName() + "$!" + Setting.getInstance().getDeviceName());
    if (!TextUtils.isEmpty(paramString4))
      localHashMap.put("rcode", paramString4);
    if (!TextUtils.isEmpty(paramString5))
      localHashMap.put("code", paramString5);
    if ((paramArrayOfString != null) && (paramArrayOfString.length > 0))
      localHashMap.put("scope", TextUtils.join(" ", paramArrayOfString));
    return generateURLParams(paramString1, "POST", localHashMap, CONSUMER_KEY, CONSUMER_SECRET, null);
  }

  public static String getParamsForEmail(String paramString)
  {
    String str1 = "POST" + "&" + encode("/users/sendpwd");
    String str2 = encode(paramString).toString();
    return generateSignature(str1 + "&" + str2, CONSUMER_SECRET, null);
  }

  public static String getParamsForLook(String paramString)
  {
    String str1 = "GET" + "&" + encode("/users/defaulttoken");
    String str2 = encode(paramString).toString();
    return generateSignature(str1 + "&" + str2, CONSUMER_SECRET, null);
  }

  public static Map<String, Object> getQQParams(String paramString1, String paramString2, String paramString3, String paramString4, String[] paramArrayOfString, String paramString5, String paramString6)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("ctype", Setting.getInstance().getCType());
    localHashMap.put("x_auth_mode", "client_auth");
    localHashMap.put("tencent_token", paramString2);
    localHashMap.put("expire", paramString3);
    localHashMap.put("openid", paramString4);
    localHashMap.put("getsimi", "1");
    localHashMap.put("device_name", Setting.getInstance().getManufacturerName() + "$!" + Setting.getInstance().getDeviceName());
    if (!TextUtils.isEmpty(paramString5))
      localHashMap.put("rcode", paramString5);
    if (!TextUtils.isEmpty(paramString6))
      localHashMap.put("code", paramString6);
    if ((paramArrayOfString != null) && (paramArrayOfString.length > 0))
      localHashMap.put("scope", TextUtils.join(" ", paramArrayOfString));
    return generateURLParams(paramString1, "POST", localHashMap, CONSUMER_KEY, CONSUMER_SECRET, null);
  }

  public static Map<String, Object> getWEIBOParams(String paramString1, String paramString2, String paramString3, String paramString4, String[] paramArrayOfString, String paramString5, String paramString6)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("ctype", Setting.getInstance().getCType());
    localHashMap.put("x_auth_mode", "client_auth");
    localHashMap.put("weibo_token", paramString2);
    localHashMap.put("expire", paramString3);
    localHashMap.put("indentity_id", paramString4);
    localHashMap.put("getsimi", "1");
    localHashMap.put("device_name", Setting.getInstance().getManufacturerName() + "$!" + Setting.getInstance().getDeviceName());
    if (!TextUtils.isEmpty(paramString5))
      localHashMap.put("rcode", paramString5);
    if (!TextUtils.isEmpty(paramString6))
      localHashMap.put("code", paramString6);
    if ((paramArrayOfString != null) && (paramArrayOfString.length > 0))
      localHashMap.put("scope", TextUtils.join(" ", paramArrayOfString));
    return generateURLParams(paramString1, "POST", localHashMap, CONSUMER_KEY, CONSUMER_SECRET, null);
  }

  public static String md5(String paramString)
  {
    Object localObject = paramString;
    if (paramString != null);
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString.getBytes());
      localObject = new BigInteger(1, localMessageDigest.digest()).toString(16);
      if (((String)localObject).length() % 2 != 0)
      {
        String str = "0" + (String)localObject;
        localObject = str;
      }
      return localObject;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      localNoSuchAlgorithmException.printStackTrace();
    }
    return (String)localObject;
  }

  static class BASE64Encoder
  {
    private static final char[] encodeTable;
    private static final char last2byte = (char)Integer.parseInt("00000011", 2);
    private static final char last4byte = (char)Integer.parseInt("00001111", 2);
    private static final char last6byte = (char)Integer.parseInt("00111111", 2);
    private static final char lead2byte;
    private static final char lead4byte;
    private static final char lead6byte = (char)Integer.parseInt("11111100", 2);

    static
    {
      lead4byte = (char)Integer.parseInt("11110000", 2);
      lead2byte = (char)Integer.parseInt("11000000", 2);
      encodeTable = new char[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
    }

    public String encode(byte[] paramArrayOfByte)
    {
      StringBuffer localStringBuffer = new StringBuffer(3 + (int)(1.34D * paramArrayOfByte.length));
      int i = 0;
      int j = 0;
      int k = 0;
      if (k >= paramArrayOfByte.length)
        if (localStringBuffer.length() % 4 == 0);
      for (int m = 4 - localStringBuffer.length() % 4; ; m--)
      {
        if (m <= 0)
        {
          return localStringBuffer.toString();
          i %= 8;
          if (i >= 8)
          {
            k++;
            break;
          }
          switch (i)
          {
          case 1:
          case 3:
          case 5:
          default:
          case 0:
          case 2:
          case 4:
          case 6:
          }
          while (true)
          {
            localStringBuffer.append(encodeTable[j]);
            i += 6;
            break;
            j = (char)((char)(paramArrayOfByte[k] & lead6byte) >>> '\002');
            continue;
            j = (char)(paramArrayOfByte[k] & last6byte);
            continue;
            j = (char)((char)(paramArrayOfByte[k] & last4byte) << '\002');
            if (k + 1 >= paramArrayOfByte.length)
              continue;
            j = (char)(j | (paramArrayOfByte[(k + 1)] & lead2byte) >>> 6);
            continue;
            j = (char)((char)(paramArrayOfByte[k] & last2byte) << '\004');
            if (k + 1 >= paramArrayOfByte.length)
              continue;
            j = (char)(j | (paramArrayOfByte[(k + 1)] & lead4byte) >>> 4);
          }
        }
        localStringBuffer.append("=");
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.network.XAuth
 * JD-Core Version:    0.6.0
 */