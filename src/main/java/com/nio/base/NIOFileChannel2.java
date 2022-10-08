package com.nio.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author mucongcong
 * @date 2022/09/30 11:25
 *  ByteBuffer（缓冲）和 FileChannel（通道），读取file01.txt 中
 *
 * @since
 **/
public class NIOFileChannel2 {
    public static void main(String[] args) throws IOException {

        File file = new File("D:\\projects\\gitproject\\nettystudy\\src\\main\\resources\\file01.txt");
        FileInputStream fileOutputStream = new FileInputStream(file);

        //通过 fileOutputStream 获取对应的 FileChannel
        //这个 fileChannel 真实类型是 FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        //将通道的数据读入到 Buffer
        fileChannel.read(byteBuffer);
        byteBuffer.flip();

        //将 byteBuffer 的字节数据转成 String
        System.out.println(new String(byteBuffer.array()));

        fileOutputStream.close();
    }
}
