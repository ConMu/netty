package com.netty.demo2;

import com.netty.demo2.client.InitClient;
import io.netty.bootstrap.Bootstrap;

/**
 * @author mucongcong
 * @date 2022/07/08 17:49
 * @since
 **/
public class Main {
    public static void main(String[] args) {
        Bootstrap init = InitClient.init();

    }
}
