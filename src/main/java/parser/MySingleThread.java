package parser;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class MySingleThread implements Callable<Set<String>>{

	private BlockingQueue<String> queue = new ArrayBlockingQueue<>(1024);
	
	public void addNewLinkToQueue(String link) throws InterruptedException{
		queue.put(link);
	}
	
	public String getLinkFromQueue() throws InterruptedException{
		return queue.take();
	}
	
	public int getCurrentQueueSize() {
		return queue.size();
	}
	
	@Override
	public Set<String> call() throws Exception {
		
		while(queue.size()<1){
            try {
                con.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
		// TODO Auto-generated method stub
		return null;
	}

}
