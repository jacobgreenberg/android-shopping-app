package dev.prsm.shopping_app;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorLogin extends java.util.concurrent.ThreadPoolExecutor
{
    public ThreadPoolExecutorLogin(int corePoolSize, int maxPoolSize, long keepAliveTime,
                                         TimeUnit unit, BlockingQueue<Runnable> workQueue)
    {
        super(corePoolSize, maxPoolSize, keepAliveTime, unit, workQueue);
    }

    protected void beforeExecute(Thread thread, Runnable runnable)
    {
        super.beforeExecute(thread, runnable);
    }

    protected void afterExecute(Runnable runnable, Throwable throwable)
    {
        super.afterExecute(runnable, throwable);
        LoginTask task = (LoginTask) runnable;
        task.updateView();
    }
}