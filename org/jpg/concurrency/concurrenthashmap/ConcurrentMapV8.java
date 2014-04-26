package org.jpg.concurrency.concurrenthashmap;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * ConcurrentHashMap v8 new feautures .
 * 
 * @author srinirag
 *
 */
public class ConcurrentMapV8 {
	private final ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();

	public ConcurrentMapV8() {
		concurrentHashMap.put("hi", "Its me");
	}

	/**
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		ConcurrentMap concurrentMap = new ConcurrentMap();
		concurrentMap.concurrentHashMapforEach();
		

	}

	/**
	 * Bulk data operation .. Searching a map with a key and value
	 * 
	 * Uses a BiFunction<? super K, ? super V, ? extends U>
	 * 
	 */

	public void concurrentHashMapSearchFunctionality() {

		String result = concurrentHashMap.search(Runtime.getRuntime()
				.availableProcessors(), (k, v) -> {
			if (k.equals("hi") && v.equals("Its me")) {
				return "I am here";
			} else {
				return "I am aint here";
			}

		});
		System.out.println(result);

		
	}

	/**
	 * Bulk data Operation uses a Function<? super K, ? extends U>
	 * 
	 * 
	 */
	public void concurrentHashMapSearchSearchKeyFunctionality() {

		concurrentHashMap.searchKeys(
				Runtime.getRuntime().availableProcessors(), (k) -> {
					if (k.equals("hi")) {
						return "I am here";
					}

					return "I am aint here";
				});
	}

	/**
	 * Uses a BiConsumer<T, U> which accepts a two args return type is void
	 * 
	 */

	public void concurrentHashMapforEach() {
		concurrentHashMap.forEach(Runtime.getRuntime().availableProcessors(), (
				k, v) -> {

			if (k.equals("hi")) {
				System.out.println(k);
			}

		});

	}

	/**
	 * Creating hasSet from a Map .
	 */

	public Set<String> createHashSet() {

		return concurrentHashMap.<String> newKeySet();

	}

	
}
