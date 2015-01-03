package org.apache.sanselan.formats.tiff.constants;

public abstract interface GPSTagConstants extends TiffDirectoryConstants, TiffFieldTypeConstants
{
  public static final TagInfo[] ALL_GPS_TAGS;
  public static final TagInfo GPS_TAG_GPS_ALTITUDE;
  public static final TagInfo GPS_TAG_GPS_ALTITUDE_REF;
  public static final int GPS_TAG_GPS_ALTITUDE_REF_VALUE_ABOVE_SEA_LEVEL = 0;
  public static final int GPS_TAG_GPS_ALTITUDE_REF_VALUE_BELOW_SEA_LEVEL = 1;
  public static final TagInfo GPS_TAG_GPS_AREA_INFORMATION;
  public static final TagInfo GPS_TAG_GPS_DATE_STAMP;
  public static final TagInfo GPS_TAG_GPS_DEST_BEARING;
  public static final TagInfo GPS_TAG_GPS_DEST_BEARING_REF;
  public static final String GPS_TAG_GPS_DEST_BEARING_REF_VALUE_MAGNETIC_NORTH = "M";
  public static final String GPS_TAG_GPS_DEST_BEARING_REF_VALUE_TRUE_NORTH = "T";
  public static final TagInfo GPS_TAG_GPS_DEST_DISTANCE;
  public static final TagInfo GPS_TAG_GPS_DEST_DISTANCE_REF;
  public static final String GPS_TAG_GPS_DEST_DISTANCE_REF_VALUE_KILOMETERS = "K";
  public static final String GPS_TAG_GPS_DEST_DISTANCE_REF_VALUE_MILES = "M";
  public static final String GPS_TAG_GPS_DEST_DISTANCE_REF_VALUE_NAUTICAL_MILES = "N";
  public static final TagInfo GPS_TAG_GPS_DEST_LATITUDE;
  public static final TagInfo GPS_TAG_GPS_DEST_LATITUDE_REF;
  public static final String GPS_TAG_GPS_DEST_LATITUDE_REF_VALUE_NORTH = "N";
  public static final String GPS_TAG_GPS_DEST_LATITUDE_REF_VALUE_SOUTH = "S";
  public static final TagInfo GPS_TAG_GPS_DEST_LONGITUDE;
  public static final TagInfo GPS_TAG_GPS_DEST_LONGITUDE_REF;
  public static final String GPS_TAG_GPS_DEST_LONGITUDE_REF_VALUE_EAST = "E";
  public static final String GPS_TAG_GPS_DEST_LONGITUDE_REF_VALUE_WEST = "W";
  public static final TagInfo GPS_TAG_GPS_DIFFERENTIAL;
  public static final int GPS_TAG_GPS_DIFFERENTIAL_VALUE_DIFFERENTIAL_CORRECTED = 1;
  public static final int GPS_TAG_GPS_DIFFERENTIAL_VALUE_NO_CORRECTION = 0;
  public static final TagInfo GPS_TAG_GPS_DOP;
  public static final TagInfo GPS_TAG_GPS_IMG_DIRECTION;
  public static final TagInfo GPS_TAG_GPS_IMG_DIRECTION_REF;
  public static final String GPS_TAG_GPS_IMG_DIRECTION_REF_VALUE_MAGNETIC_NORTH = "M";
  public static final String GPS_TAG_GPS_IMG_DIRECTION_REF_VALUE_TRUE_NORTH = "T";
  public static final TagInfo GPS_TAG_GPS_LATITUDE;
  public static final TagInfo GPS_TAG_GPS_LATITUDE_REF;
  public static final String GPS_TAG_GPS_LATITUDE_REF_VALUE_NORTH = "N";
  public static final String GPS_TAG_GPS_LATITUDE_REF_VALUE_SOUTH = "S";
  public static final TagInfo GPS_TAG_GPS_LONGITUDE;
  public static final TagInfo GPS_TAG_GPS_LONGITUDE_REF;
  public static final String GPS_TAG_GPS_LONGITUDE_REF_VALUE_EAST = "E";
  public static final String GPS_TAG_GPS_LONGITUDE_REF_VALUE_WEST = "W";
  public static final TagInfo GPS_TAG_GPS_MAP_DATUM;
  public static final TagInfo GPS_TAG_GPS_MEASURE_MODE;
  public static final int GPS_TAG_GPS_MEASURE_MODE_VALUE_2_DIMENSIONAL_MEASUREMENT = 2;
  public static final int GPS_TAG_GPS_MEASURE_MODE_VALUE_3_DIMENSIONAL_MEASUREMENT = 3;
  public static final TagInfo GPS_TAG_GPS_PROCESSING_METHOD;
  public static final TagInfo GPS_TAG_GPS_SATELLITES;
  public static final TagInfo GPS_TAG_GPS_SPEED;
  public static final TagInfo GPS_TAG_GPS_SPEED_REF;
  public static final String GPS_TAG_GPS_SPEED_REF_VALUE_KMPH = "K";
  public static final String GPS_TAG_GPS_SPEED_REF_VALUE_KNOTS = "N";
  public static final String GPS_TAG_GPS_SPEED_REF_VALUE_MPH = "M";
  public static final TagInfo GPS_TAG_GPS_STATUS;
  public static final String GPS_TAG_GPS_STATUS_VALUE_MEASUREMENT_INTEROPERABILITY = "V";
  public static final String GPS_TAG_GPS_STATUS_VALUE_MEASUREMENT_IN_PROGRESS = "A";
  public static final TagInfo GPS_TAG_GPS_TIME_STAMP;
  public static final TagInfo GPS_TAG_GPS_TRACK;
  public static final TagInfo GPS_TAG_GPS_TRACK_REF;
  public static final String GPS_TAG_GPS_TRACK_REF_VALUE_MAGNETIC_NORTH = "M";
  public static final String GPS_TAG_GPS_TRACK_REF_VALUE_TRUE_NORTH = "T";
  public static final TagInfo GPS_TAG_GPS_VERSION_ID = new TagInfo("GPS Version ID", 0, FIELD_TYPE_DESCRIPTION_BYTE, 4, EXIF_DIRECTORY_GPS);

