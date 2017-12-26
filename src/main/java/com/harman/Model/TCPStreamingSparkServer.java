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
	static TCPStreamingSparkServer SparkInst = null;
	
	public static TCPStreamingSparkServer getInstance() {
		if (SparkInst == null)
			SparkInst = new TCPStreamingSparkServer();
		return SparkInst;
	}
	public ErrorType StartTCPServer(String jsonReq) throws IOException {

		ServerSocket serverSocket = new ServerSocket(9999, 100, InetAddress.getByName("localhost"));
		System.out.println("Server started  at:  " + serverSocket);

		while (true) {
			System.out.println("Waiting for a  connection...");

			final Socket activeSocket = serverSocket.accept();

			System.out.println("Received a  connection from  " + activeSocket);
			Runnable runnable = () -> handleClientRequest(activeSocket,jsonReq );
			new Thread(runnable).start(); // start a new thread
		}
	}

public static void handleClientRequest(Socket socket, String jsonReq) {

		try {
			//BufferedReader socketReader = null;
			BufferedWriter socketWriter = null;
			//socketReader = new BufferedReader(new InputStreamReader(
					//socket.getInputStream()));
			socketWriter = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));

			 String inMsg ;
			//while ((inMsg = socketReader.readLine()) != null)
			{
				//System.out.println("Received from  client: " + inMsg);

				//String outMsg = inMsg;
				
				socketWriter.write(jsonReq);
				socketWriter.write("\n");
				socketWriter.flush();
			}
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
