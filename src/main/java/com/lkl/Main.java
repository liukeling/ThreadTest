package com.lkl;

/**
 * 需求：生产者五个，消费者十个，生产者一共生产一千个产品，消费者进行消费
 * 思路：
 * 1：每个生产者生产的产品放入一个集合中。
 * 2：消费者从集合中拿产品进行消费
 * 3：如果集合没有产品那么进行等待:
 *
 * 生产者：
 * 1：可以生产产品，每个生产者生产的产品不冲突。
 * 消费者：
 * 1：可以消费产品，对集合进行减少
 *
 */
public class Main {
    //本次抢购的产品一个是一千个
    public static int count = 100;
    public static void main(String[] args){
        //设置总数
        Producer.setCount(count);
        Consumer.setCount(count);
        Producer producer1 = new Producer();
        Producer producer2 = new Producer();
        Producer producer3 = new Producer();
        Producer producer4 = new Producer();
        Producer producer5 = new Producer();
        Producer producer6 = new Producer();
        producer1.setName("生产者一号");
        producer2.setName("生产者二号");
        producer3.setName("生产者三号");
        producer4.setName("生产者四号");
        producer5.setName("生产者五号");
        producer6.setName("生产者六号");
        Consumer consumer1 = new Consumer();
        Consumer consumer2 = new Consumer();
        Consumer consumer3 = new Consumer();
        Consumer consumer4 = new Consumer();
        Consumer consumer5 = new Consumer();
        Consumer consumer6 = new Consumer();
        consumer1.setName("消费者一号");
        consumer2.setName("消费者二号");
        consumer3.setName("消费者三号");
        //开始
        producer1.start();
        producer2.start();
        producer3.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
    }
}
