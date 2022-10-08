package com.nio.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author mucongcong
 * @date 2022/09/30 11:25
 *  NIO 还提供了 MappedByteBuffer，可以让文件直接在内存（堆外的内存）中进行修改，而如何同步到文件由 NIO 来完成。【举例说明】
 *
 * @since
 **/
public class NIOBuffer4 {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\projects\\gitproject\\nettystudy\\src\\main\\resources\\file01.txt", "rw");
        //获取通道
        FileChannel channel = randomAccessFile.getChannel();

        File file2 = new File("D:\\projects\\gitproject\\nettystudy\\src\\main\\resources\\file02.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        FileChannel copyChannel = fileOutputStream.getChannel();

        /**
         * 参数 1:FileChannel.MapMode.READ_WRITE 使用的读写模式
         * 参数 2：0：可以直接修改的起始位置
         * 参数 3:5: 是映射到内存的大小（不是索引位置），即将 1.txt 的多少个字节映射到内存
         * 可以直接修改的范围就是 0-5
         * 实际类型 DirectByteBuffer
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'S');
        mappedByteBuffer.put(3, (byte) 'A');
//        mappedByteBuffer.put(5, (byte) 'Y');//IndexOutOfBoundsException

        //使用 transferForm 完成拷贝
        copyChannel.transferFrom(channel, 0, channel.size());

        randomAccessFile.close();
        System.out.println("修改成功~~");
    }
}
