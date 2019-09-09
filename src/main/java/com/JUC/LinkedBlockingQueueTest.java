package com.JUC;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 基于链表实现的阻塞队列，先进先出队列，有界队列。在创建时如果不指定容量大小，则默认大小为Integer.MAX_VALUE。
 * 内部ReentrantLock  采用的是非公平锁
 */
public class LinkedBlockingQueueTest {
    static final LinkedBlockingQueue block = new LinkedBlockingQueue();

}
