package org.apache.sanselan.formats.tiff.constants;

public class TagConstantsUtils
  implements TiffDirectoryConstants
{
  public static TiffDirectoryConstants.ExifDirectoryType getExifDirectoryType(int paramInt)
  {
    for (int i = 0; ; i++)
    {
      if (i >= EXIF_DIRECTORIES.length)
        return EXIF_DIRECTORY_UNKNOWN;
      if (EXIF_DIRECTORIES[i].directoryType == paramInt)
        return EXIF_DIRECTORIES[i];
    }
  }

  public static TagInfo[] mergeTagLists(TagInfo[][] paramArrayOfTagInfo)
  {
    int i = 0;
    int j = 0;
    TagInfo[] arrayOfTagInfo;
    int k;
    if (j >= paramArrayOfTagInfo.length)
    {
      arrayOfTagInfo = new TagInfo[i];
      k = 0;
    }
    for (int m = 0; ; m++)
    {
      if (m >= paramArrayOfTagInfo.length)
      {
        return arrayOfTagInfo;
        i += paramArrayOfTagInfo[j].length;
        j++;
        break;
      }
      System.arraycopy(paramArrayOfTagInfo[m], 0, arrayOfTagInfo, k, paramArrayOfTagInfo[m].length);
      k += paramArrayOfTagInfo[m].length;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.constants.TagConstantsUtils
 * JD-Core Version:    0.6.0
 */