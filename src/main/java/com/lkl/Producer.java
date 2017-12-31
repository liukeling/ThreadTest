package com.lkl;

import java.util.List;

public class Producer extends Thread {
    protected List<Warehouse> warehouse;
    public Producer(List<Warehouse> warehouse){
            this.warehouse = warehouse;
    }
    @Override
    public void run() {
        if(warehouse == null){
            System.out.println("仓库为空！");
            return;
        }
//        while(){
//
//        }
    }
}
