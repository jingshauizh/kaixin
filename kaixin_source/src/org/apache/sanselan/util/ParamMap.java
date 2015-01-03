package org.apache.sanselan.util;

import java.util.Map;

public class ParamMap
{
  public static boolean getParamBoolean(Map paramMap, Object paramObject, boolean paramBoolean)
  {
    boolean bool = paramBoolean;
    if (paramMap == null);
    for (Object localObject = null; ; localObject = paramMap.get(paramObject))
    {
      if ((localObject != null) && ((localObject instanceof Boolean)))
        bool = ((Boolean)localObject).booleanValue();
      return bool;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.util.ParamMap
 * JD-Core Version:    0.6.0
 */