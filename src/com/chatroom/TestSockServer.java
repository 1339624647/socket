package com.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TestSockServer {
	// 定义集合存储通道
	static List<Clinet> clientList = new ArrayList<Clinet>();

	public static void main(String[] args) {
		Socket socket = null;

		try {
			// 准备
			ServerSocket s = new ServerSocket(8888);
			System.out.println("服务器启动!");
			// 连接
			// socket = s.accept();
			// 信息交互
			while (true) {
				socket = s.accept();
				// 调用Clinet()的带参构造方法
				Clinet c = new TestSockServer().new Clinet(socket);
				clientList.add(c);
				c.start();
			}

		} catch (IOException e) {
			System.out.println("server over!");
		}
	}

	// 内部类
	class Clinet extends Thread {
		DataOutputStream dos = null;
		DataInputStream dis = null;
		Socket socket = null;

		public Clinet(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				dos = new DataOutputStream(socket.getOutputStream());
				dis = new DataInputStream(socket.getInputStream());
				while (true) {

					String str = null;
					if ((str = dis.readUTF()) != null) {
						System.out.println("客户端说：" + str);
					}
					// dos.writeUTF(socket.getPort() + "说：" + str);
					for (int i = 0; i < clientList.size(); i++) {
						// 提取客户端i的socket
						new DataOutputStream(clientList.get(i).socket.getOutputStream())
								.writeUTF(socket.getPort() + "说：" + str);
					}
				}
			} catch (Exception e) {
				System.out.println("客户端" + socket.getPort() + "退出");
				clientList.remove(this);
				//e.printStackTrace();
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
}
