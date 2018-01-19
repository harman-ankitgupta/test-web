package com.harman.Model;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.log4j.Logger;
import com.harman.utils.HarmanUtils;

public class TCPStreamingSparkServer {
	Logger logger = HarmanUtils.returnLogObject(this);
	private static TCPStreamingSparkServer SparkInst = null;
	private static Socket activeSocket = null ;
	private static ServerSocket serverSocket = null;
	private static BufferedWriter socketWriter = null;

	public static TCPStreamingSparkServer getInstance() {
		if (SparkInst == null)
			SparkInst = new TCPStreamingSparkServer();
		return SparkInst;
	}

	public Socket getClientSocket()
	{
		return activeSocket;
	}
	public ServerSocket getServerSocket()
	{
		return serverSocket;
	}
	public void StartTCPServer(String jsonReq) throws IOException {

		serverSocket = new ServerSocket(9997, 100, InetAddress.getByName("10.0.0.5"));
		System.out.println("TCP Server started " + serverSocket);
		logger.info("TCP Server started: " + serverSocket);
		//while(true)
		//{
		activeSocket = serverSocket.accept();
		System.out.println("Received a  connection from  " + activeSocket);
		logger.info("Received a  connection from  " + activeSocket);		
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
			System.out.println("Before \n" + jsonReq);
			logger.info("Before \n" + jsonReq);
            jsonReq=jsonReq.replace("\n", "")+"\n";
            System.out.println("after \n removed" + jsonReq);
            logger.info("after \n removed" + jsonReq);
			socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			socketWriter.write(jsonReq);
			//socketWriter.write("\n");
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


