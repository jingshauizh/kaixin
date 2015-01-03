package org.apache.sanselan.formats.tiff;

import java.util.Comparator;

public abstract class TiffElement
{
  public static final Comparator COMPARATOR = new Comparator()
  {
    public int compare(Object paramObject1, Object paramObject2)
    {
      TiffElement localTiffElement1 = (TiffElement)paramObject1;
      TiffElement localTiffElement2 = (TiffElement)paramObject2;
      return localTiffElement1.offset - localTiffElement2.offset;
    }
  };
  public final int length;
  public final int offset;

  public TiffElement(int paramInt1, int paramInt2)
  {
    this.offset = paramInt1;
    this.length = paramInt2;
  }

  public String getElementDescription()
  {
    return getElementDescription(false);
  }

  public abstract String getElementDescription(boolean paramBoolean);

  public static abstract class DataElement extends TiffElement
  {
    public final byte[] data;

    public DataElement(int paramInt1, int paramInt2, byte[] paramArrayOfByte)
    {
      super(paramInt2);
      this.data = paramArrayOfByte;
    }
  }

  public static final class Stub extends TiffElement
  {
    public Stub(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }

    public String getElementDescription(boolean paramBoolean)
    {
      return "Element, offset: " + this.offset + ", length: " + this.length + ", last: " + (this.offset + this.length);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.TiffElement
 * JD-Core Version:    0.6.0
 */