package org.apache.sanselan.formats.jpeg;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.TiffImageMetadata.Item;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.util.Debug;

public class JpegImageMetadata
  implements IImageMetadata
{
  private static final String newline = System.getProperty("line.separator");
  private final TiffImageMetadata exif;

  public JpegImageMetadata(Object paramObject, TiffImageMetadata paramTiffImageMetadata)
  {
    this.exif = paramTiffImageMetadata;
  }

  public void dump()
  {
    Debug.debug(toString());
  }

  public TiffField findEXIFValue(TagInfo paramTagInfo)
  {
    ArrayList localArrayList = getItems();
    int i = 0;
    if (i >= localArrayList.size())
      return null;
    Object localObject = localArrayList.get(i);
    if (!(localObject instanceof TiffImageMetadata.Item));
    TiffField localTiffField;
    do
    {
      i++;
      break;
      localTiffField = ((TiffImageMetadata.Item)localObject).getTiffField();
    }
    while (localTiffField.tag != paramTagInfo.tag);
    return localTiffField;
  }

  public Object getEXIFThumbnail()
    throws ImageReadException, IOException
  {
    return null;
  }

  public TiffImageMetadata getExif()
  {
    return this.exif;
  }

  public ArrayList getItems()
  {
    ArrayList localArrayList = new ArrayList();
    if (this.exif != null)
      localArrayList.addAll(this.exif.getItems());
    return localArrayList;
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
    localStringBuffer.append(paramString);
    if (this.exif == null)
      localStringBuffer.append("No Exif metadata.");
    while (true)
    {
      localStringBuffer.append(newline);
      localStringBuffer.append(paramString);
      localStringBuffer.append("No Photoshop (IPTC) metadata.");
      return localStringBuffer.toString();
      localStringBuffer.append("Exif metadata:");
      localStringBuffer.append(newline);
      localStringBuffer.append(this.exif.toString("\t"));
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.jpeg.JpegImageMetadata
 * JD-Core Version:    0.6.0
 */