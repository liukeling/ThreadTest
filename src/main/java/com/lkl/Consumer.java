package com.lkl;

public class Consumer extends Thread{
    //用来进行通知消费者进行消费的对象
    public static final Object waitObj = new Object();
    //保证总数正确的锁对象
    protected static final Object lockObj = new Object();
    //获取产品仓库
    protected Warehouse warehouse = Warehouse.get_warehouse();
    //需要消费的总数
    protected  static int count = 0;
    //实际消费的总数  单个线程
    protected int myCount = 0;
    //消费者总数
    protected  static int consumerCount = 0;
    public static void setCount(int count){
        synchronized (waitObj) {
            if(consumerCount == 0) {
                Consumer.count = count;
            }
        }
    }
    public Consumer(){

    }
    @Override
    public void run() {
        synchronized (waitObj){
            consumerCount ++;
        }
        try{
            execute();
        }catch(Exception e){
//            e.printStackTrace();
            System.out.println(getName()+"消费者异常退出!");
        }finally {
            consumerCount --;
            System.out.println(getName()+"本次抢购现场我抢购了"+myCount+"个产品");
        }
    }
    public void execute(){
        while(true) {
            //先对总数进行判断然后消费
            synchronized (lockObj) {
                if (count == 0) {
                    break;
                }
                count --;
            }
            Product product = warehouse.get();
            while(product == null){
                try {
                    System.out.println(getName()+"这产品这么火爆，这次没抢到，下次我一定要抢到!");
                    synchronized (waitObj) {
                        waitObj.wait();
                    }
                    product = warehouse.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //等待异常
                    System.out.println(getName()+"这次的产品不好，等下次产品！");
                }
            }
            //使用产品
            try {
                if(product != null) {
                    myCount ++;
                    //消费了一个，仓库如果是满的那么现在不是的了，那就让生产者继续生产
                    synchronized (Producer.waitObj) {
                        Producer.waitObj.notifyAll();
                        System.out.println(getName()+"使用了一个产品");
                    }
                    Thread.sleep(product.lifeTime);
                }
            } catch (InterruptedException e) {
//                e.printStackTrace();
                System.out.println(getName()+"产品质量有问题，我要投诉！");
            }
        }
    }
}
