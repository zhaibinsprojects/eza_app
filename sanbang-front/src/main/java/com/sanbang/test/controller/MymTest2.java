package com.sanbang.test.controller;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MymTest2 implements Runnable{
   


	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			System.out.println(Thread.currentThread().getName()+"等待");
			MymTest.lock.readLock().lock();
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
