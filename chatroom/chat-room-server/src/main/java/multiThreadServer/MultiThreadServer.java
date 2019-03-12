package multiThreadServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author:lorrie
 * Create:2019/2/24
 */

//多线程服务端
public class MultiThreadServer {
    public static void main(String[] args) {
        int port=6666;
        //线程池调度
        ExecutorService executor= Executors.newFixedThreadPool(5);

        try {
            if(args.length>0){
                port=Integer.parseInt(args[0]);
            }
            ServerSocket serverSocket=new ServerSocket(port);
            System.out.println("等待客户端的连接...");
            while (true){
                Socket client=serverSocket.accept();
                executor.submit(new ExecutorClient(client));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
