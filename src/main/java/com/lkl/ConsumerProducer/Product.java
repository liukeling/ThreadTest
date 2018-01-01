package com.lkl.ConsumerProducer;

public class Product {
    //产品的使用寿命
    protected long lifeTime = 0l;
    public Product(long lifeTime){
        this.lifeTime = lifeTime;
    }
}
