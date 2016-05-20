package com.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TestSockClient {
	static DataInputStream dis = null;
    public static void main(String[] args) {
	DataOutputStream dos = null;
	Socket socket = null;
	String str=null;
	Scanner input=new Scanner(System.in);
	try {
		socket=new Socket("127.0.0.1", 8888);
		System.out.println("客户端已连接！");
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			//客户端向服务器发送内容
			//匿名内部类 另起一个线程作输出  子线程
			new Thread(){
				@Override
				public void run() {
					String s=null;
					try {
						while(true){
						if ((s = dis.readUTF()) != null) 
							System.out.println(s);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						//忽略异常
						//e.printStackTrace();
					}
				};
			}.start();
			//主线程
			do {
			str=input.nextLine();
			dos.writeUTF(str);
		} while (!str.equals("88"));
		System.out.println("client over!");
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
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
