package org.oyach.mybatis.until;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuzhenyuan
 * @version Last modified 15/2/28
 * @since 0.0.1
 */
public class CollectionUtil {

    @Test
    public void test01() throws Exception {
        List<Long> orderIds = new ArrayList<Long>();
        orderIds.add(1L);
        orderIds.add(2L);
        orderIds.add(3L);
        orderIds.add(4L);
        orderIds.add(5L);
        orderIds.add(6L);
        orderIds.add(7L);

        List<List<Long>> parts = new ArrayList<List<Long>>();

        final int size = orderIds.size();
        final int length = 2;
        for (int i = 0; i < size; i += length) {
            parts.add(new ArrayList<Long>(orderIds.subList(i, Math.min(size, i + length))));
        }

        System.out.println(parts);

    }
}
