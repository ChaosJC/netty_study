package com.zfjyc;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOService {
    public static void main(String[] args) throws IOException {
        ExecutorService executorService1 = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了！");
        while (true) {
            System.out.println("线程id："+Thread.currentThread().getId()+"，线程名称："+Thread.currentThread().getName());
             //监听，等待客户端连接
            Socket socket = serverSocket.accept();
            System.out.println("连接了一个客户端！");
            executorService1.execute(new Runnable() {
                @Override
                public void run() {
                    handler(socket);
                }
            });
        }
    }

    public static void handler(Socket socket) {
        try {
            System.out.println("线程id："+Thread.currentThread().getId()+"，线程名称："+Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true) {
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭链接");
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}