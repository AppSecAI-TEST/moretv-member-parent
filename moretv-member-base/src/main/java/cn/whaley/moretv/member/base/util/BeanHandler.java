package cn.whaley.moretv.member.base.util;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import java.util.List;

/**
 * bean 辅助工具
 *
 * Created by Bob Jiang on 2016/9/12.
 */
public class BeanHandler {


    public static <E, T> T copyProperties(E e, T t) {
        BeanUtils.copyProperties(e , t);
        return t;
    }

    public static <E, T> T copyProperties(E e, Class<T> clx) {
        try {
            return copyProperties(e, clx.newInstance());
        } catch (Exception ex) {
            return null;
        }
    }

    public static <E, T> List<T> copyProperties(List<E> eList, Class<T> clx) {
        List<T> tList = Lists.newArrayList();
        for (E e : eList) {
            tList.add(copyProperties(e, clx));
        }
        return tList;
    }

}
