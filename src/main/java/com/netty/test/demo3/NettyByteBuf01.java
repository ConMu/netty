package com.netty.test.demo3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Netty核心模块demo-Unpooled缓冲封装容器
 *
 * @author mucongcong
 * @date 2022/10/24 11:09
 * @since
 **/
public class NettyByteBuf01 {
    public static void main(String[] args) {
        //创建一个ByteBuf
        //说明
        //1. 创建 对象，该对象包含一个数组arr , 是一个byte[10]
        //2. 在netty 的buffer中，不需要使用flip 进行反转
        //   底层维护了 readerindex 和 writerIndex
        //3. 通过 readerindex 和  writerIndex 和  capacity， 将buffer分成三个区域
        // 0---readerindex 已经读取的区域
        // readerindex---writerIndex ， 可读的区域
        // writerIndex -- capacity, 可写的区域
        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        System.out.println("capacity=" + buffer.capacity());

        // 不需要flip翻转
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());
        }
        System.out.println("============");
    }
}
