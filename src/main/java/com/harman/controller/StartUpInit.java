package com.harman.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import com.harman.Model.TCPStreamingSparkServer;
import com.harman.utils.HarmanUtils;

@Component
public class StartUpInit{
	Logger logger = HarmanUtils.returnLogObject(this);
	/*static StartUpInit statup;
	
	private static BlockingQueue<AppMessage> mainQueue = new ArrayBlockingQueue<>(100);
	private ExecutorService executorService;
	public static StartUpInit getStartUIntance(){
		return statup;
	}
	*/
	@PostConstruct
	public void initTcpServer()
	{/*
		//statup=this;
		System.out.println("On app startup:: before starting Tcp server thread \n");
		logger.info("On app startup:: before starting Tcp server thread");

		BasicThreadFactory factory = new BasicThreadFactory.Builder()
				.namingPattern("server-thread").build();
		executorService = Executors.newSingleThreadExecutor(factory);
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				try {
					 Create country code table on startup 
					//CountryCodeTable country_code = CountryCodeTable.getInstance();
					//country_code.tableCreate();
					//new Thread(country_code).start();
					//System.out.println("Country code Thread started");
					
					MainServer serevr =MainServer.getInstance();
					new Thread(serevr).start();
					System.out.println("On app startup:: Streaming server thread started \n");
					logger.info("On app startup:: Streaming Server thread started");
										
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("Error in App startup, Msg:" + e.getMessage());
				}}});
		*/}
	
	@PreDestroy
	 public void deInitServer()
	{/*
		MainServer mainServer = MainServer.getInstance();
		ServerSocket serverSocket = mainServer.getServerSocket();
		
		try {			
			int result = mainServer.closeClients();
			if (result == 1)
			{
				System.out.println("On app destroy:: Active clients sockets closed \n");
				logger.info("On app destroy:: Active clients sockets closed");
			}else
			{
				System.out.println("Failed to close Active clients sockets \n");
			}
			
			if(serverSocket != null)
			{
				serverSocket.close();
				logger.info("On app destroy:: Server socket closed");
				System.out.println("On app destroy:: Server socket closed \n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error in App destroy socket close, Msg:" + e.getMessage());
		}
		if (executorService != null) {
			executorService.shutdownNow();
		}
		System.out.println("On app destroy:: Server thread stopped \n");
		logger.info("On app destroy:: Server thread closed");
	*/}

//	public BlockingQueue<AppMessage> getQueue()
//	{
//		return mainQueue;
//	}
}
