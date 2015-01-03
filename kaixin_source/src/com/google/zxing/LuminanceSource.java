package com.google.zxing;

public abstract class LuminanceSource
{
  private final int height;
  private final int width;

  protected LuminanceSource(int paramInt1, int paramInt2)
  {
    this.width = paramInt1;
    this.height = paramInt2;
  }

  public LuminanceSource crop(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    throw new RuntimeException("This luminance source does not support cropping.");
  }

  public final int getHeight()
  {
    return this.height;
  }

  public abstract byte[] getMatrix();

  public abstract byte[] getRow(int paramInt, byte[] paramArrayOfByte);

  public final int getWidth()
  {
    return this.width;
  }

  public boolean isCropSupported()
  {
    return false;
  }

  public boolean isRotateSupported()
  {
    return false;
  }

  public LuminanceSource rotateCounterClockwise()
  {
    throw new RuntimeException("This luminance source does not support rotation.");
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.LuminanceSource
 * JD-Core Version:    0.6.0
 */