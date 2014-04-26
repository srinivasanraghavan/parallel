package org.jpg.concurrency.deadlockAviodance;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class AccountTransfer {

	/**
	 * Code thats prone to deadlocks
	 * 
	 * @param fromAccount
	 * @param toAccount
	 * @param amount
	 * @throws InsufficientFundsException
	 */
	public void transferMoneySynchronised(Account fromAccount,
			Account toAccount, DollarAmount amount) throws Exception {
		synchronized (fromAccount) {
			synchronized (toAccount) {
				if (fromAccount.getBalance().compareTo(amount) < 0)
					throw new Exception();
				else {
					fromAccount.debit(amount);
					toAccount.credit(amount);
				}
			}
		}
	}

	/**
	 *
	 * Code free From deadLock
	 * 
	 * @param fromAcct
	 * @param toAcct
	 * @param amount
	 * @param timeout
	 * @param unit
	 * @return
	 * @throws Exception
	 * @throws InterruptedException
	 */
	public boolean transferMoneytryLock(Account fromAcct, Account toAcct,
			DollarAmount amount, long timeout, TimeUnit unit) throws Exception,
			InterruptedException {

		long stopTime = System.nanoTime() + unit.toNanos(timeout);
		while (true) {
			if (fromAcct.lock.tryLock()) {
				try {
					if (toAcct.lock.tryLock()) {
						try {
							if (fromAcct.getBalance().compareTo(amount) < 0)
								throw new Exception();
							else {
								fromAcct.debit(amount);
								toAcct.credit(amount);
								return true;
							}
						} finally {
							toAcct.lock.unlock();
						}
					}
				} finally {
					fromAcct.lock.unlock();
				}
			}
			if (System.nanoTime() < stopTime)
				return false;

		}
	}

}

class Account {

	private DollarAmount balance;

	public ReentrantLock lock;

	public void debit(DollarAmount amount) {

	}

	public void credit(DollarAmount amount) {

	}

	public DollarAmount getBalance() {
		return balance;
	}

}

class DollarAmount implements Comparable<DollarAmount> {

	private Long amount;

	@Override
	public int compareTo(DollarAmount o) {
		if (o.amount.equals(this.amount)) {
			return 1;
		}

		return -1;

	}

}
