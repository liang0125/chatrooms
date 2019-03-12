package singleserver;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * 单线程聊天室服务端
 * <p>
 * Author:lorrie
 * Create:2019/2/23
 */
public class SingleThreadServer {
    private static OutputStreamWriter outputClient;
    private  static InputStreamReader inputStreamReader;
    private static Socket client;
    public static void main(String[] args) {
        int port = 6666;

        try {
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            }
            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("等待客户端的连接...");
            //客户端接收返回Socket对象
                 client = serverSocket.accept();

                //来自客户端给服务端的输入
                //接收数据
                 outputClient = new OutputStreamWriter(client.getOutputStream());
                outputClient.write("你好，欢迎连接服务端！！！\n");
                outputClient.flush();
                Scanner inputClient = new Scanner(client.getInputStream());
                String in = inputClient.next();
                System.out.println(in);

                //服务端线程
            Thread thread=new Thread(new Runnable() {
                public void run() {
                    try {
                        Scanner input=new Scanner(client.getInputStream());
                        while (true) {
                            if (input.hasNext()) {
                                System.out.println(input.next());

                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

            //发送数据
            Scanner ins=new Scanner(System.in);
            while (true){
                if(ins.hasNext()){
                    try {
                        outputClient.write(ins.next()+"\n");
                        outputClient.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
