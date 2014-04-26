package org.jpg.concurrency.boundedbuffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExplicitConditionQueuedBuffer<V> extends AbstractBoundedBuffer<V> {

	protected final Lock lock = new ReentrantLock();
	// CONDITION PREDICATE: notFull (count < items.length)
	private final Condition notFull = lock.newCondition();
	// CONDITION PREDICATE: notEmpty (count > 0)
	private final Condition notEmpty = lock.newCondition();

	public ExplicitConditionQueuedBuffer(int size) {
		super(size);
		// TODO Auto-generated constructor stub
	}

	public void put(V v) throws InterruptedException {
		lock.lock();
		try {
			while (isFull())
				notFull.await();
			doPut(v);
			notEmpty.signal();
		} finally {
			lock.unlock();
		}

	}

	// BLOCKS-UNTIL: not-empty
	public V take() throws InterruptedException {
		lock.lock();
		try {
			while (isEmpty())
				notEmpty.await();
			V v = doTake();
			notFull.signal();
			return v;
		} finally {
			lock.unlock();
		}
	}

}
