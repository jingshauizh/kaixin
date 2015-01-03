package org.apache.sanselan.formats.tiff;

import java.util.ArrayList;
import java.util.Collections;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.util.Debug;

public class TiffContents
{
  public final ArrayList directories;
  public final TiffHeader header;

  public TiffContents(TiffHeader paramTiffHeader, ArrayList paramArrayList)
  {
    this.header = paramTiffHeader;
    this.directories = paramArrayList;
  }

  public void dissect(boolean paramBoolean)
    throws ImageReadException
  {
    ArrayList localArrayList = getElements();
    Collections.sort(localArrayList, TiffElement.COMPARATOR);
    int i = 0;
    for (int j = 0; ; j++)
    {
      if (j >= localArrayList.size())
      {
        Debug.debug("end: " + i);
        Debug.debug();
        return;
      }
      TiffElement localTiffElement = (TiffElement)localArrayList.get(j);
      if (localTiffElement.offset > i)
        Debug.debug("\tgap: " + (localTiffElement.offset - i));
      if (localTiffElement.offset < i)
        Debug.debug("\toverlap");
      Debug.debug("element, start: " + localTiffElement.offset + ", length: " + localTiffElement.length + ", end: " + (localTiffElement.offset + localTiffElement.length) + ": " + localTiffElement.getElementDescription(false));
      if (paramBoolean)
      {
        String str = localTiffElement.getElementDescription(true);
        if (str != null)
          Debug.debug(str);
      }
      i = localTiffElement.offset + localTiffElement.length;
    }
  }

  public TiffField findField(TagInfo paramTagInfo)
    throws ImageReadException
  {
    for (int i = 0; ; i++)
    {
      TiffField localTiffField;
      if (i >= this.directories.size())
        localTiffField = null;
      do
      {
        return localTiffField;
        localTiffField = ((TiffDirectory)this.directories.get(i)).findField(paramTagInfo);
      }
      while (localTiffField != null);
    }
  }

  public ArrayList getElements()
    throws ImageReadException
  {
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(this.header);
    int i = 0;
    if (i >= this.directories.size())
      return localArrayList1;
    TiffDirectory localTiffDirectory = (TiffDirectory)this.directories.get(i);
    localArrayList1.add(localTiffDirectory);
    ArrayList localArrayList2 = localTiffDirectory.entries;
    for (int j = 0; ; j++)
    {
      if (j >= localArrayList2.size())
      {
        if (localTiffDirectory.hasTiffImageData())
          localArrayList1.addAll(localTiffDirectory.getTiffRawImageDataElements());
        if (localTiffDirectory.hasJpegImageData())
          localArrayList1.add(localTiffDirectory.getJpegRawImageDataElement());
        i++;
        break;
      }
      TiffElement localTiffElement = ((TiffField)localArrayList2.get(j)).getOversizeValueElement();
      if (localTiffElement == null)
        continue;
      localArrayList1.add(localTiffElement);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.TiffContents
 * JD-Core Version:    0.6.0
 */