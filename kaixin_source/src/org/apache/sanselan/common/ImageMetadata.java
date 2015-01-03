package org.apache.sanselan.common;

import java.util.ArrayList;

public class ImageMetadata
  implements IImageMetadata
{
  protected static final String newline = System.getProperty("line.separator");
  private final ArrayList items = new ArrayList();

  public void add(String paramString1, String paramString2)
  {
    add(new Item(paramString1, paramString2));
  }

  public void add(IImageMetadata.IImageMetadataItem paramIImageMetadataItem)
  {
    this.items.add(paramIImageMetadataItem);
  }

  public ArrayList getItems()
  {
    return new ArrayList(this.items);
  }

  public String toString()
  {
    return toString(null);
  }

  public String toString(String paramString)
  {
    if (paramString == null)
      paramString = "";
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; ; i++)
    {
      if (i >= this.items.size())
        return localStringBuffer.toString();
      if (i > 0)
        localStringBuffer.append(newline);
      localStringBuffer.append(((IImageMetadata.IImageMetadataItem)this.items.get(i)).toString(paramString + "\t"));
    }
  }

  public static class Item
    implements IImageMetadata.IImageMetadataItem
  {
    private final String keyword;
    private final String text;

    public Item(String paramString1, String paramString2)
    {
      this.keyword = paramString1;
      this.text = paramString2;
    }

    public String getKeyword()
    {
      return this.keyword;
    }

    public String getText()
    {
      return this.text;
    }

    public String toString()
    {
      return toString(null);
    }

    public String toString(String paramString)
    {
      String str = this.keyword + ": " + this.text;
      if (paramString != null)
        str = paramString + str;
      return str;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.ImageMetadata
 * JD-Core Version:    0.6.0
 */