package cn.whaley.moretv.member.base.util;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * Created by leiwenbin on 15/9/25.
 * Base64 Tools
 */

public final class Base64Helper {

    public static String base64Encode(final String source) {
        if (source == null)
            return null;

        return (new BASE64Encoder()).encode(source.getBytes());
    }

    public static String base64Decode(final String bSource) {
        if (bSource == null)
            return null;
        try {
            byte[] b = (new BASE64Decoder()).decodeBuffer(bSource);
            return new String(b);
        } catch (IOException e) {
            return null;
        }
    }

    public static String base64Encode(final byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }

    public static String base64Decode(final byte[] bytes) {
        return new String(Base64.decodeBase64(bytes));
    }

}
