package org.apache.sanselan;

public class ImageFormat
{
  public static final ImageFormat IMAGE_FORMAT_BMP;
  public static final ImageFormat IMAGE_FORMAT_GIF;
  public static final ImageFormat IMAGE_FORMAT_ICO;
  public static final ImageFormat IMAGE_FORMAT_JBIG2;
  public static final ImageFormat IMAGE_FORMAT_JPEG;
  public static final ImageFormat IMAGE_FORMAT_PBM;
  public static final ImageFormat IMAGE_FORMAT_PGM;
  public static final ImageFormat IMAGE_FORMAT_PNG;
  public static final ImageFormat IMAGE_FORMAT_PNM;
  public static final ImageFormat IMAGE_FORMAT_PPM;
  public static final ImageFormat IMAGE_FORMAT_PSD;
  public static final ImageFormat IMAGE_FORMAT_TGA;
  public static final ImageFormat IMAGE_FORMAT_TIFF;
  public static final ImageFormat IMAGE_FORMAT_UNKNOWN = new ImageFormat("UNKNOWN", false);
  public final boolean actual;
  public final String extension;
  public final String name;

  static
  {
    IMAGE_FORMAT_PNG = new ImageFormat("PNG");
    IMAGE_FORMAT_GIF = new ImageFormat("GIF");
    IMAGE_FORMAT_ICO = new ImageFormat("ICO");
    IMAGE_FORMAT_TIFF = new ImageFormat("TIFF");
    IMAGE_FORMAT_JPEG = new ImageFormat("JPEG");
    IMAGE_FORMAT_BMP = new ImageFormat("BMP");
    IMAGE_FORMAT_PSD = new ImageFormat("PSD");
    IMAGE_FORMAT_PBM = new ImageFormat("PBM");
    IMAGE_FORMAT_PGM = new ImageFormat("PGM");
    IMAGE_FORMAT_PPM = new ImageFormat("PPM");
    IMAGE_FORMAT_PNM = new ImageFormat("PNM");
    IMAGE_FORMAT_TGA = new ImageFormat("TGA");
    IMAGE_FORMAT_JBIG2 = new ImageFormat("JBig2");
  }

  private ImageFormat(String paramString)
  {
    this.name = paramString;
    this.extension = paramString;
    this.actual = true;
  }

  private ImageFormat(String paramString, boolean paramBoolean)
  {
    this.name = paramString;
    this.extension = paramString;
    this.actual = paramBoolean;
  }

  public static final ImageFormat[] getAllFormats()
  {
    ImageFormat[] arrayOfImageFormat = new ImageFormat[13];
    arrayOfImageFormat[0] = IMAGE_FORMAT_UNKNOWN;
    arrayOfImageFormat[1] = IMAGE_FORMAT_PNG;
    arrayOfImageFormat[2] = IMAGE_FORMAT_GIF;
    arrayOfImageFormat[3] = IMAGE_FORMAT_TIFF;
    arrayOfImageFormat[4] = IMAGE_FORMAT_JPEG;
    arrayOfImageFormat[5] = IMAGE_FORMAT_BMP;
    arrayOfImageFormat[6] = IMAGE_FORMAT_PSD;
    arrayOfImageFormat[7] = IMAGE_FORMAT_PBM;
    arrayOfImageFormat[8] = IMAGE_FORMAT_PGM;
    arrayOfImageFormat[9] = IMAGE_FORMAT_PPM;
    arrayOfImageFormat[10] = IMAGE_FORMAT_PNM;
    arrayOfImageFormat[11] = IMAGE_FORMAT_TGA;
    arrayOfImageFormat[12] = IMAGE_FORMAT_JBIG2;
    return arrayOfImageFormat;
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ImageFormat))
      return false;
    return ((ImageFormat)paramObject).name.equals(this.name);
  }

  public int hashCode()
  {
    return this.name.hashCode();
  }

  public String toString()
  {
    return "{" + this.name + ": " + this.extension + "}";
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.ImageFormat
 * JD-Core Version:    0.6.0
 */