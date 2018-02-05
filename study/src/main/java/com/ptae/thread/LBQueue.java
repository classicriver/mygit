/**   
*/
package com.ptae.thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: TODO 自定义实现linkedblockingqueue
 * @author xiesc
 * @date 2018年1月8日
 * @version V1.0  
 */
public class LBQueue<E> {

	private AtomicInteger count = new AtomicInteger();// 计数器
	private final int capacity;// 容量

	private ReentrantLock putLock = new ReentrantLock();// 写锁
	private Condition putCondition = putLock.newCondition();
	private ReentrantLock takeLock = new ReentrantLock();// 读锁
	private Condition takeCondition = takeLock.newCondition();
	private Node<E> head;
	private Node<E> last;

	public LBQueue(int capacity) {
		if (capacity <= 0)
			throw new IllegalArgumentException();
		this.capacity = capacity;
		last = head = new Node<E>(null);
	}

	static class Node<E> {
		E item;
		Node<E> next;
		Node(E e) {
			item = e;
		}
	}

	public void put(E e) {
		Node<E> node = new Node<E>(e);
		putLock.lock();
		try {

			while (count.get() == capacity) {
				putCondition.await();
			}

			if (count.get() < capacity) {
				last = last.next = node;
			}
			int c = count.incrementAndGet();

			if (c < capacity) {
				putCondition.signal();
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			putLock.unlock();
		}
	}

	public E take() {
		E x = null;
		takeLock.lock();
		try {

			while (count.get() == 0) {
				takeCondition.await();
			}
			takeLock.lock();
			Node<E> h = head;
			Node<E> first = h.next;
			h.next = h; // help GC
			head = first;
			x = first.item;
			first.item = null;

			int c = count.getAndDecrement();

			if (c > 1 ) {
				takeCondition.signal();
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			takeLock.unlock();
		}

		return x;
	}
}
