package org.apache.sanselan.formats.tiff;

public class JpegImageData extends TiffElement.DataElement
{
  public JpegImageData(int paramInt1, int paramInt2, byte[] paramArrayOfByte)
  {
    super(paramInt1, paramInt2, paramArrayOfByte);
  }

  public String getElementDescription(boolean paramBoolean)
  {
    return "Jpeg image data: " + this.data.length + " bytes";
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.JpegImageData
 * JD-Core Version:    0.6.0
 */