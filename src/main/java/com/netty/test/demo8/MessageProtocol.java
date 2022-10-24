package com.netty.test.demo8;

/**
 * 解决粘包、解包问题：
 * 使用自定义协议+编解码器来解决
 * 关键就是要解决服务器端每次读取数据长度的问题，这个问题解决，就不会出现服务器多读或少读数据的问题，从而避免的 TCP 粘包、拆包。
 *
 * @author mucongcong
 * @date 2022/10/24 17:35
 * @since
 **/
public class MessageProtocol {
    private int len; //关键

    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
