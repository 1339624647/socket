package com.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TestSockServer {
	public static void main(String[] args) {
		DataOutputStream dos = null;
		DataInputStream dis = null;
		Socket socket = null;
		try {
			// 准备
			ServerSocket s = new ServerSocket(8888);
			System.out.println("服务器启动");
			// 连接
			socket = s.accept();
			// 信息交互
			while (true) {
				dos = new DataOutputStream(socket.getOutputStream());
				dis = new DataInputStream(socket.getInputStream());
				String str = null;
				if ((str = dis.readUTF()) != null) {
					System.out.println("客户端说：" + str);
				}
				dos.writeUTF("服务器说：" + str);
			}

		} catch (IOException e) {
			System.out.println("server over!");
		} finally {
			try {
				dis.close();
				dos.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
