package org.jpg.concurrency.genericparallelism;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

/**
 * 
 * @author srinirag
 *
 *         Functional Interface has only one abstract method.
 *
 * @param <U>
 * @param <V>
 */
@FunctionalInterface
interface ParallelCommand<U, V> {

	public V executeParallely(U u);

	/**
	 * TO work on the concept
	 *
	 * @param <R>
	 * @param command
	 * @return
	 */
	default <R> ParallelCommand<U, R> andThen(ParallelCommand<V, R> command) {
		Objects.requireNonNull(command);
		return (U u) -> command.executeParallely(executeParallely(u));
	}

}
/**
 * 
 * @author srinirag
 *
 */
public class GenericParallelizer {

	List<ParallelCommand<String, String>> commands = new CopyOnWriteArrayList<>();

	public GenericParallelizer() {
		commands.add((u) -> u + "1");
		commands.add((u) -> u + "2");
		commands.add((u) -> u + "3");
		commands.add((u) -> u + "4");
		
	}

	public void executeMyCommands() {

		commands.parallelStream().forEach(
				(cmd) -> System.out.println(cmd
						.executeParallely("I am in parallel")));

	}

	public static void main(String[] args) {

		GenericParallelizer genereicParallelizer = new GenericParallelizer();
		genereicParallelizer.executeMyCommands();
	}
}
