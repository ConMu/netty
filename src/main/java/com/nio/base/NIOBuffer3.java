package com.nio.base;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author mucongcong
 * @date 2022/09/30 11:25
 *  可以将一个普通 Buffer 转成只读 Buffer【举例说明】  HeapByteBufferR  提供只读功能
 *
 * @since
 **/
public class NIOBuffer3 {
    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(64);

        for (int i = 0; i < 64; i++) {
            buffer.put((byte) i);
        }

        //读取
        buffer.flip();

        //只读bugger
        ByteBuffer asReadOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(asReadOnlyBuffer.getClass());

        // 读取
        while (asReadOnlyBuffer.hasRemaining()) {
            System.out.println(asReadOnlyBuffer.get());
        }

        asReadOnlyBuffer.put((byte) 100);
    }
}
