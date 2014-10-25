/*************************************************************************************************
* @author Jason Zhang
* email: jingshuaizh@163.com
* blog:  http://blog.csdn.net/jingshuaizh
* 
* @date Oct 3, 2014
**************************************************************************************************/
package com.kaixin001.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GetSystemPropertyUtil
{
  private static final String TAG = "GetSytemPropertyUtil";

  public static Class creatClassObject(String paramString)
    throws ClassNotFoundException
  {
    return Class.forName(paramString);
  }

  public static String getSystemProperties(Class paramClass, String paramString)
  {
    try
    {
      String str = (String)paramClass.getMethod("get", new Class[] { String.class }).invoke(paramClass.newInstance(), new Object[] { paramString });
      return str;
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
      return null;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      localNoSuchMethodException.printStackTrace();
      return null;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return null;
    }
    catch (InstantiationException localInstantiationException)
    {
      localInstantiationException.printStackTrace();
      return null;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return null;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.printStackTrace();
    }
    return null;
  }
}