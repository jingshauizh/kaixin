package org.apache.sanselan.formats.tiff.write;

import java.util.ArrayList;
import java.util.List;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;
import org.apache.sanselan.util.Debug;

public final class TiffOutputSet
  implements TiffConstants
{
  private static final String newline = System.getProperty("line.separator");
  public final int byteOrder;
  private final ArrayList directories = new ArrayList();

  public TiffOutputSet()
  {
    this(73);
  }

  public TiffOutputSet(int paramInt)
  {
    this.byteOrder = paramInt;
  }

  public void addDirectory(TiffOutputDirectory paramTiffOutputDirectory)
    throws ImageWriteException
  {
    if (findDirectory(paramTiffOutputDirectory.type) != null)
      throw new ImageWriteException("Output set already contains a directory of that type.");
    this.directories.add(paramTiffOutputDirectory);
  }

  public TiffOutputDirectory addExifDirectory()
    throws ImageWriteException
  {
    TiffOutputDirectory localTiffOutputDirectory = new TiffOutputDirectory(-2);
    addDirectory(localTiffOutputDirectory);
    return localTiffOutputDirectory;
  }

  public TiffOutputDirectory addGPSDirectory()
    throws ImageWriteException
  {
    TiffOutputDirectory localTiffOutputDirectory = new TiffOutputDirectory(-3);
    addDirectory(localTiffOutputDirectory);
    return localTiffOutputDirectory;
  }

  public TiffOutputDirectory addInteroperabilityDirectory()
    throws ImageWriteException
  {
    getOrCreateExifDirectory();
    TiffOutputDirectory localTiffOutputDirectory = new TiffOutputDirectory(-4);
    addDirectory(localTiffOutputDirectory);
    return localTiffOutputDirectory;
  }

  public TiffOutputDirectory addRootDirectory()
    throws ImageWriteException
  {
    TiffOutputDirectory localTiffOutputDirectory = new TiffOutputDirectory(0);
    addDirectory(localTiffOutputDirectory);
    return localTiffOutputDirectory;
  }

  public void dump()
  {
    Debug.debug(toString());
  }

  public TiffOutputDirectory findDirectory(int paramInt)
  {
    for (int i = 0; ; i++)
    {
      TiffOutputDirectory localTiffOutputDirectory;
      if (i >= this.directories.size())
        localTiffOutputDirectory = null;
      do
      {
        return localTiffOutputDirectory;
        localTiffOutputDirectory = (TiffOutputDirectory)this.directories.get(i);
      }
      while (localTiffOutputDirectory.type == paramInt);
    }
  }

  public TiffOutputField findField(int paramInt)
  {
    for (int i = 0; ; i++)
    {
      TiffOutputField localTiffOutputField;
      if (i >= this.directories.size())
        localTiffOutputField = null;
      do
      {
        return localTiffOutputField;
        localTiffOutputField = ((TiffOutputDirectory)this.directories.get(i)).findField(paramInt);
      }
      while (localTiffOutputField != null);
    }
  }

  public TiffOutputField findField(TagInfo paramTagInfo)
  {
    return findField(paramTagInfo.tag);
  }

  public List getDirectories()
  {
    return new ArrayList(this.directories);
  }

  public TiffOutputDirectory getExifDirectory()
  {
    return findDirectory(-2);
  }

  public TiffOutputDirectory getGPSDirectory()
  {
    return findDirectory(-3);
  }

  public TiffOutputDirectory getInteroperabilityDirectory()
  {
    return findDirectory(-4);
  }

  public TiffOutputDirectory getOrCreateExifDirectory()
    throws ImageWriteException
  {
    getOrCreateRootDirectory();
    TiffOutputDirectory localTiffOutputDirectory = findDirectory(-2);
    if (localTiffOutputDirectory != null)
      return localTiffOutputDirectory;
    return addExifDirectory();
  }

  public TiffOutputDirectory getOrCreateGPSDirectory()
    throws ImageWriteException
  {
    getOrCreateExifDirectory();
    TiffOutputDirectory localTiffOutputDirectory = findDirectory(-3);
    if (localTiffOutputDirectory != null)
      return localTiffOutputDirectory;
    return addGPSDirectory();
  }

  public TiffOutputDirectory getOrCreateRootDirectory()
    throws ImageWriteException
  {
    TiffOutputDirectory localTiffOutputDirectory = findDirectory(0);
    if (localTiffOutputDirectory != null)
      return localTiffOutputDirectory;
    return addRootDirectory();
  }

  protected List getOutputItems(TiffOutputSummary paramTiffOutputSummary)
    throws ImageWriteException
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; ; i++)
    {
      if (i >= this.directories.size())
        return localArrayList;
      localArrayList.addAll(((TiffOutputDirectory)this.directories.get(i)).getOutputItems(paramTiffOutputSummary));
    }
  }

  public TiffOutputDirectory getRootDirectory()
  {
    return findDirectory(0);
  }

  public void removeField(int paramInt)
  {
    for (int i = 0; ; i++)
    {
      if (i >= this.directories.size())
        return;
      ((TiffOutputDirectory)this.directories.get(i)).removeField(paramInt);
    }
  }

  public void removeField(TagInfo paramTagInfo)
  {
    removeField(paramTagInfo.tag);
  }

  public void setGPSInDegrees(double paramDouble1, double paramDouble2)
    throws ImageWriteException
  {
    TiffOutputDirectory localTiffOutputDirectory = getOrCreateGPSDirectory();
    String str1;
    double d1;
    if (paramDouble1 < 0.0D)
    {
      str1 = "W";
      d1 = Math.abs(paramDouble1);
      if (paramDouble2 >= 0.0D)
        break label340;
    }
    label340: for (String str2 = "S"; ; str2 = "N")
    {
      double d2 = Math.abs(paramDouble2);
      TiffOutputField localTiffOutputField1 = TiffOutputField.create(TiffConstants.GPS_TAG_GPS_LONGITUDE_REF, this.byteOrder, str1);
      localTiffOutputDirectory.removeField(TiffConstants.GPS_TAG_GPS_LONGITUDE_REF);
      localTiffOutputDirectory.add(localTiffOutputField1);
      TiffOutputField localTiffOutputField2 = TiffOutputField.create(TiffConstants.GPS_TAG_GPS_LATITUDE_REF, this.byteOrder, str2);
      localTiffOutputDirectory.removeField(TiffConstants.GPS_TAG_GPS_LATITUDE_REF);
      localTiffOutputDirectory.add(localTiffOutputField2);
      double d3 = ()d1;
      double d4 = 60.0D * (d1 % 1.0D);
      double d5 = ()d4;
      double d6 = 60.0D * (d4 % 1.0D);
      Double[] arrayOfDouble1 = new Double[3];
      Double localDouble1 = new Double(d3);
      arrayOfDouble1[0] = localDouble1;
      Double localDouble2 = new Double(d5);
      arrayOfDouble1[1] = localDouble2;
      Double localDouble3 = new Double(d6);
      arrayOfDouble1[2] = localDouble3;
      TiffOutputField localTiffOutputField3 = TiffOutputField.create(TiffConstants.GPS_TAG_GPS_LONGITUDE, this.byteOrder, arrayOfDouble1);
      localTiffOutputDirectory.removeField(TiffConstants.GPS_TAG_GPS_LONGITUDE);
      localTiffOutputDirectory.add(localTiffOutputField3);
      double d7 = ()d2;
      double d8 = 60.0D * (d2 % 1.0D);
      double d9 = ()d8;
      double d10 = 60.0D * (d8 % 1.0D);
      Double[] arrayOfDouble2 = new Double[3];
      Double localDouble4 = new Double(d7);
      arrayOfDouble2[0] = localDouble4;
      Double localDouble5 = new Double(d9);
      arrayOfDouble2[1] = localDouble5;
      Double localDouble6 = new Double(d10);
      arrayOfDouble2[2] = localDouble6;
      TiffOutputField localTiffOutputField4 = TiffOutputField.create(TiffConstants.GPS_TAG_GPS_LATITUDE, this.byteOrder, arrayOfDouble2);
      localTiffOutputDirectory.removeField(TiffConstants.GPS_TAG_GPS_LATITUDE);
      localTiffOutputDirectory.add(localTiffOutputField4);
      return;
      str1 = "E";
      break;
    }
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
    localStringBuffer.append("TiffOutputSet {");
    localStringBuffer.append(newline);
    localStringBuffer.append(paramString);
    localStringBuffer.append("byteOrder: " + this.byteOrder);
    localStringBuffer.append(newline);
    int i = 0;
    if (i >= this.directories.size())
    {
      localStringBuffer.append(paramString);
      localStringBuffer.append("}");
      localStringBuffer.append(newline);
      return localStringBuffer.toString();
    }
    TiffOutputDirectory localTiffOutputDirectory = (TiffOutputDirectory)this.directories.get(i);
    localStringBuffer.append(paramString);
    localStringBuffer.append("\tdirectory " + i + ": " + localTiffOutputDirectory.description() + " (" + localTiffOutputDirectory.type + ")");
    localStringBuffer.append(newline);
    ArrayList localArrayList = localTiffOutputDirectory.getFields();
    for (int j = 0; ; j++)
    {
      if (j >= localArrayList.size())
      {
        i++;
        break;
      }
      TiffOutputField localTiffOutputField = (TiffOutputField)localArrayList.get(j);
      localStringBuffer.append(paramString);
      localStringBuffer.append("\t\tfield " + i + ": " + localTiffOutputField.tagInfo);
      localStringBuffer.append(newline);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.write.TiffOutputSet
 * JD-Core Version:    0.6.0
 */