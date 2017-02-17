package cn.whaley.member.base.util;

import java.io.*;
import java.util.List;

/**
 * Created by leiwenbin on 15/10/20.
 * Object Serialize
 */

@SuppressWarnings("ALL")
public class SerializeHelper {

    public static <T> byte[] serialize(T object) {
        if (object == null)
            throw new NullPointerException("Can't serialize null");

        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            bytes = baos.toByteArray();
        } catch (Exception e) {
            LogHelper.getLogger().error("object serialize error,exception:" + e);
        } finally {
            close(baos);
            close(oos);
        }
        return bytes;
    }

    public static <T> byte[] serialize(List<T> object) {
        if (object == null)
            throw new NullPointerException("Can't serialize null");

        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        byte[] bytes = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            for (T t : object)
                os.writeObject(t);
            os.writeObject(null);
            os.close();
            bos.close();
            bytes = bos.toByteArray();
        } catch (Exception e) {
            LogHelper.getLogger().error("object serialize error,exception:" + e);
        } finally {
            close(os);
            close(bos);
        }
        return bytes;
    }

    public static <T> T unserialize(byte[] bytes) {
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        T obj = null;
        try {
            if (bytes != null) {
                bis = new ByteArrayInputStream(bytes);
                is = new ObjectInputStream(bis);
                obj = (T) is.readObject();
                is.close();
                bis.close();
            }
        } catch (Exception e) {
            LogHelper.getLogger().error("object unserialize error,exception:" + e);
        } finally {
            close(is);
            close(bis);
        }
        return obj;
    }

    protected static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                LogHelper.getLogger().error("Unable to close:" + closeable + "exception:" + e);
            }
        }
    }
}
