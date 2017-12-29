package com.harman.controller;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import org.springframework.stereotype.Component;
import com.harman.Model.TCPStreamingSparkServer;

@Component
public class StartUpInit{
	
	private static BlockingQueue<AppMessage> mainQueue = new ArrayBlockingQueue<>(100);
	private ExecutorService executorService;
	
	
	@PostConstruct
	public void initTcpServer()
	{
//		AppMessage exitMsg = new AppMessage("{Msg : Default msg in queue}");
//		try {
//			mainQueue.put(exitMsg);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		System.out.println("Before starting server thread \n");

		BasicThreadFactory factory = new BasicThreadFactory.Builder()
				.namingPattern("server-thread").build();

		executorService = Executors.newSingleThreadExecutor(factory);
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				try {
					TCPStreamingSparkServer ServerInst = TCPStreamingSparkServer.getInstance();
					String requestBody = "Connection string from tcp server \n";
					ServerInst.StartTCPServer(requestBody);
					System.out.println("server thread started \n");
					//System.out.println("First Msg sent to spark:: Connection string from tcp server");
					//StartUpInit start = new StartUpInit();
					//BlockingQueue<AppMessage> msgQueue = start.getQueue();
					/* Start consumer and retrieve messages to feed spark-client*/

					System.out.println(" starting consumer thread \n");
					Consumer consumer = Consumer.getInstance(mainQueue);
					new Thread(consumer).start();


				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}});
		}
	
	@PreDestroy
	 public void deInitServer()
	{
		TCPStreamingSparkServer ServerInst = TCPStreamingSparkServer.getInstance();
		Socket socket = ServerInst.getSocket();
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("server thread stopped \n");
		if (executorService != null) {
			executorService.shutdownNow();
		}
	}

	public BlockingQueue<AppMessage> getQueue()
	{
		return mainQueue;
	}
}
