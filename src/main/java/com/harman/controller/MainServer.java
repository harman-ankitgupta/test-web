/*package com.harman.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import org.apache.log4j.Logger;

import com.harman.Model.TCPStreamingSparkServer;
import com.harman.utils.HarmanUtils;

public class MainServer extends Thread {
	// Logger
	Logger logger = HarmanUtils.returnLogObject(this);

	private static ServerSocket m_serverSocket = null;
	final public static int MAX_CLIENTS = 2;
	final private SubServer[] m_clientConnections = new SubServer[MAX_CLIENTS];
	private static MainServer tcp_server = null;
	

	private MainServer() throws IOException {
		try
		{
		m_serverSocket = new ServerSocket(9997);
		}
		catch (Exception e)
		{
			System.out.println("Error Msr:" + e.toString());
		}
		//this.m_serverSocket = new ServerSocket(9997, 100, InetAddress.getByName("localhost"));
		start();
	}

	public static MainServer getInstance() {
		if (tcp_server == null)
			try {
				tcp_server = new MainServer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return tcp_server;
	}

	public ServerSocket getServerSocket()
	{
		return m_serverSocket;

	}

	public int getActiveSubServer() 
	{
		int retVal = -1;
		for (int idx = 0 ; idx < MAX_CLIENTS ; idx ++)
		{
			if (this.m_clientConnections[idx] != null)
				retVal = idx;
			break;
		}
		return retVal;
	}
	public int setFreeSubServer(int id)
	{
		int retVal = 0;
		for (int idx = 0 ; idx < MAX_CLIENTS ; idx ++)
		{
			if (id == idx)
			{
				this.m_clientConnections[idx] = null;
				retVal = 1;
			}
			break;
		}
		return retVal;
	}

	public int closeClient(int idx) 
	{
		if (this.m_clientConnections[idx] != null) {
			this.m_clientConnections[idx].close();
			return 1;
		}
		return 0;
	}
	
	public int closeClients()
	{
		int retVal = 0;
		for (int idx = 0 ; idx < MAX_CLIENTS ; idx ++)
		{
			if (m_clientConnections[idx] != null)
				retVal = closeClient(idx);
		}
		return retVal;
	}

	@Override
	public void run() {
		
		while (true) {
			Socket clientConnection = null;
			try {
				clientConnection = m_serverSocket.accept();
				assignConnectionToSubServer(clientConnection);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				break;
			}			
		}
		System.out.println("Error: server Accept call exception \n");
	}
	
	public void assignConnectionToSubServer(Socket client_connection) {
		for (int client_idx = 0; client_idx < MAX_CLIENTS; client_idx++) {
			// find an unassigned subserver (waiter)
			
			if (this.m_clientConnections[client_idx] == null) {
				System.out.println("Adding new subserver at index:"+ client_idx);
				logger.info("Adding new subserver at index:"+ client_idx);
				this.m_clientConnections[client_idx] = new SubServer(client_connection, client_idx);
				System.out.println("Adding new subserver :"+ m_clientConnections[client_idx]);
				logger.info("Adding new subserver :"+ m_clientConnections[client_idx]);
				break;
				}
		}
	}

	protected class SubServer 
	{
		final private int connection_id;
		final private Socket m_connection;
		StartUpInit start = StartUpInit.getStartUIntance();
		BlockingQueue<AppMessage> msgQueue = start.getQueue();

		public SubServer(Socket client_connection, int id) 
		{
			this.connection_id = id;
			this.m_connection = client_connection;
			Consumer consumer = new Consumer(msgQueue, this.connection_id, client_connection);
			new Thread(consumer).start();			
		}

	
		 terminates the connection with this client (i.e. stops serving him)
		public void close() {
			try {
				this.m_connection.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}
}
*/