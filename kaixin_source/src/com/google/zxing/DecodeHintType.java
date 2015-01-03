package com.google.zxing;

public final class DecodeHintType
{
  public static final DecodeHintType ALLOWED_LENGTHS;
  public static final DecodeHintType ASSUME_CODE_39_CHECK_DIGIT;
  public static final DecodeHintType CHARACTER_SET;
  public static final DecodeHintType NEED_RESULT_POINT_CALLBACK;
  public static final DecodeHintType OTHER = new DecodeHintType();
  public static final DecodeHintType POSSIBLE_FORMATS;
  public static final DecodeHintType PURE_BARCODE = new DecodeHintType();
  public static final DecodeHintType TRY_HARDER;

  static
  {
    POSSIBLE_FORMATS = new DecodeHintType();
    TRY_HARDER = new DecodeHintType();
    CHARACTER_SET = new DecodeHintType();
    ALLOWED_LENGTHS = new DecodeHintType();
    ASSUME_CODE_39_CHECK_DIGIT = new DecodeHintType();
    NEED_RESULT_POINT_CALLBACK = new DecodeHintType();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.DecodeHintType
 * JD-Core Version:    0.6.0
 */