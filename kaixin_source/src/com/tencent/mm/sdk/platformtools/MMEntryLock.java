package com.tencent.mm.sdk.platformtools;

import java.util.HashSet;
import java.util.Set;

public final class MMEntryLock
{
  private static Set<String> an = new HashSet();

  public static boolean isLocked(String paramString)
  {
    return an.contains(paramString);
  }

  public static boolean lock(String paramString)
  {
    if (isLocked(paramString))
    {
      Log.d("MicroMsg.MMEntryLock", "locked-" + paramString);
      return false;
    }
    Log.d("MicroMsg.MMEntryLock", "lock-" + paramString);
    return an.add(paramString);
  }

  public static void unlock(String paramString)
  {
    an.remove(paramString);
    Log.d("MicroMsg.MMEntryLock", "unlock-" + paramString);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.MMEntryLock
 * JD-Core Version:    0.6.0
 */