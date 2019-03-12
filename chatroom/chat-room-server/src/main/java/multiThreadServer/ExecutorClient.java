package multiThreadServer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author:lorrie
 * Create:2019/2/24
 */
public class ExecutorClient implements Runnable {
    //当前客户端
    private Socket currentClient;
    //所有注册用户的集合
    private final static ConcurrentHashMap<String, Socket> ONLINE_USER = new ConcurrentHashMap<>();

    public ExecutorClient(Socket currentClient) {
        this.currentClient = currentClient;
    }

    @Override
    public void run() {
        try {
            //接收数据
            Scanner in = new Scanner(currentClient.getInputStream());

            while (true) {
                String select = in.next();
                switch (select) {
                    case "1": {//注册
                        String message = in.next();
                        String userName = message.split("\\:")[0];
                        String password = message.split("\\:")[1];
                        this.register(userName, password);
                        break;
                    }
                    case "2": {//私聊
                        String message = in.next();
                        String targetUser = message.split("\\:")[0];
                        String targetMessage = message.split("\\:")[1];
                        this.privateChat(targetUser, targetMessage);
                        break;
                    }
                    case "3": {//群聊
                        String message = in.next();
                        this.groupChat(message);
                        break;
                    }
                    case "4": {//退出
                        this.quit();
                        break;
                    }
                }
                if (select.equals("4")) {
                    break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //退出功能具体实现
    private void quit() {
        String userName = this.getCurrentClientName();
        System.out.println("用户" + userName + this.currentClient.getRemoteSocketAddress() + "下线了！！！");
        sendMessage(this.getCurrentClientName(), "bye!!!");
        try {
            this.currentClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ONLINE_USER.remove(userName);
        this.printCurrentClient();
    }

    //打印当前在线人数
    private void printCurrentClient() {
        System.out.println("当前在线用户人数：(" + ONLINE_USER.size() + ") 列表如下：");
        for (String userName : ONLINE_USER.keySet()) {
            System.out.println(userName);
        }
    }

    //群聊功能具体实现
    private void groupChat(String groupMessage) {
        String currentUserName = this.getCurrentClientName();
        for (Map.Entry<String, Socket> entry : ONLINE_USER.entrySet()) {
            if (!entry.getKey().equals(currentUserName)) {
                sendMessage(entry.getKey(), currentUserName + "(" + this.currentClient.getRemoteSocketAddress() + "):" + groupMessage);
            }
        }
    }

    //私聊功能具体实现
    private void privateChat(String targetUserName, String targetMessage) {
        String currentUserName = this.getCurrentClientName();
        if (targetUserName == null) {
            sendMessage(currentUserName, "没有此用户！！！");
        } else if (!ONLINE_USER.containsKey(targetUserName)) {
            sendMessage(currentUserName, "该用户不在线！！！");
        } else {
            sendMessage(targetUserName, currentUserName + "(" + this.currentClient.getRemoteSocketAddress() + "):" + targetMessage);

            sendMessage(currentUserName, "发送成功");
        }
    }

    //注册功能具体实现
    private void register(String userName, String password) {
        //数据库存储
        ClientsDAO clientsDAO = new ClientsDAO();

        //判断是否已经注册过
        String flag = clientsDAO.search(userName);
        //第一次注册
        if (flag == null) {
            clientsDAO.add(userName, password);
            ONLINE_USER.put(userName, this.currentClient);
            sendMessage(userName, "注册成功!!!");
            System.out.println("用户" + userName + "加入聊天室" + this.currentClient.getRemoteSocketAddress());
        }
        //已经注册过的直接登录即可
        else {
            if (ONLINE_USER.containsKey(userName)) {
                sendMessage(this.getCurrentClientName(), "该用户已登录!!!");
            }
            else {
                if (password.equals(clientsDAO.searchPassword(userName))) {
                    ONLINE_USER.put(userName, this.currentClient);
                    sendMessage(userName, "登录成功!!!");
                    System.out.println("用户" + userName + "加入聊天室" + this.currentClient.getRemoteSocketAddress());
                } else {
                    sendMessage(userName, "密码输入有误！！！");
                }
            }
        }
        this.printCurrentClient();
    }

    //向目标客户发送消息
    private void sendMessage(String targetUser, String message) {
        try {
            Socket target = currentClient;
            for (Map.Entry<String, Socket> entry : ONLINE_USER.entrySet()) {
                if (targetUser.equals(entry.getKey())) {
                    target = entry.getValue();
                }
            }
            OutputStream outputStream = target.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            writer.write(message + "\n");
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取当前客户的用户名
    private String getCurrentClientName() {
        String currentUserName = " ";
        for (Map.Entry<String, Socket> entry : ONLINE_USER.entrySet()) {
            if (this.currentClient.equals(entry.getValue())) {
                currentUserName = entry.getKey();
                break;
            }
        }
        return currentUserName;
    }
}
