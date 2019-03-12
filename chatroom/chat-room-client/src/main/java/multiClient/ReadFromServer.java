package multiClient;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Author:lorrie
 * Create:2019/2/24
 */

//从服务器读取消息
public class ReadFromServer implements Runnable{
    private final Socket client;
    public ReadFromServer(Socket client) {
        this.client=client;
    }

    @Override
    public void run() {
        try {
            Scanner inputClient=new Scanner(client.getInputStream());
            while (true){
                if(inputClient.hasNext()){
                    String message=inputClient.next();
                    System.out.println(message);
                    if("bye!!!".equals(message)){
                        break;
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
