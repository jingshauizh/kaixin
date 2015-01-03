package org.apache.sanselan.formats.tiff.constants;

import org.apache.sanselan.SanselanConstants;

public abstract interface AllTagConstants extends SanselanConstants, TiffTagConstants, ExifTagConstants, GPSTagConstants
{
  public static final TagInfo[] ALL_TAGS;

  static
  {
    TagInfo[][] arrayOfTagInfo; = new TagInfo[3][];
    arrayOfTagInfo;[0] = ALL_TIFF_TAGS;
    arrayOfTagInfo;[1] = ALL_EXIF_TAGS;
    arrayOfTagInfo;[2] = ALL_GPS_TAGS;
    ALL_TAGS = TagConstantsUtils.mergeTagLists(arrayOfTagInfo;);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.constants.AllTagConstants
 * JD-Core Version:    0.6.0
 */