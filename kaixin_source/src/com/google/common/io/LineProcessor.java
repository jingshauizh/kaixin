package com.google.common.io;

import com.google.common.annotations.Beta;
import java.io.IOException;

@Beta
public abstract interface LineProcessor<T>
{
  public abstract T getResult();

  public abstract boolean processLine(String paramString)
    throws IOException;
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.io.LineProcessor
 * JD-Core Version:    0.6.0
 */