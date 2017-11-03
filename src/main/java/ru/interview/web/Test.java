package ru.interview.web;


import java.util.*;

/**
 * Created by raster on 15.12.16.
 */
/*class myThread implements Runnable{

    public void run() {
        System.out.println("Вторичный поток!");
    }
}

public class Test {

    static myThread thread;

    public static void main(String[] args) {

        thread = new myThread();

        Thread mythread = new Thread(thread);

        mythread.start();

        System.out.println("Основной поток!");
    }
}*/


class myThread extends Thread{

    public void run() {
        for (int i = 0; i < 5; i++) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Вторичный поток!");
        }
    }
}

public class Test {

    public static void main(String[] args) {

        int [] list = {1,2,3};

        List newlist = Arrays.asList(list);

        newlist.contains(list);


        myThread mythread = new myThread();

        mythread.start();


        for (int i = 0; i < 5; i++) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Основной поток!");
        }

        if (mythread.isAlive()){
            try {
                mythread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}