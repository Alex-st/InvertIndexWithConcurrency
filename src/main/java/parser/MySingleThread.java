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

	private Map<String, Boolean> linkQueue;
	private Lock lock;
    private Condition con;
	private ThreadPoolManager threadPoolManager;
	
	public MySingleThread(ThreadPoolManager tpm) {
		threadPoolManager = tpm;
		linkQueue = new ConcurrentHashMap<>();
		lock = new ReentrantLock();
	    con = lock.newCondition();
	}
	
	public void addNewLinkToQueue(String link) throws InterruptedException{
		linkQueue.put(link, true);
		con.signal();
	}
	
	public void addNewLinkToQueue(String link, Boolean isInitial) throws InterruptedException{
		linkQueue.put(link, isInitial);
		con.signal();
	}
	
	public Map.Entry<String, Boolean> getLinkFromQueue() throws InterruptedException{ 
		return linkQueue.entrySet().iterator().next();
	}
	
	public int getCurrentQueueSize() {
		return linkQueue.size();
	}
	
	@Override
	public void run() {
		
		Map<String, String> listOfWords = new ConcurrentHashMap<>();
		String curLink = null;
		Boolean isLinkInitial = true;
		
		while (!Thread.currentThread().isInterrupted()) {
			
			while(linkQueue.size()<1){
	            try {
	                con.await();
	            } catch (InterruptedException ex) {
//	                Logger.getLogger(MySingleThread.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
			
			try {
				curLink = getLinkFromQueue().getKey();
				isLinkInitial = getLinkFromQueue().getValue();
				linkQueue.remove(curLink);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// perform processing of all internal page links(only to one step-in)
			if (isLinkInitial) {
				if (SoupParser.getAllLinks(curLink).size() > 0) {
					for (String i: SoupParser.getAllLinks(curLink)) {
						try {
							addNewLinkToQueue(i, false);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			for (String i : SoupParser.getAllWordsFromPage(curLink)) {
				listOfWords.put(i, curLink);
			}
			
			threadPoolManager.addItemToInvertIndex(listOfWords);
			
		}

	}

}
