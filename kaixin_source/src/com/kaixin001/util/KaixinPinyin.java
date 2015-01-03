package com.kaixin001.util;

public class KaixinPinyin
{
  public static final String[] pins = { "a", "ai", "an", "ang", "ao", "b", "ba", "bai", "ban", "bang", "bao", "bei", "ben", "beng", "bi", "bian", "biao", "bie", "bin", "bing", "bo", "bu", "c", "ca", "cai", "can", "cang", "cao", "ce", "cen", "ceng", "cha", "chai", "chan", "chang", "chao", "che", "chen", "cheng", "chi", "chong", "chou", "chu", "chuai", "chuan", "chuang", "chui", "chun", "chuo", "ci", "cong", "cou", "cu", "cuan", "cui", "cun", "cuo", "d", "da", "dai", "dan", "dang", "dao", "de", "dei", "deng", "di", "dia", "dian", "diao", "die", "ding", "diu", "dong", "dou", "du", "duan", "dui", "dun", "duo", "e", "ei", "en", "eng", "er", "f", "fa", "fan", "fang", "fei", "fen", "feng", "fo", "fou", "fu", "g", "ga", "gai", "gan", "gang", "gao", "ge", "gei", "gen", "geng", "gong", "gou", "gu", "gua", "guai", "guan", "guang", "gui", "gun", "guo", "h", "ha", "hai", "han", "hang", "hao", "he", "hei", "hen", "heng", "hng", "hong", "hou", "hu", "hua", "huai", "huan", "huang", "hui", "hun", "huo", "i", "j", "ji", "jia", "jian", "jiang", "jiao", "jie", "jin", "jing", "jiong", "jiu", "ju", "juan", "jue", "jun", "k", "ka", "kai", "kan", "kang", "kao", "ke", "ken", "keng", "kong", "kou", "ku", "kua", "kuai", "kuan", "kuang", "kui", "kun", "kuo", "l", "la", "lai", "lan", "lang", "lao", "le", "lei", "leng", "li", "lia", "lian", "liang", "liao", "lie", "lin", "ling", "liu", "lo", "long", "lou", "lu", "lv", "lve", "luan", "lue", "lun", "luo", "m", "ma", "mai", "man", "mang", "mao", "me", "mei", "men", "meng", "mi", "mian", "miao", "mie", "min", "ming", "miu", "mo", "mou", "mu", "n", "na", "nai", "nan", "nang", "nao", "ne", "nei", "nen", "neng", "ng", "ni", "nian", "niang", "niao", "nie", "nin", "ning", "niu", "nong", "nou", "nu", "nv", "nve", "nuan", "nue", "nuo", "o", "ou", "p", "pa", "pai", "pan", "pang", "pao", "pei", "pen", "peng", "pi", "pian", "piao", "pie", "pin", "ping", "po", "pou", "pu", "q", "qi", "qia", "qian", "qiang", "qiao", "qie", "qin", "qing", "qiong", "qiu", "qu", "quan", "que", "qui", "qun", "r", "ran", "rang", "rao", "re", "ren", "reng", "ri", "rong", "rou", "ru", "ruan", "rui", "run", "ruo", "s", "sa", "sai", "san", "sang", "sao", "se", "sen", "seng", "sha", "shai", "shan", "shang", "shao", "she", "shei", "shen", "sheng", "shi", "shou", "shu", "shua", "shuai", "shuan", "shuang", "shui", "shun", "shuo", "si", "song", "sou", "su", "suan", "sui", "sun", "suo", "t", "ta", "tai", "tan", "tang", "tao", "te", "tei", "teng", "ti", "tian", "tiao", "tie", "ting", "tong", "tou", "tu", "tuan", "tui", "tun", "tuo", "u", "v", "w", "wa", "wai", "wan", "wang", "wei", "wen", "weng", "wo", "wu", "x", "xi", "xia", "xian", "xiang", "xiao", "xie", "xin", "xing", "xiong", "xiu", "xu", "xuan", "xue", "xun", "y", "ya", "yan", "yang", "yao", "ye", "yi", "yiao", "yin", "ying", "yo", "yong", "you", "yu", "yuan", "yue", "yun", "z", "za", "zai", "zan", "zang", "zao", "ze", "zei", "zen", "zeng", "zha", "zhai", "zhan", "zhang", "zhao", "zhe", "zhei", "zhen", "zheng", "zhi", "zhong", "zhou", "zhu", "zhua", "zhuai", "zhuan", "zhuang", "zhui", "zhun", "zhuo", "zi", "zong", "zou", "zu", "zuan", "zui", "zun", "zuo" };
  private short[] idx;

