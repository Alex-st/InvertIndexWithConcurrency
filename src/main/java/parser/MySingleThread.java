package parser;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MySingleThread implements Runnable{

	private BlockingQueue<String> queue;
	private Lock lock;
    private Condition con;
	private ThreadPoolManager threadPoolManager;
	
	public MySingleThread(ThreadPoolManager tpm) {
		threadPoolManager = tpm;
		queue = new ArrayBlockingQueue<>(1024);
		lock = new ReentrantLock();
	    con = lock.newCondition();
	}
	
	public void addNewLinkToQueue(String link) throws InterruptedException{
		queue.put(link);
		con.signal();
	}
	
	public String getLinkFromQueue() throws InterruptedException{
		return queue.take();
	}
	
	public int getCurrentQueueSize() {
		return queue.size();
	}
	
	@Override
	public void run() {
		
		Map<String, String> listOfWords = new ConcurrentHashMap<>();
		String curLink = null;
		
		while (!Thread.currentThread().isInterrupted()) {
			
			while(queue.size()<1){
	            try {
	                con.await();
	            } catch (InterruptedException ex) {
	                //Logger.getLogger(MySingleThread.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
			
			try {
				curLink = getLinkFromQueue();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			for (String i : SoupParser.getAllWordsFromPage(curLink)) {
				listOfWords.put(i, curLink);
			}
			
			//todo - add import of generated listOfWords to Global Invert List
		}

		//return listOfWords;
	}

}
