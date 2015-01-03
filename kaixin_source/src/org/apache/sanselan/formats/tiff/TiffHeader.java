package org.apache.sanselan.formats.tiff;

public class TiffHeader extends TiffElement
{
  public final int byteOrder;
  public final int offsetToFirstIFD;
  public final int tiffVersion;

  public TiffHeader(int paramInt1, int paramInt2, int paramInt3)
  {
    super(0, 8);
    this.byteOrder = paramInt1;
    this.tiffVersion = paramInt2;
    this.offsetToFirstIFD = paramInt3;
  }

  public String getElementDescription(boolean paramBoolean)
  {
    if (paramBoolean)
      return null;
    return "TIFF Header";
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.TiffHeader
 * JD-Core Version:    0.6.0
 */