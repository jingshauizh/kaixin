package org.apache.sanselan.formats.tiff.constants;

public abstract interface TiffDirectoryConstants
{
  public static final int DIRECTORY_TYPE_DIR_0 = 0;
  public static final int DIRECTORY_TYPE_DIR_1 = 1;
  public static final int DIRECTORY_TYPE_DIR_2 = 2;
  public static final int DIRECTORY_TYPE_DIR_3 = 3;
  public static final int DIRECTORY_TYPE_DIR_4 = 4;
  public static final int DIRECTORY_TYPE_EXIF = -2;
  public static final int DIRECTORY_TYPE_GPS = -3;
  public static final int DIRECTORY_TYPE_INTEROPERABILITY = -4;
  public static final int DIRECTORY_TYPE_MAKER_NOTES = -5;
  public static final int DIRECTORY_TYPE_ROOT = 0;
  public static final int DIRECTORY_TYPE_SUB = 1;
  public static final int DIRECTORY_TYPE_SUB0 = 1;
  public static final int DIRECTORY_TYPE_SUB1 = 2;
  public static final int DIRECTORY_TYPE_SUB2 = 3;
  public static final int DIRECTORY_TYPE_THUMBNAIL = 2;
  public static final int DIRECTORY_TYPE_UNKNOWN = -1;
  public static final ExifDirectoryType[] EXIF_DIRECTORIES;
  public static final ExifDirectoryType EXIF_DIRECTORY_EXIF_IFD;
  public static final ExifDirectoryType EXIF_DIRECTORY_GPS;
  public static final ExifDirectoryType EXIF_DIRECTORY_IFD0;
  public static final ExifDirectoryType EXIF_DIRECTORY_IFD1;
  public static final ExifDirectoryType EXIF_DIRECTORY_IFD2;
  public static final ExifDirectoryType EXIF_DIRECTORY_IFD3;
  public static final ExifDirectoryType EXIF_DIRECTORY_INTEROP_IFD;
  public static final ExifDirectoryType EXIF_DIRECTORY_MAKER_NOTES;
  public static final ExifDirectoryType EXIF_DIRECTORY_SUB_IFD;
  public static final ExifDirectoryType EXIF_DIRECTORY_SUB_IFD1;
  public static final ExifDirectoryType EXIF_DIRECTORY_SUB_IFD2;
  public static final ExifDirectoryType EXIF_DIRECTORY_UNKNOWN;
  public static final ExifDirectoryType TIFF_DIRECTORY_IFD0 = new TiffDirectoryConstants.ExifDirectoryType.Image(0, "IFD0");
  public static final ExifDirectoryType TIFF_DIRECTORY_IFD1;
  public static final ExifDirectoryType TIFF_DIRECTORY_IFD2;
  public static final ExifDirectoryType TIFF_DIRECTORY_IFD3;
  public static final ExifDirectoryType TIFF_DIRECTORY_ROOT;

  static
  {
    EXIF_DIRECTORY_IFD0 = TIFF_DIRECTORY_IFD0;
    TIFF_DIRECTORY_ROOT = TIFF_DIRECTORY_IFD0;
    TIFF_DIRECTORY_IFD1 = new TiffDirectoryConstants.ExifDirectoryType.Image(1, "IFD1");
    EXIF_DIRECTORY_IFD1 = TIFF_DIRECTORY_IFD1;
    TIFF_DIRECTORY_IFD2 = new TiffDirectoryConstants.ExifDirectoryType.Image(2, "IFD2");
    EXIF_DIRECTORY_IFD2 = TIFF_DIRECTORY_IFD2;
    TIFF_DIRECTORY_IFD3 = new TiffDirectoryConstants.ExifDirectoryType.Image(3, "IFD3");
    EXIF_DIRECTORY_IFD3 = TIFF_DIRECTORY_IFD3;
    EXIF_DIRECTORY_SUB_IFD = TIFF_DIRECTORY_IFD1;
    EXIF_DIRECTORY_SUB_IFD1 = TIFF_DIRECTORY_IFD2;
    EXIF_DIRECTORY_SUB_IFD2 = TIFF_DIRECTORY_IFD3;
    EXIF_DIRECTORY_INTEROP_IFD = new TiffDirectoryConstants.ExifDirectoryType.Special(-4, "Interop IFD");
    EXIF_DIRECTORY_MAKER_NOTES = new TiffDirectoryConstants.ExifDirectoryType.Special(-5, "Maker Notes");
    EXIF_DIRECTORY_EXIF_IFD = new TiffDirectoryConstants.ExifDirectoryType.Special(-2, "Exif IFD");
    EXIF_DIRECTORY_GPS = new TiffDirectoryConstants.ExifDirectoryType.Special(-3, "GPS IFD");
    EXIF_DIRECTORY_UNKNOWN = null;
    ExifDirectoryType[] arrayOfExifDirectoryType = new ExifDirectoryType[14];
    arrayOfExifDirectoryType[0] = TIFF_DIRECTORY_ROOT;
    arrayOfExifDirectoryType[1] = EXIF_DIRECTORY_EXIF_IFD;
    arrayOfExifDirectoryType[2] = TIFF_DIRECTORY_IFD0;
    arrayOfExifDirectoryType[3] = EXIF_DIRECTORY_IFD0;
    arrayOfExifDirectoryType[4] = TIFF_DIRECTORY_IFD1;
    arrayOfExifDirectoryType[5] = EXIF_DIRECTORY_IFD1;
    arrayOfExifDirectoryType[6] = TIFF_DIRECTORY_IFD2;
    arrayOfExifDirectoryType[7] = EXIF_DIRECTORY_IFD2;
    arrayOfExifDirectoryType[8] = EXIF_DIRECTORY_INTEROP_IFD;
    arrayOfExifDirectoryType[9] = EXIF_DIRECTORY_MAKER_NOTES;
    arrayOfExifDirectoryType[10] = EXIF_DIRECTORY_SUB_IFD;
    arrayOfExifDirectoryType[11] = EXIF_DIRECTORY_SUB_IFD1;
    arrayOfExifDirectoryType[12] = EXIF_DIRECTORY_SUB_IFD2;
    arrayOfExifDirectoryType[13] = EXIF_DIRECTORY_GPS;
    EXIF_DIRECTORIES = arrayOfExifDirectoryType;
  }

  public static abstract class ExifDirectoryType
  {
    public final int directoryType;
    public final String name;

    public ExifDirectoryType(int paramInt, String paramString)
    {
      this.directoryType = paramInt;
      this.name = paramString;
    }

    public abstract boolean isImageDirectory();

    public static class Image extends TiffDirectoryConstants.ExifDirectoryType
    {
      public Image(int paramInt, String paramString)
      {
        super(paramString);
      }

      public boolean isImageDirectory()
      {
        return true;
      }
    }

    public static class Special extends TiffDirectoryConstants.ExifDirectoryType
    {
      public Special(int paramInt, String paramString)
      {
        super(paramString);
      }

      public boolean isImageDirectory()
      {
        return false;
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.constants.TiffDirectoryConstants
 * JD-Core Version:    0.6.0
 */