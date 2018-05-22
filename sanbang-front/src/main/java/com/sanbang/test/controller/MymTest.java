package com.sanbang.test.controller;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MymTest implements Runnable{
   
	public static ReentrantReadWriteLock lock=new ReentrantReadWriteLock();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        new Thread(new MymTest(), "1").start();;
        new Thread(new MymTest2(), "2").start();;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			System.out.println(Thread.currentThread().getName()+"等待");
			lock.writeLock().lock();
			System.out.println(Thread.currentThread().getName()+"执行");
			try {
				Thread.sleep(5000l);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
