package com.tencent.record.debug;

import java.io.File;
import java.io.FileFilter;

class g
  implements FileFilter
{
  g(e parame)
  {
  }

  public boolean accept(File paramFile)
  {
    if (!paramFile.getName().endsWith(this.a.j()));
    do
      return false;
    while (e.d(paramFile) == -1);
    return true;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.record.debug.g
 * JD-Core Version:    0.6.0
 */