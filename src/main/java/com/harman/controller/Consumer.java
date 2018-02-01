//package com.harman.controller;
//
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.net.Socket;
//import java.util.concurrent.BlockingQueue;
//import org.apache.log4j.Logger;
//import com.harman.Model.TCPStreamingSparkServer;
//import com.harman.utils.HarmanUtils;
//
//public class Consumer implements Runnable{
//	Logger logger = HarmanUtils.returnLogObject(this);
//	
//	private static BufferedWriter socketWriter = null;
//	private BlockingQueue<AppMessage> queue;
//	private int threadId = 0;
//	private Socket activeSocket = null;
//	private static boolean threadRunning = true;
//	
//	
//	public Consumer(BlockingQueue<AppMessage> q, int th_idx, Socket client_connection){
//		this.queue = q;
//		this.threadId = th_idx;
//		this.activeSocket = client_connection;
//	}
//
//	@Override
//	public void run() {
//		long thread_id = Thread.currentThread().getId();
//		System.out.println("Consumer thread running...Id: "+ threadId + "with Fd:"+ activeSocket + "Thread:" + thread_id);
//		logger.info("Consumer thread started Id: " + threadId + "with Fd:"+ activeSocket + "Thread:" + thread_id);
//		while(threadRunning){
//			synchronized (queue){
//				while(queue.size() == 0)
//				{
//					try {
//						System.out.println("Queue is empty, Consumer" +threadId + "is waiting");
//						logger.info("Queue is empty, Consumer" +threadId + "is waiting");
//						queue.wait();
//
//					} catch (InterruptedException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//						logger.error("Error in consumer"+threadId + ", Msg:"+ e1.getMessage());
//					}
//				}
//				while (queue.size() > 0) {
//					try {
//						
//						handleClientRequest(activeSocket, threadId, queue.take().getMsg().toString());
//						System.out.println("Messge fed to spark by thread:" +threadId +" \n");
//						logger.info("Message fed to spark by thread:" +threadId + "\n");
//					} catch (Exception e) {
//						logger.error("Error in consumer, Msg:"+ e.getMessage());
//						System.out.println("exception");
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//	}
//	
//	public void handleClientRequest(Socket socket, int connection_id, String jsonReq) {
//		try {
//			logger.info("Before \n" + jsonReq);
//			jsonReq = jsonReq.replace("\n", "") + "\n";
//			logger.info("after \n removed" + jsonReq);
//			socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//			socketWriter.write(jsonReq);
//			socketWriter.flush();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//			logger.info(threadId + "Cant write on its socket, Close the socket and kill thread");
//			try {
//				threadRunning = false;
//				MainServer main = MainServer.getInstance();
//				main.setFreeSubServer(connection_id);
//				socket.close();
//				
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			
//		}
//	}
//		
//}
//
