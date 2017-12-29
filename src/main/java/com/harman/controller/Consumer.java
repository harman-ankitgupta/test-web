package com.harman.controller;

import java.io.BufferedWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import com.harman.Model.TCPStreamingSparkServer;

public class Consumer implements Runnable{
	
	
private static Consumer consumer = null;
private BlockingQueue<AppMessage> queue;
public static Consumer getInstance(BlockingQueue<AppMessage> q)
{
	if (consumer == null)
		consumer = new Consumer(q);
	return consumer;
}
    public Consumer(BlockingQueue<AppMessage> q){
        this.queue=q;
    }

    @Override
    public void run() {
    	System.out.println("Consumer thread running....");
    	while(true){
    		synchronized (queue){
    			while(queue.size() == 0)
    			{
    				try {
    					System.out.println("queue is empty, Consumer waiting.. ");
    					queue.wait();
    					
    				} catch (InterruptedException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
    			}
    			while (queue.size() > 0) {
    				try {
    					TCPStreamingSparkServer sparkServer = TCPStreamingSparkServer.getInstance();
    					Socket activeSocket = sparkServer.getSocket();
    					sparkServer.handleClientRequest(activeSocket, queue.take().getMsg().toString());
    					System.out.println("Messge fed to spark \n");
    				} catch (InterruptedException e) {
    					System.out.println("exception");
    					e.printStackTrace();
    				}
    			}
    		}
    	}
    }
}

