package singleClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Author:lorrie
 * Create:2019/2/23
 */

public class SingleThreadClient {

    private static OutputStreamWriter outputClient;
    private  static InputStreamReader inputStreamReader;
    private static Socket client;

    public static void main(String[] args) {
        int port=6666;
        String host="127.0.0.1";
        try {
            if(args.length>1){
                port= Integer.parseInt(args[0]);
                if(args[1].split(".").length==4){
                    host=args[1];
                }
            }

            //第一次连接的提示信息
            client=new Socket(host,port);
            System.out.println("服务端连接成功！！！");

                Scanner inputClient = new Scanner(client.getInputStream());
                String in = inputClient.next();
                System.out.println(in);
                outputClient = new OutputStreamWriter(client.getOutputStream());

                outputClient.write("连接到"+client.getInetAddress()+"地址的客户端,可以开始交互了！\n");
                outputClient.flush();

            Thread thread=new Thread(new Runnable() {
                Scanner input=new Scanner(client.getInputStream());
                public void run() {

                    //接收数据
                    while (true) {

                        if (input.hasNext()) {
                                System.out.println("服务端："+input.next());
                            }
                    }
                }
            });
            thread.start();

            //发送数据
            Scanner ins=new Scanner(System.in);
            while (true){
                try {
                    outputClient.write("客户端："+ins.next()+"\n");
                    outputClient.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
