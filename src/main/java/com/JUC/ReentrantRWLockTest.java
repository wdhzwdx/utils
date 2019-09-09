package com.JUC;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 */
public class ReentrantRWLockTest {
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        ReentrantReadWriteLock.ReadLock rLock =  lock.readLock();
        ReentrantReadWriteLock.WriteLock wLock =  lock.writeLock();

        wLock.lock();
    }


}
