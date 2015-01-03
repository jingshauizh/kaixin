package org.apache.sanselan.common;

import java.util.ArrayList;

public abstract interface IImageMetadata
{
  public abstract ArrayList getItems();

  public abstract String toString(String paramString);

  public static abstract interface IImageMetadataItem
  {
    public abstract String toString();

    public abstract String toString(String paramString);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.IImageMetadata
 * JD-Core Version:    0.6.0
 */