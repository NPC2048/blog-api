package com.liangyuelong.blog.utils.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * socket 消息处理工具类
 *
 * @author yuelong.liang
 */
public class MessageUtils {


    /**
     * 从 socket 读取消息
     *
     * @return String
     */
    public static String readMessage(Socket socket)  {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 发送消息 + \n
     *
     * @param socket  Socket
     * @param message msg
     * @throws IOException e
     */
    public static void sendMessage(Socket socket, String message) throws IOException {
        OutputStream output = socket.getOutputStream();
        output.write(message.getBytes(StandardCharsets.UTF_8));
        output.write('\n');
        output.flush();
    }

    public static void sendMessage(Socket socket, int bit) throws IOException {
        OutputStream output = socket.getOutputStream();
        output.write(bit);
        output.write('\n');
        output.flush();
    }
}
