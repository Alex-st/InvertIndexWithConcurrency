package parser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadPoolManager {

	private Map<String, String> invertIndex;
	
	public ThreadPoolManager() {
		invertIndex = new ConcurrentHashMap<>();
	}
	
	public void addItemToInvertIndex(Map<String, String> map) {
		invertIndex.putAll(map);
	}
	
	public Map<String, String> getInvertIndex() {
		return invertIndex;
	}
	
	
	private static void main(String[] args) {
		
	}
}
