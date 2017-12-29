package com.harman.Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

import com.harman.utils.ErrorType;

public class TCPStreamingSparkServer {
	private static TCPStreamingSparkServer SparkInst = null;
	private static Socket activeSocket = null ;
	private static BufferedWriter socketWriter = null;

	public static TCPStreamingSparkServer getInstance() {
		if (SparkInst == null)
			SparkInst = new TCPStreamingSparkServer();
		return SparkInst;
	}
	
	public Socket getSocket()
	{
	return activeSocket;
	}
	public void StartTCPServer(String jsonReq) throws IOException {

		ServerSocket serverSocket = new ServerSocket(9997, 100, InetAddress.getByName("localhost"));
		System.out.println("Server started  at:  " + serverSocket);

		//while (true) {
			System.out.println("Waiting for a  connection... port: 9997");

			activeSocket = serverSocket.accept();

			System.out.println("Received a  connection from  " + activeSocket);
			Runnable runnable = () -> handleClientRequest(activeSocket,jsonReq );
			new Thread(runnable).start(); // start a new thread
		//}
	}

	public void handleClientRequest(Socket socket, String jsonReq) 
	{
		String connectionString  = "Connection string from tcp server \n";
		if (jsonReq == connectionString)
			return;

		try {	
			socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			socketWriter.write(jsonReq);
			socketWriter.write("\n");
			socketWriter.flush();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public BufferedWriter getBufferWriter()
	{
		return socketWriter;
	}
}


