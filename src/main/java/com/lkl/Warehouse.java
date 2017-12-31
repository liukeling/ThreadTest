package com.lkl;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private static Warehouse _warehouse = new Warehouse();

    public static Warehouse get_warehouse() {
        return _warehouse;
    }
    protected Producer[] producers = new Producer[100];
    protected int curAddItem = 0;
    protected int curGetItem = 0;
    protected int size = 1000;
    //添加产品所
    protected Object lock1 = new Object();
    //获取产品锁
    protected Object lock3 = new Object();

    //直接加入到仓库
    public boolean add(Producer producer){
        synchronized (lock1) {
            if((curAddItem+1)%size == curGetItem % size){
                return false;
            }else{
                producers[curAddItem%size] = producer;
                if(curAddItem == Integer.MAX_VALUE){
                    curAddItem = curAddItem%size;
                }
                curAddItem ++;
                return true;
            }
        }
    }
    //从仓库中获取
    protected Producer get(){
        synchronized (lock3){
            if(curGetItem%size == curAddItem%size){
                return null;
            }
            Producer product = producers[curGetItem%size];
            curGetItem ++;
            if(curGetItem == Integer.MAX_VALUE){
                curGetItem = curGetItem%size;
            }
            return product;
        }
    }
}
