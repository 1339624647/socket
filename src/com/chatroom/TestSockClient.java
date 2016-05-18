package com.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TestSockClient {
public static void main(String[] args) {
	DataOutputStream dos = null;
	DataInputStream dis = null;
	Socket socket = null;
	String s=null;
	String str=null;
	Scanner input=new Scanner(System.in);
	try {
		socket=new Socket("127.0.0.1", 8888);
		do {
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			//客户端向服务器发送内容
			str=input.nextLine();
			dos.writeUTF(str);
			if ((s = dis.readUTF()) != null) {
				System.out.println(s);
			}
		} while (!str.equals("88"));
		System.out.println("server over!");
	} catch (Exception e) {
		// TODO: handle exception
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
