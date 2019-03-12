package multiClient;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Author:lorrie
 * Create:2019/2/24
 */

//向服务器发送消息
public class WriterToServer implements Runnable{
    private static Scanner in=new Scanner(System.in);
    private Socket client;
    public WriterToServer(Socket client) {
        this.client=client;
    }

    @Override
    public void run() {
        try {
            OutputStream outputStream=client.getOutputStream();
            OutputStreamWriter outputClient=new OutputStreamWriter(outputStream);

            menu();
            while (true){
                Thread.sleep(500);
                System.out.println("请选择：");
                String  select=in.next();
                outputClient.write(select+"\n");
                outputClient.flush();
                    if(select.equals("1")){
                        System.out.println("请输入用户名和密码，用:隔开：");
                        String message=in.next();
                        outputClient.write(message+"\n");
                        outputClient.flush();
                        continue;
                    }
               else if(select.equals("2")){
                        System.out.println("请输入要私发的用户及消息，用:隔开");
                        String message=in.next();
                        outputClient.write(message+"\n");
                        outputClient.flush();
                        continue;
                    }
               else if(select.equals("3")){
                        System.out.println("请输入要群发的消息：");
                        String message=in.next();
                        outputClient.write(message+"\n");
                        outputClient.flush();
                        continue;
                    }
                else if(select.equals("4")){
                        break;
                    }
                }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //菜单提示信息
    public void menu(){
        System.out.println("********      欢迎加入聊天室         *******");
        System.out.println("*********  1.注册/登录   2.私聊      *******");
        System.out.println("*********  3.群聊        4.退出      *******");
        System.out.println("*******************************************");


    }
}
