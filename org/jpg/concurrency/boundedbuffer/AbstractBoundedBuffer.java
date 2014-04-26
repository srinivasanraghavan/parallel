package org.jpg.concurrency.boundedbuffer;

/**
 * 
 * Construts for a bounded buffer .
 *
 * @param <V>
 */
public abstract class AbstractBoundedBuffer<V> {

	/**
	 * Buffer
	 */
	private final V[] buf;
	private int tail;
	private int head;
	private int count;

	@SuppressWarnings("unchecked")
	public AbstractBoundedBuffer(int size) {
		buf = (V[]) new Object[size];
	}

	/**
	 * Puts the value V
	 * 
	 * @param v
	 */
	protected final void doPut(V v) {
		buf[tail] = v;
		if (++tail == buf.length)
			tail = 0;
		++count;
	}

	/**
	 * Takes the value .
	 * 
	 * @return
	 */

	protected final V doTake() {
		V v = buf[head];
		buf[head] = null;
		if (++head == buf.length)
			head = 0;
		--count;
		return v;
	}

	/**
	 * Condition predicate for the isFull .
	 * 
	 * @return
	 */
	protected final boolean isFull() {
		return count == buf.length;
	}

	/**
	 * Condition predicate isEmpty
	 * 
	 * @return
	 */
	protected final boolean isEmpty() {
		return count == 0;
	}

	/**
	 * Put the element in the buffer;
	 * 
	 * @param v
	 * @throws InterruptedException
	 */
	protected abstract void put(V v) throws InterruptedException;

	/**
	 * Takes the element from the buffer .
	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public abstract V take() throws InterruptedException;
}