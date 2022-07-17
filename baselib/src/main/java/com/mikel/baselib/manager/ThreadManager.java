package com.mikel.baselib.manager;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadManager {
    private static final String TAG = "ThreadManager";
    private static ThreadManager instence = null;
    private ThreadPoolExecutor mThreadPoolExecutor;
    //核心线程数
    private int corePoolSize = 5;
    //最大线程数
    private int maximumPoolSize = 10;
    //空闲线程的空闲时间
    private long keepAliveTime = 10;
    //等待执行的容器容量大小
    private int capacity = 10;
    //任务等待队列
    private LinkedBlockingQueue waitingTasksQueue =new LinkedBlockingQueue();

    public synchronized static ThreadManager getInstence() {
        if (instence == null) {
            synchronized (ThreadManager.class) {
                if (instence == instence) {
                    instence = new ThreadManager();
                }
            }
        }
        return instence;
    }

    /**
     * 构造方法里面就初始化线程池
     * ArrayBlockingQueue是一个执行任务的容量，当调用mThreadPoolExecutor的execute，容量加1，执行run完后，容量减1
     * ArrayBlockingQueue后面传入true就是以FIFO规则存储：先进先出
     */
    public ThreadManager() {
        if(mThreadPoolExecutor==null) {
            mThreadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(capacity,true), handler);
        }
        mThreadPoolExecutor.execute(runnable);
    }


    /**
     * 往队列里面存入可执行任务
     * @param runnable
     */
    public void postTask(Runnable runnable){
        try {
            waitingTasksQueue.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Runnable runnable=new Runnable() {

        @Override
        public void run() {
            //开启循环
            while(true){
                //取出等待的执行任务
                Runnable taskQueueRunnable = null;
                try {
                    Log.d(TAG,"等待队列Size：" + waitingTasksQueue.size());
                    taskQueueRunnable = (Runnable) waitingTasksQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(runnable != null){
                    mThreadPoolExecutor.execute(taskQueueRunnable);
                }
                Log.d(TAG,"线程池大小"  + mThreadPoolExecutor.getPoolSize());
            }
        }
    };

    /**
     * 拒绝策略
     * 当ArrayBlockingQueue容量过大，就要执行拒绝策略
     *
     */
    private RejectedExecutionHandler handler = new RejectedExecutionHandler(){

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                waitingTasksQueue.put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}

