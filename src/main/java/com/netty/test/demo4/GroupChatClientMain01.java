package com.netty.test.demo4;

/**
 * @author mucongcong
 * @date 2022/10/24 11:41
 * @since
 **/
public class GroupChatClientMain01 {

    public static void main(String[] args) throws Exception {
        new GroupChatClient("127.0.0.1", 7000).run();
    }
}
