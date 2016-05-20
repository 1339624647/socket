package com.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TestSockServer {
	// ���弯�ϴ洢ͨ��
	static List<Clinet> clientList = new ArrayList<Clinet>();

	public static void main(String[] args) {
		Socket socket = null;

		try {
			// ׼��
			ServerSocket s = new ServerSocket(8888);
			System.out.println("����������!");
			// ����
			// socket = s.accept();
			// ��Ϣ����
			while (true) {
				socket = s.accept();
				// ����Clinet()�Ĵ��ι��췽��
				Clinet c = new TestSockServer().new Clinet(socket);
				clientList.add(c);
				c.start();
			}

		} catch (IOException e) {
			System.out.println("server over!");
		}
	}

	// �ڲ���
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
						System.out.println("�ͻ���˵��" + str);
					}
					// dos.writeUTF(socket.getPort() + "˵��" + str);
					for (int i = 0; i < clientList.size(); i++) {
						// ��ȡ�ͻ���i��socket
						new DataOutputStream(clientList.get(i).socket.getOutputStream())
								.writeUTF(socket.getPort() + "˵��" + str);
					}
				}
			} catch (Exception e) {
				System.out.println("�ͻ���" + socket.getPort() + "�˳�");
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