  // ERROR //
  KaixinPinyin(android.content.Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 892	java/lang/Object:<init>	()V
    //   4: aconst_null
    //   5: astore_2
    //   6: new 894	java/io/DataInputStream
    //   9: dup
    //   10: aload_1
    //   11: invokevirtual 900	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   14: ldc_w 901
    //   17: invokevirtual 907	android/content/res/Resources:openRawResource	(I)Ljava/io/InputStream;
    //   20: invokespecial 910	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
    //   23: astore_3
    //   24: aload_0
    //   25: aload_3
    //   26: invokevirtual 914	java/io/DataInputStream:available	()I
    //   29: iconst_2
    //   30: idiv
    //   31: newarray short
    //   33: putfield 916	com/kaixin001/util/KaixinPinyin:idx	[S
    //   36: iconst_0
    //   37: istore 6
    //   39: aload_0
    //   40: getfield 916	com/kaixin001/util/KaixinPinyin:idx	[S
    //   43: arraylength
    //   44: istore 7
    //   46: iload 6
    //   48: iload 7
    //   50: if_icmplt +8 -> 58
    //   53: aload_3
    //   54: invokestatic 922	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   57: return
    //   58: aload_0
    //   59: getfield 916	com/kaixin001/util/KaixinPinyin:idx	[S
    //   62: iload 6
    //   64: aload_3
    //   65: invokevirtual 926	java/io/DataInputStream:readShort	()S
    //   68: sastore
    //   69: iinc 6 1
    //   72: goto -33 -> 39
    //   75: astore 8
    //   77: aload_2
    //   78: invokestatic 922	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   81: return
    //   82: astore 5
    //   84: aload_2
    //   85: invokestatic 922	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   88: aload 5
    //   90: athrow
    //   91: astore 5
    //   93: aload_3
    //   94: astore_2
    //   95: goto -11 -> 84
    //   98: astore 4
    //   100: aload_3
    //   101: astore_2
    //   102: goto -25 -> 77
    //
    // Exception table:
    //   from	to	target	type
    //   6	24	75	java/lang/Exception
    //   6	24	82	finally
    //   24	36	91	finally
    //   39	46	91	finally
    //   58	69	91	finally
    //   24	36	98	java/lang/Exception
    //   39	46	98	java/lang/Exception
    //   58	69	98	java/lang/Exception
  }

  String get_pinyin(int paramInt1, int paramInt2)
  {
    if (paramInt1 != 0)
    {
      int i = 0xFFFF & paramInt1 >> 16;
      int j = this.idx[(paramInt2 + (1 + (65536 + i)))];
      return pins[(j >> 5)];
    }
    return null;
  }

  int get_pinyin_num(int paramInt)
  {
    return (0xFFFF & paramInt) >> 1;
  }

  String[] get_pinyins(char paramChar)
  {
    int i = get_special_code(paramChar);
    if (i != 0)
    {
      String[] arrayOfString = new String[get_pinyin_num(i)];
      for (int j = 0; ; j++)
      {
        if (j >= arrayOfString.length)
          return arrayOfString;
        arrayOfString[j] = get_pinyin(i, j);
      }
    }
    return null;
  }

  int get_special_code(char paramChar)
  {
    int i = 0xFFFF & this.idx[paramChar];
    if (i != 0)
      return i << 16 | this.idx[(65536 + i)];
    return 0;
  }

  boolean is_baijiaxing_for_char(char paramChar)
  {
    return is_baijiaxing_for_code(get_special_code(paramChar));
  }

  boolean is_baijiaxing_for_code(int paramInt)
  {
    return (paramInt & 0x1) != 0;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.KaixinPinyin
 * JD-Core Version:    0.6.0
 */