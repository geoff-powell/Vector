package com.greenmist.vector.new.keyword

/**
 * Created by geoffpowell on 11/19/17.
 */
object ColorKeywords {
    
    private val colorMap = HashMap<String, Int>()
    
    init {
        colorMap.put("aliceblue", -0xf0701)
        colorMap.put("antiquewhite", -0x51429)
        colorMap.put("aqua", -0xff0001)
        colorMap.put("aquamarine", -0x80002c)
        colorMap.put("azure", -0xf0001)
        colorMap.put("beige", -0xa0a24)
        colorMap.put("bisque", -0x1b3c)
        colorMap.put("black", -0x1000000)
        colorMap.put("blanchedalmond", -0x1433)
        colorMap.put("blue", -0xffff01)
        colorMap.put("blueviolet", -0x75d41e)
        colorMap.put("brown", -0x5ad5d6)
        colorMap.put("burlywood", -0x214779)
        colorMap.put("cadetblue", -0xa06160)
        colorMap.put("chartreuse", -0x800100)
        colorMap.put("chocolate", -0x2d96e2)
        colorMap.put("coral", -0x80b0)
        colorMap.put("cornflowerblue", -0x9b6a13)
        colorMap.put("cornsilk", -0x724)
        colorMap.put("crimson", -0x23ebc4)
        colorMap.put("cyan", -0xff0001)
        colorMap.put("darkblue", -0xffff75)
        colorMap.put("darkcyan", -0xff7475)
        colorMap.put("darkgoldenrod", -0x4779f5)
        colorMap.put("darkgray", -0x565657)
        colorMap.put("darkgrey", -0x565657)
        colorMap.put("darkgreen", -0xff9c00)
        colorMap.put("darkkhaki", -0x424895)
        colorMap.put("darkmagenta", -0x74ff75)
        colorMap.put("darkolivegreen", -0xaa94d1)
        colorMap.put("darkorange", -0x7400)
        colorMap.put("darkorchid", -0x66cd34)
        colorMap.put("darkred", -0x750000)
        colorMap.put("darksalmon", -0x166986)
        colorMap.put("darkseagreen", -0x704371)
        colorMap.put("darkslateblue", -0xb7c275)
        colorMap.put("darkslategray", -0xd0b0b1)
        colorMap.put("darkslategrey", -0xd0b0b1)
        colorMap.put("darkturquoise", -0xff312f)
        colorMap.put("darkviolet", -0x6bff2d)
        colorMap.put("deeppink", -0xeb6d)
        colorMap.put("deepskyblue", -0xff4001)
        colorMap.put("dimgray", -0x969697)
        colorMap.put("dimgrey", -0x969697)
        colorMap.put("dodgerblue", -0xe16f01)
        colorMap.put("firebrick", -0x4dddde)
        colorMap.put("floralwhite", -0x510)
        colorMap.put("forestgreen", -0xdd74de)
        colorMap.put("fuchsia", -0xff01)
        colorMap.put("gainsboro", -0x232324)
        colorMap.put("ghostwhite", -0x70701)
        colorMap.put("gold", -0x2900)
        colorMap.put("goldenrod", -0x255ae0)
        colorMap.put("gray", -0x7f7f80)
        colorMap.put("grey", -0x7f7f80)
        colorMap.put("green", -0xff8000)
        colorMap.put("greenyellow", -0x5200d1)
        colorMap.put("honeydew", -0xf0010)
        colorMap.put("hotpink", -0x964c)
        colorMap.put("indianred", -0x32a3a4)
        colorMap.put("indigo", -0xb4ff7e)
        colorMap.put("ivory", -0x10)
        colorMap.put("khaki", -0xf1974)
        colorMap.put("lavender", -0x191906)
        colorMap.put("lavenderblush", -0xf0b)
        colorMap.put("lawngreen", -0x830400)
        colorMap.put("lemonchiffon", -0x533)
        colorMap.put("lightblue", -0x52271a)
        colorMap.put("lightcoral", -0xf7f80)
        colorMap.put("lightcyan", -0x1f0001)
        colorMap.put("lightgoldenrodyellow", -0x5052e)
        colorMap.put("lightgray", -0x2c2c2d)
        colorMap.put("lightgrey", -0x2c2c2d)
        colorMap.put("lightgreen", -0x6f1170)
        colorMap.put("lightpink", -0x493f)
        colorMap.put("lightsalmon", -0x5f86)
        colorMap.put("lightseagreen", -0xdf4d56)
        colorMap.put("lightskyblue", -0x783106)
        colorMap.put("lightslategray", -0x887767)
        colorMap.put("lightslategrey", -0x887767)
        colorMap.put("lightsteelblue", -0x4f3b22)
        colorMap.put("lightyellow", -0x20)
        colorMap.put("lime", -0xff0100)
        colorMap.put("limegreen", -0xcd32ce)
        colorMap.put("linen", -0x50f1a)
        colorMap.put("magenta", -0xff01)
        colorMap.put("maroon", -0x800000)
        colorMap.put("mediumaquamarine", -0x993256)
        colorMap.put("mediumblue", -0xffff33)
        colorMap.put("mediumorchid", -0x45aa2d)
        colorMap.put("mediumpurple", -0x6c8f28)
        colorMap.put("mediumseagreen", -0xc34c8f)
        colorMap.put("mediumslateblue", -0x849712)
        colorMap.put("mediumspringgreen", -0xff0566)
        colorMap.put("mediumturquoise", -0xb72e34)
        colorMap.put("mediumvioletred", -0x38ea7b)
        colorMap.put("midnightblue", -0xe6e690)
        colorMap.put("mintcream", -0xa0006)
        colorMap.put("mistyrose", -0x1b1f)
        colorMap.put("moccasin", -0x1b4b)
        colorMap.put("navajowhite", -0x2153)
        colorMap.put("navy", -0xffff80)
        colorMap.put("oldlace", -0x20a1a)
        colorMap.put("olive", -0x7f8000)
        colorMap.put("olivedrab", -0x9471dd)
        colorMap.put("orange", -0x5b00)
        colorMap.put("orangered", -0xbb00)
        colorMap.put("orchid", -0x258f2a)
        colorMap.put("palegoldenrod", -0x111756)
        colorMap.put("palegreen", -0x670468)
        colorMap.put("paleturquoise", -0x501112)
        colorMap.put("palevioletred", -0x278f6d)
        colorMap.put("papayawhip", -0x102b)
        colorMap.put("peachpuff", -0x2547)
        colorMap.put("peru", -0x327ac1)
        colorMap.put("pink", -0x3f35)
        colorMap.put("plum", -0x225f23)
        colorMap.put("powderblue", -0x4f1f1a)
        colorMap.put("purple", -0x7fff80)
        colorMap.put("red", -0x10000)
        colorMap.put("rosybrown", -0x437071)
        colorMap.put("royalblue", -0xbe961f)
        colorMap.put("saddlebrown", -0x74baed)
        colorMap.put("salmon", -0x57f8e)
        colorMap.put("sandybrown", -0xb5ba0)
        colorMap.put("seagreen", -0xd174a9)
        colorMap.put("seashell", -0xa12)
        colorMap.put("sienna", -0x5fadd3)
        colorMap.put("silver", -0x3f3f40)
        colorMap.put("skyblue", -0x783115)
        colorMap.put("slateblue", -0x95a533)
        colorMap.put("slategray", -0x8f7f70)
        colorMap.put("slategrey", -0x8f7f70)
        colorMap.put("snow", -0x506)
        colorMap.put("springgreen", -0xff0081)
        colorMap.put("steelblue", -0xb97d4c)
        colorMap.put("tan", -0x2d4b74)
        colorMap.put("teal", -0xff7f80)
        colorMap.put("thistle", -0x274028)
        colorMap.put("tomato", -0x9cb9)
        colorMap.put("turquoise", -0xbf1f30)
        colorMap.put("violet", -0x117d12)
        colorMap.put("wheat", -0xa214d)
        colorMap.put("white", -0x1)
        colorMap.put("windowtext", 0xff0)
        colorMap.put("window", -0x1)
        colorMap.put("whitesmoke", -0xa0a0b)
        colorMap.put("yellow", -0x100)
        colorMap.put("yellowgreen", -0x6532ce)

        fun get(colorName: String?): Int? = colorMap[colorName?.toLowerCase()]
    }    
}