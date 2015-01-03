package com.tencent.mm.sdk.storage;

import android.content.ContentValues;
import android.database.Cursor;

public abstract interface MDBItem
{
  public abstract void convertFrom(Cursor paramCursor);

  public abstract ContentValues convertTo();
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.storage.MDBItem
 * JD-Core Version:    0.6.0
 */