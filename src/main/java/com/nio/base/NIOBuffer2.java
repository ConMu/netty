package com.nio.base;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author mucongcong
 * @date 2022/09/30 11:25
 *  ByteBuffer 支持类型化的 put 和 get，put 放入的是什么数据类型，get 就应该使用相应的数据类型来取出，否则可能有 BufferUnderflowException 异常。【举例说明】
 *
 * @since
 **/
public class NIOBuffer2 {
    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(64);

        //类型化方式放入数据
        buffer.putInt(100);
        buffer.putLong(9);
        buffer.putChar('尚');
        buffer.putShort((short) 4);

        buffer.flip();
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());

        buffer.flip();
        System.out.println(buffer.getLong());
        System.out.println(buffer.getInt());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());

    }
}
