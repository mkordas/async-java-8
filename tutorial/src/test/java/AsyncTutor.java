import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AsyncTutor extends CompletableFutureBase {

    public static void main(String[] args) {
        new AsyncTutor().test();
    }

    /**
     * Modify this example by using asynchronous calls so that it was
     * executing in 2 seconds
     */
    @Test
    public void testThenCombineSync() throws Exception {
        long start = System.nanoTime();

        CompletableFuture<Integer> initial =
            CompletableFuture.supplyAsync(this::slowInit);
        CompletableFuture<Integer> future1 =
            initial.thenApply(this::slowIncrement);
        CompletableFuture<Integer> future2 =
            initial.thenApply(this::slowIncrement);
        CompletableFuture<Integer> future3 =
            future1.thenCombine(future2, (x, y) -> x * y);

        //int result = future3.get();
        //System.out.println("result: "+result);

        long elapsedTime = System.nanoTime() - start;
        System.out.printf("%d sec passed",
            TimeUnit.NANOSECONDS.toSeconds(elapsedTime));

        assertEquals(TimeUnit.NANOSECONDS.toSeconds(elapsedTime), 2);

    }

    public void test() {
        CompletableFuture<Integer> initial =
            CompletableFuture.supplyAsync(this::slowInit);
        CompletableFuture<Integer> future1 =
            initial.thenApply(this::slowIncrement);
        CompletableFuture<Integer> future2 =
            initial.thenApply(this::slowIncrement);
        CompletableFuture<Integer> future3 =
            future1.thenCombine(future2, (x, y) -> x * y);
        future3.thenAccept(res -> System.out.println(res));
    }

}
