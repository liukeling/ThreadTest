package com.lkl.ConsumerProducer;

import java.util.Random;

public class Producer extends Thread {
    protected String bankString = "               ";
    //对外开放的通知生产者继续生产的对象
    public static final Object waitObj = new Object();
    //保证总数正确的锁对象
    protected static final Object lockObj = new Object();
    //获取产品仓库
    protected Warehouse warehouse = Warehouse.get_warehouse();
    //生产者实际生产的个数
    protected static int myCount = 0;
    //需要生产的总数
    protected static int count = 0;
    //当前线程总数
    protected static int countThread = 0;
    public static void setCount(int count){
        synchronized (waitObj) {
            //如果没有线程运行就可以设置总数
            if(countThread == 0) {
                Producer.count = count;
            }
        }
    }
    @Override
    public void run() {
        synchronized (waitObj) {
            countThread ++;
        }
        try {
            execute();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(bankString+getName()+"线程异常退出");
        }finally {
            synchronized (waitObj) {
                countThread--;
            }
        }
    }
    protected void execute(){
        //用来生产随机数
        Random random = new Random();
        //如果仓库为空就结束
        if (warehouse == null) {
            System.out.println(bankString+"仓库为空！");
            return;
        }
        //循环生产
        while (true) {
            synchronized (lockObj) {
                if (count == 0) {
                    //产品生产完毕
                    System.out.println(bankString+getName() + "产品已全部生产完毕");
                    break;
                }
                count--;
            }
            //生产耗时
            long time = random.nextInt(1000);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
            //生产出产品
            Product producer = new Product(random.nextInt(1000));
            //添加到仓库
            boolean ok = false;
            //没有添加成功就一直进行等待
            while (!ok) {
                ok = warehouse.add(producer);
                if (!ok) {
                    try {
                        System.out.println(bankString+getName() + "仓库满了！等待消费者消费。。。。。。");
                        //如果仓库满了那么就等待消费消费
                        synchronized (waitObj) {
                            waitObj.wait();
                        }
                    } catch (InterruptedException e) {
//                        e.printStackTrace();
                        System.out.println(bankString+getName() + "线程等待异常!");
                        break;
                    }
                } else {
                    //只有真正添加进去才算生产了
                    myCount++;

                    System.out.println(bankString+getName()+"生产了一个产品");
                    //有产品了，让消费者抢购
                    try {
                        synchronized (Consumer.waitObj) {
                            Consumer.waitObj.notifyAll();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }
}
