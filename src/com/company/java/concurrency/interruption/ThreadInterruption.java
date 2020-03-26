package com.company.java.concurrency.interruption;

import java.util.concurrent.TimeUnit;

public class ThreadInterruption {

    public static void main(String[] args){
        Task task = new Task();

        Thread thread = new Thread(task);
        thread.start();

        try{
            TimeUnit.SECONDS.sleep(3);
            thread.interrupt();
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        System.out.println("Inside main ....");

    }
}

class Task implements Runnable{

    @Override
    public void run() {
        System.out.println("Inside run ....");

        try{
            TimeUnit.SECONDS.sleep(9);

            //This code will not execute it will throw Interrupted Exception
            System.out.println("current thread state : " + Thread.currentThread().isInterrupted());

        }catch (InterruptedException e)
        {
            System.out.println("Interrupted !!" + Thread.currentThread().isInterrupted());
        }
        go();
    }

    private void go(){
        System.out.println("inside go method ...");

        more();
    }

    private void more()
    {
        System.out.println("inside more method ....");
    }
}
