package com.nio.base;

import java.nio.IntBuffer;
/**
 * nio基础测试
 *
 * @author mucongcong
 * @date 2022/09/30 10:26
 * @since
 **/
public class NIOBuffer {
    public static void main(String[] args) {
        //举例说明 Buffer 的使用(简单说明)
        //创建一个 Buffer，大小为 5，即可以存放 5 个 int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i*2);
        }
        //如何从 buffer 读取数据
        //将 buffer 转换，读写切换(!!!)  修改position 参数，进行颠倒
        intBuffer.flip();
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
