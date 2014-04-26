package org.jpg.concurrency.boundedbuffer;

public class WaitNotifyBoundBuffer<V> extends AbstractBoundedBuffer<V> {

	private static final Object lock = new Object();

	public WaitNotifyBoundBuffer(int size) {
		super(size);
		// TODO Auto-generated constructor stub
	}

	public void put(V v) throws InterruptedException {
		synchronized (lock) {
			while (isFull())
				wait();
			doPut(v);
			notifyAll();
		}
	}

	// BLOCKS-UNTIL: not-empty
	public V take() throws InterruptedException {
		synchronized (lock) {
			while (isEmpty())
				wait();
			V v = doTake();
			notifyAll();
			return v;
		}
	}

}
