package multiThreadServer;

import java.net.Socket;
import java.util.Scanner;

/**
 * Author:lorrie
 * Create:2019/2/25
 */

//客户端的信息
public class Clients {
    private String name;
    private String  password;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
