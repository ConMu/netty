package com.nio.base;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @author mucongcong
 * @date 2022/09/30 11:25
 *  ByteBuffer（缓冲）和 FileChannel（通道），将 "hello,尚硅谷" 写入到 file01.txt 中
 *
 * @since
 **/
public class NIOFileChannel {
    public static void main(String[] args) throws IOException {
        String s = "Hello World";
        //创建一个输出流 -> channel
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\projects\\gitproject\\nettystudy\\src\\main\\resources\\file01.txt");

        //通过 fileOutputStream 获取对应的 FileChannel
        //这个 fileChannel 真实类型是 FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();
        //创建一个缓冲区 ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将 str 放入 byteBuffer
        byteBuffer.put(s.getBytes());
        //对 byteBuffer 进行 flip
        byteBuffer.flip();
        //将 byteBuffer 数据写入到 fileChannel
        fileChannel.write(byteBuffer);

        fileOutputStream.close();
    }
}
