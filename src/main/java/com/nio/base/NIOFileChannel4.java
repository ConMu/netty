package com.nio.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author mucongcong
 * @date 2022/09/30 11:25
 *  使用 FileChannel（通道）和方法 transferFrom，完成文件的拷贝
 *
 * @since
 **/
public class NIOFileChannel4 {
    public static void main(String[] args) throws IOException {

        File file = new File("D:\\projects\\gitproject\\nettystudy\\src\\main\\resources\\image.png");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel originChannel = fileInputStream.getChannel();
        File file2 = new File("D:\\projects\\gitproject\\nettystudy\\src\\main\\resources\\image3.png");
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        FileChannel copyChannel = fileOutputStream.getChannel();
        //使用 transferForm 完成拷贝
        copyChannel.transferFrom(originChannel, 0, originChannel.size());

        originChannel.close();
        copyChannel.close();
        fileInputStream.close();
        fileOutputStream.close();

    }
}
