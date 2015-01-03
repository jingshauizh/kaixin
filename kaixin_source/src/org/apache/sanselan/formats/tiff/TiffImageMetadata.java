package org.apache.sanselan.formats.tiff;

import java.util.ArrayList;
import java.util.List;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.common.IImageMetadata.IImageMetadataItem;
import org.apache.sanselan.common.ImageMetadata;
import org.apache.sanselan.common.ImageMetadata.Item;
import org.apache.sanselan.common.RationalNumber;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.formats.tiff.constants.TagInfo.Offset;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;
import org.apache.sanselan.formats.tiff.constants.TiffDirectoryConstants;
import org.apache.sanselan.formats.tiff.fieldtypes.FieldType;
import org.apache.sanselan.formats.tiff.write.TiffOutputDirectory;
import org.apache.sanselan.formats.tiff.write.TiffOutputField;
import org.apache.sanselan.formats.tiff.write.TiffOutputSet;

public class TiffImageMetadata extends ImageMetadata
  implements TiffDirectoryConstants
{
  public final TiffContents contents;

  public TiffImageMetadata(TiffContents paramTiffContents)
  {
    this.contents = paramTiffContents;
  }

  public TiffDirectory findDirectory(int paramInt)
  {
    ArrayList localArrayList = getDirectories();
    for (int i = 0; ; i++)
    {
      if (i >= localArrayList.size())
        return null;
      Directory localDirectory = (Directory)localArrayList.get(i);
      if (localDirectory.type == paramInt)
        return localDirectory.directory;
    }
  }

  public TiffField findField(TagInfo paramTagInfo)
    throws ImageReadException
  {
    ArrayList localArrayList = getDirectories();
    for (int i = 0; ; i++)
    {
      TiffField localTiffField;
      if (i >= localArrayList.size())
        localTiffField = null;
      do
      {
        return localTiffField;
        localTiffField = ((Directory)localArrayList.get(i)).findField(paramTagInfo);
      }
      while (localTiffField != null);
    }
  }

  public List getAllFields()
    throws ImageReadException
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = getDirectories();
    for (int i = 0; ; i++)
    {
      if (i >= localArrayList2.size())
        return localArrayList1;
      localArrayList1.addAll(((Directory)localArrayList2.get(i)).getAllFields());
    }
  }

  public ArrayList getDirectories()
  {
    return super.getItems();
  }

  public GPSInfo getGPS()
    throws ImageReadException
  {
    TiffDirectory localTiffDirectory = findDirectory(-3);
    if (localTiffDirectory == null)
      return null;
    TiffField localTiffField1 = localTiffDirectory.findField(TiffConstants.GPS_TAG_GPS_LATITUDE_REF);
    TiffField localTiffField2 = localTiffDirectory.findField(TiffConstants.GPS_TAG_GPS_LATITUDE);
    TiffField localTiffField3 = localTiffDirectory.findField(TiffConstants.GPS_TAG_GPS_LONGITUDE_REF);
    TiffField localTiffField4 = localTiffDirectory.findField(TiffConstants.GPS_TAG_GPS_LONGITUDE);
    if ((localTiffField1 == null) || (localTiffField2 == null) || (localTiffField3 == null) || (localTiffField4 == null))
      return null;
    String str1 = localTiffField1.getStringValue();
    RationalNumber[] arrayOfRationalNumber1 = (RationalNumber[])localTiffField2.getValue();
    String str2 = localTiffField3.getStringValue();
    RationalNumber[] arrayOfRationalNumber2 = (RationalNumber[])localTiffField4.getValue();
    if ((arrayOfRationalNumber1.length != 3) || (arrayOfRationalNumber2.length != 3))
      throw new ImageReadException("Expected three values for latitude and longitude.");
    return new GPSInfo(str1, str2, arrayOfRationalNumber1[0], arrayOfRationalNumber1[1], arrayOfRationalNumber1[2], arrayOfRationalNumber2[0], arrayOfRationalNumber2[1], arrayOfRationalNumber2[2]);
  }

  public ArrayList getItems()
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = super.getItems();
    for (int i = 0; ; i++)
    {
      if (i >= localArrayList2.size())
        return localArrayList1;
      localArrayList1.addAll(((Directory)localArrayList2.get(i)).getItems());
    }
  }

  public TiffOutputSet getOutputSet()
    throws ImageWriteException
  {
    int i = this.contents.header.byteOrder;
    TiffOutputSet localTiffOutputSet = new TiffOutputSet(i);
    ArrayList localArrayList = getDirectories();
    int j = 0;
    if (j >= localArrayList.size())
      return localTiffOutputSet;
    Directory localDirectory = (Directory)localArrayList.get(j);
    if (localTiffOutputSet.findDirectory(localDirectory.type) != null);
    while (true)
    {
      j++;
      break;
      localTiffOutputSet.addDirectory(localDirectory.getOutputDirectory(i));
    }
  }

  public static class Directory extends ImageMetadata
    implements IImageMetadata.IImageMetadataItem
  {
    private final TiffDirectory directory;
    public final int type;

    public Directory(TiffDirectory paramTiffDirectory)
    {
      this.type = paramTiffDirectory.type;
      this.directory = paramTiffDirectory;
    }

    public void add(TiffField paramTiffField)
    {
      add(new TiffImageMetadata.Item(paramTiffField));
    }

    public TiffField findField(TagInfo paramTagInfo)
      throws ImageReadException
    {
      return this.directory.findField(paramTagInfo);
    }

    public List getAllFields()
      throws ImageReadException
    {
      return this.directory.getDirectoryEntrys();
    }

    public JpegImageData getJpegImageData()
    {
      return this.directory.getJpegImageData();
    }

    public TiffOutputDirectory getOutputDirectory(int paramInt)
      throws ImageWriteException
    {
      while (true)
      {
        int i;
        try
        {
          TiffOutputDirectory localTiffOutputDirectory = new TiffOutputDirectory(this.type);
          ArrayList localArrayList = getItems();
          i = 0;
          if (i < localArrayList.size())
            continue;
          localTiffOutputDirectory.setJpegImageData(getJpegImageData());
          return localTiffOutputDirectory;
          TiffField localTiffField = ((TiffImageMetadata.Item)localArrayList.get(i)).getTiffField();
          if ((localTiffOutputDirectory.findField(localTiffField.tag) == null) && (!(localTiffField.tagInfo instanceof TagInfo.Offset)))
          {
            TagInfo localTagInfo = localTiffField.tagInfo;
            FieldType localFieldType = localTiffField.fieldType;
            int j = localTiffField.length;
            byte[] arrayOfByte = localTagInfo.encodeValue(localFieldType, localTiffField.getValue(), paramInt);
            TiffOutputField localTiffOutputField = new TiffOutputField(localTiffField.tag, localTagInfo, localFieldType, j, arrayOfByte);
            localTiffOutputField.setSortHint(localTiffField.getSortHint());
            localTiffOutputDirectory.add(localTiffOutputField);
          }
        }
        catch (ImageReadException localImageReadException)
        {
          throw new ImageWriteException(localImageReadException.getMessage(), localImageReadException);
        }
        i++;
      }
    }

    public String toString(String paramString)
    {
      String str1;
      StringBuilder localStringBuilder;
      if (paramString != null)
      {
        str1 = paramString;
        localStringBuilder = new StringBuilder(String.valueOf(str1)).append(this.directory.description()).append(": ");
        if (getJpegImageData() == null)
          break label78;
      }
      label78: for (String str2 = " (jpegImageData)"; ; str2 = "")
      {
        return str2 + "\n" + super.toString(paramString) + "\n";
        str1 = "";
        break;
      }
    }
  }

  public static class GPSInfo
  {
    public final RationalNumber latitudeDegrees;
    public final RationalNumber latitudeMinutes;
    public final String latitudeRef;
    public final RationalNumber latitudeSeconds;
    public final RationalNumber longitudeDegrees;
    public final RationalNumber longitudeMinutes;
    public final String longitudeRef;
    public final RationalNumber longitudeSeconds;

    public GPSInfo(String paramString1, String paramString2, RationalNumber paramRationalNumber1, RationalNumber paramRationalNumber2, RationalNumber paramRationalNumber3, RationalNumber paramRationalNumber4, RationalNumber paramRationalNumber5, RationalNumber paramRationalNumber6)
    {
      this.latitudeRef = paramString1;
      this.longitudeRef = paramString2;
      this.latitudeDegrees = paramRationalNumber1;
      this.latitudeMinutes = paramRationalNumber2;
      this.latitudeSeconds = paramRationalNumber3;
      this.longitudeDegrees = paramRationalNumber4;
      this.longitudeMinutes = paramRationalNumber5;
      this.longitudeSeconds = paramRationalNumber6;
    }

    public double getLatitudeAsDegreesNorth()
      throws ImageReadException
    {
      double d = this.latitudeDegrees.doubleValue() + this.latitudeMinutes.doubleValue() / 60.0D + this.latitudeSeconds.doubleValue() / 3600.0D;
      if (this.latitudeRef.trim().equalsIgnoreCase("n"))
        return d;
      if (this.latitudeRef.trim().equalsIgnoreCase("s"))
        return -d;
      throw new ImageReadException("Unknown latitude ref: \"" + this.latitudeRef + "\"");
    }

    public double getLongitudeAsDegreesEast()
      throws ImageReadException
    {
      double d = this.longitudeDegrees.doubleValue() + this.longitudeMinutes.doubleValue() / 60.0D + this.longitudeSeconds.doubleValue() / 3600.0D;
      if (this.longitudeRef.trim().equalsIgnoreCase("e"))
        return d;
      if (this.longitudeRef.trim().equalsIgnoreCase("w"))
        return -d;
      throw new ImageReadException("Unknown longitude ref: \"" + this.longitudeRef + "\"");
    }

    public String toString()
    {
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("[GPS. ");
      localStringBuffer.append("Latitude: " + this.latitudeDegrees.toDisplayString() + " degrees, " + this.latitudeMinutes.toDisplayString() + " minutes, " + this.latitudeSeconds.toDisplayString() + " seconds " + this.latitudeRef);
      localStringBuffer.append(", Longitude: " + this.longitudeDegrees.toDisplayString() + " degrees, " + this.longitudeMinutes.toDisplayString() + " minutes, " + this.longitudeSeconds.toDisplayString() + " seconds " + this.longitudeRef);
      localStringBuffer.append("]");
      return localStringBuffer.toString();
    }
  }

  public static class Item extends ImageMetadata.Item
  {
    private final TiffField entry;

    public Item(TiffField paramTiffField)
    {
      super(paramTiffField.getValueDescription());
      this.entry = paramTiffField;
    }

    public TiffField getTiffField()
    {
      return this.entry;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.TiffImageMetadata
 * JD-Core Version:    0.6.0
 */