package backend.game;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class BoundedBuffer<T> {
	private LinkedList<T> buf;
	private Semaphore mutex, count;
	
	public BoundedBuffer() {
		buf = new LinkedList<T>();
		mutex = new Semaphore(1);
		count = new Semaphore(0);
	}
	
	public void push(T item) {
		try {
			mutex.acquire();
			buf.push(item);
			mutex.release();
			count.release();
		}catch(InterruptedException e) {}
	}
	
	public T pop() {
		T top = null;
		try {
			count.acquire();
			mutex.acquire();
			top = buf.pop();
			mutex.release();
		}catch(InterruptedException e) {}
		return top;
	}
}
