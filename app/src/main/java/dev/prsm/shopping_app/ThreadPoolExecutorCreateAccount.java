package dev.prsm.shopping_app;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorCreateAccount extends ThreadPoolExecutor
{
    private CreateAccount createAccount;

    public ThreadPoolExecutorCreateAccount(int corePoolSize, int maxPoolSize, long keepAliveTime,
                                           TimeUnit unit, BlockingQueue<Runnable> workQueue,
                                           CreateAccount createAccount)
    {
        super(corePoolSize, maxPoolSize, keepAliveTime, unit, workQueue);
        this.createAccount = createAccount;
    }


    protected void beforeExecute(Thread thread, Runnable runnable)
    {
        super.beforeExecute(thread, runnable);
    }

    protected void afterExecute(Runnable runnable, Throwable throwable)
    {
        super.afterExecute(runnable, throwable);
        CreateAccountTask task = (CreateAccountTask) runnable;
        task.updateView();
    }
}
