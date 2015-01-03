package com.kaixin001.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

public class DeviceUtil
{
  public static String MD5(String paramString)
  {
    try
    {
      MessageDigest localMessageDigest2 = MessageDigest.getInstance("md5");
      localMessageDigest1 = localMessageDigest2;
      arrayOfChar = paramString.toCharArray();
      arrayOfByte1 = new byte[arrayOfChar.length];
      i = 0;
      if (i >= arrayOfChar.length)
      {
        arrayOfByte2 = localMessageDigest1.digest(arrayOfByte1);
        localStringBuffer = new StringBuffer();
        j = 0;
        if (j < arrayOfByte2.length)
          break label91;
        return localStringBuffer.toString();
      }
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      while (true)
      {
        char[] arrayOfChar;
        byte[] arrayOfByte1;
        int i;
        byte[] arrayOfByte2;
        StringBuffer localStringBuffer;
        int j;
        localNoSuchAlgorithmException.printStackTrace();
        MessageDigest localMessageDigest1 = null;
        continue;
        arrayOfByte1[i] = (byte)arrayOfChar[i];
        i++;
        continue;
        label91: int k = 0xFF & arrayOfByte2[j];
        if (k < 16)
          localStringBuffer.append("0");
        localStringBuffer.append(Integer.toHexString(k));
        j++;
      }
    }
  }

  public static String getBrandAndType()
  {
    return Build.BOARD + Build.MODEL;
  }

  public static int getCarrier(Context paramContext)
  {
    String str = ((TelephonyManager)paramContext.getSystemService("phone")).getSubscriberId();
    int i = 0;
    if (str != null)
    {
      if ((!str.startsWith("46000")) && (!str.startsWith("46002")))
        break label41;
      i = 1;
    }
    label41: boolean bool;
    do
    {
      return i;
      if (str.startsWith("46001"))
        return 2;
      bool = str.startsWith("46003");
      i = 0;
    }
    while (!bool);
    return 3;
  }

  public static int getConnType(Context paramContext)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    int i = 0;
    if (localNetworkInfo != null)
    {
      if (localNetworkInfo.getType() != 1)
        break label31;
      i = 1;
    }
    label31: int j;
    do
    {
      return i;
      if ((localNetworkInfo.getSubtype() == 2) || (localNetworkInfo.getSubtype() == 1) || (localNetworkInfo.getSubtype() == 4))
        return 2;
      j = localNetworkInfo.getType();
      i = 0;
    }
    while (j != 0);
    return 3;
  }

  public static String getImei(Context paramContext)
  {
    return MD5(((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId());
  }

  public static String getLocalIP(Context paramContext)
  {
    Object localObject = "";
    try
    {
      Enumeration localEnumeration1 = NetworkInterface.getNetworkInterfaces();
      while (true)
      {
        if (!localEnumeration1.hasMoreElements())
          return localObject;
        Enumeration localEnumeration2 = ((NetworkInterface)localEnumeration1.nextElement()).getInetAddresses();
        while (localEnumeration2.hasMoreElements())
        {
          InetAddress localInetAddress = (InetAddress)localEnumeration2.nextElement();
          if (localInetAddress.isLoopbackAddress())
            continue;
          String str = localInetAddress.getHostAddress().toString();
          localObject = str;
        }
      }
    }
    catch (SocketException localSocketException)
    {
    }
    return (String)localObject;
  }

  public static String getPackageName(Context paramContext)
  {
    return paramContext.getPackageName();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.DeviceUtil
 * JD-Core Version:    0.6.0
 */