package multiClient;

import java.io.IOException;
import java.net.Socket;

/**
 * Author:lorrie
 * Create:2019/2/24
 */

//多线程客户端
public class MultiThreadClient {

    public static void main(String[] args) {
        String host="127.0.0.1";
        int port=6666;
        try {
            if(args.length>1){
                host=args[0];
                port=Integer.parseInt(args[1]);
            }
            Socket client = new Socket(host, port);
            System.out.println("连接成功 !!!");
            new Thread(new ReadFromServer(client)).start();
            new Thread(new WriterToServer(client)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
