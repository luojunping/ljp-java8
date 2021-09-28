package com.ljp.test.oio;

import java.nio.charset.StandardCharsets;

/**
 * @author 罗俊平
 * @email 591402399@qq.com
 * @date 2021/9/21
 * @since 1.0.0
 **/
public class Haha {

    public static void main(String[] args) {
        String haha = "hello";
        System.out.println(haha.getBytes(StandardCharsets.UTF_8).length);
        byte[] bytes = haha.getBytes(StandardCharsets.UTF_8);
        byte[] bb = new byte[4];
        for (int i = 0; i < 4; i++) {
            bb[i] = bytes[i];
        }
        System.out.println(new String(bb, StandardCharsets.UTF_8));
    }
}