  static
  {
    GPS_TAG_GPS_LATITUDE_REF = new TagInfo("GPS Latitude Ref", 1, FIELD_TYPE_DESCRIPTION_ASCII, 2, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_LATITUDE = new TagInfo("GPS Latitude", 2, FIELD_TYPE_DESCRIPTION_RATIONAL, 3, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_LONGITUDE_REF = new TagInfo("GPS Longitude Ref", 3, FIELD_TYPE_DESCRIPTION_ASCII, 2, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_LONGITUDE = new TagInfo("GPS Longitude", 4, FIELD_TYPE_DESCRIPTION_RATIONAL, 3, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_ALTITUDE_REF = new TagInfo("GPS Altitude Ref", 5, FIELD_TYPE_DESCRIPTION_BYTE, -1, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_ALTITUDE = new TagInfo("GPS Altitude", 6, FIELD_TYPE_DESCRIPTION_RATIONAL, -1, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_TIME_STAMP = new TagInfo("GPS Time Stamp", 7, FIELD_TYPE_DESCRIPTION_RATIONAL, 3, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_SATELLITES = new TagInfo("GPS Satellites", 8, FIELD_TYPE_DESCRIPTION_ASCII, -1, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_STATUS = new TagInfo("GPS Status", 9, FIELD_TYPE_DESCRIPTION_ASCII, 2, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_MEASURE_MODE = new TagInfo("GPS Measure Mode", 10, FIELD_TYPE_DESCRIPTION_ASCII, 2, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_DOP = new TagInfo("GPS DOP", 11, FIELD_TYPE_DESCRIPTION_RATIONAL, -1, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_SPEED_REF = new TagInfo("GPS Speed Ref", 12, FIELD_TYPE_DESCRIPTION_ASCII, 2, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_SPEED = new TagInfo("GPS Speed", 13, FIELD_TYPE_DESCRIPTION_RATIONAL, -1, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_TRACK_REF = new TagInfo("GPS Track Ref", 14, FIELD_TYPE_DESCRIPTION_ASCII, 2, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_TRACK = new TagInfo("GPS Track", 15, FIELD_TYPE_DESCRIPTION_RATIONAL, -1, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_IMG_DIRECTION_REF = new TagInfo("GPS Img Direction Ref", 16, FIELD_TYPE_DESCRIPTION_ASCII, 2, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_IMG_DIRECTION = new TagInfo("GPS Img Direction", 17, FIELD_TYPE_DESCRIPTION_RATIONAL, -1, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_MAP_DATUM = new TagInfo("GPS Map Datum", 18, FIELD_TYPE_DESCRIPTION_ASCII, -1, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_DEST_LATITUDE_REF = new TagInfo("GPS Dest Latitude Ref", 19, FIELD_TYPE_DESCRIPTION_ASCII, 2, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_DEST_LATITUDE = new TagInfo("GPS Dest Latitude", 20, FIELD_TYPE_DESCRIPTION_RATIONAL, 3, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_DEST_LONGITUDE_REF = new TagInfo("GPS Dest Longitude Ref", 21, FIELD_TYPE_DESCRIPTION_ASCII, 2, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_DEST_LONGITUDE = new TagInfo("GPS Dest Longitude", 22, FIELD_TYPE_DESCRIPTION_RATIONAL, 3, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_DEST_BEARING_REF = new TagInfo("GPS Dest Bearing Ref", 23, FIELD_TYPE_DESCRIPTION_ASCII, 2, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_DEST_BEARING = new TagInfo("GPS Dest Bearing", 24, FIELD_TYPE_DESCRIPTION_RATIONAL, -1, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_DEST_DISTANCE_REF = new TagInfo("GPS Dest Distance Ref", 25, FIELD_TYPE_DESCRIPTION_ASCII, 2, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_DEST_DISTANCE = new TagInfo("GPS Dest Distance", 26, FIELD_TYPE_DESCRIPTION_RATIONAL, -1, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_PROCESSING_METHOD = new TagInfo.Text("GPS Processing Method", 27, FIELD_TYPE_DESCRIPTION_UNKNOWN, -1, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_AREA_INFORMATION = new TagInfo.Text("GPS Area Information", 28, FIELD_TYPE_DESCRIPTION_UNKNOWN, -1, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_DATE_STAMP = new TagInfo("GPS Date Stamp", 29, FIELD_TYPE_DESCRIPTION_ASCII, 11, EXIF_DIRECTORY_GPS);
    GPS_TAG_GPS_DIFFERENTIAL = new TagInfo("GPS Differential", 30, FIELD_TYPE_DESCRIPTION_SHORT, -1, EXIF_DIRECTORY_GPS);
    TagInfo[] arrayOfTagInfo = new TagInfo[31];
    arrayOfTagInfo[0] = GPS_TAG_GPS_VERSION_ID;
    arrayOfTagInfo[1] = GPS_TAG_GPS_LATITUDE_REF;
    arrayOfTagInfo[2] = GPS_TAG_GPS_LATITUDE;
    arrayOfTagInfo[3] = GPS_TAG_GPS_LONGITUDE_REF;
    arrayOfTagInfo[4] = GPS_TAG_GPS_LONGITUDE;
    arrayOfTagInfo[5] = GPS_TAG_GPS_ALTITUDE_REF;
    arrayOfTagInfo[6] = GPS_TAG_GPS_ALTITUDE;
    arrayOfTagInfo[7] = GPS_TAG_GPS_TIME_STAMP;
    arrayOfTagInfo[8] = GPS_TAG_GPS_SATELLITES;
    arrayOfTagInfo[9] = GPS_TAG_GPS_STATUS;
    arrayOfTagInfo[10] = GPS_TAG_GPS_MEASURE_MODE;
    arrayOfTagInfo[11] = GPS_TAG_GPS_DOP;
    arrayOfTagInfo[12] = GPS_TAG_GPS_SPEED_REF;
    arrayOfTagInfo[13] = GPS_TAG_GPS_SPEED;
    arrayOfTagInfo[14] = GPS_TAG_GPS_TRACK_REF;
    arrayOfTagInfo[15] = GPS_TAG_GPS_TRACK;
    arrayOfTagInfo[16] = GPS_TAG_GPS_IMG_DIRECTION_REF;
    arrayOfTagInfo[17] = GPS_TAG_GPS_IMG_DIRECTION;
    arrayOfTagInfo[18] = GPS_TAG_GPS_MAP_DATUM;
    arrayOfTagInfo[19] = GPS_TAG_GPS_DEST_LATITUDE_REF;
    arrayOfTagInfo[20] = GPS_TAG_GPS_DEST_LATITUDE;
    arrayOfTagInfo[21] = GPS_TAG_GPS_DEST_LONGITUDE_REF;
    arrayOfTagInfo[22] = GPS_TAG_GPS_DEST_LONGITUDE;
    arrayOfTagInfo[23] = GPS_TAG_GPS_DEST_BEARING_REF;
    arrayOfTagInfo[24] = GPS_TAG_GPS_DEST_BEARING;
    arrayOfTagInfo[25] = GPS_TAG_GPS_DEST_DISTANCE_REF;
    arrayOfTagInfo[26] = GPS_TAG_GPS_DEST_DISTANCE;
    arrayOfTagInfo[27] = GPS_TAG_GPS_PROCESSING_METHOD;
    arrayOfTagInfo[28] = GPS_TAG_GPS_AREA_INFORMATION;
    arrayOfTagInfo[29] = GPS_TAG_GPS_DATE_STAMP;
    arrayOfTagInfo[30] = GPS_TAG_GPS_DIFFERENTIAL;
    ALL_GPS_TAGS = arrayOfTagInfo;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.constants.GPSTagConstants
 * JD-Core Version:    0.6.0
 */