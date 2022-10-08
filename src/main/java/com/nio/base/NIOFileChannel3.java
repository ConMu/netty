package com.nio.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author mucongcong
 * @date 2022/09/30 11:25
 *  ByteBuffer（缓冲）和 FileChannel（通道），拷贝文件
 *
 * @since
 **/
public class NIOFileChannel3 {
    public static void main(String[] args) throws IOException {

        File file = new File("D:\\projects\\gitproject\\nettystudy\\src\\main\\resources\\image.png");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel originChannel = fileInputStream.getChannel();
        File file2 = new File("D:\\projects\\gitproject\\nettystudy\\src\\main\\resources\\image2.png");
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        FileChannel copyChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true) {
            byteBuffer.clear();
            int read = originChannel.read(byteBuffer);
            if (read == -1) {
                //读完
                break;
            }

            byteBuffer.flip();
            copyChannel.write(byteBuffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
