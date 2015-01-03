package com.google.zxing.oned.rss.expanded.decoders;

final class BlockParsedResult
{
  private final DecodedInformation decodedInformation;
  private final boolean finished;

  BlockParsedResult()
  {
    this.finished = true;
    this.decodedInformation = null;
  }

  BlockParsedResult(DecodedInformation paramDecodedInformation, boolean paramBoolean)
  {
    this.finished = paramBoolean;
    this.decodedInformation = paramDecodedInformation;
  }

  BlockParsedResult(boolean paramBoolean)
  {
    this.finished = paramBoolean;
    this.decodedInformation = null;
  }

  DecodedInformation getDecodedInformation()
  {
    return this.decodedInformation;
  }

  boolean isFinished()
  {
    return this.finished;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.rss.expanded.decoders.BlockParsedResult
 * JD-Core Version:    0.6.0
 */