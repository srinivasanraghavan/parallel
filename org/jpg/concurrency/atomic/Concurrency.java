package org.jpg.concurrency.atomic;
import java.util.concurrent.Callable;

/**
 *
 * @author vaichoud
 */
public class Concurrency implements Callable<Long> {
  @Override
  public Long call() throws Exception {
    long sum = 0;
    for (long i = 0; i <= 100; i++) {
      sum += i;
    }
    return sum;
  }
}


