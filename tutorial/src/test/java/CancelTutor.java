import java.util.concurrent.CompletableFuture;

public class CancelTutor extends CompletableFutureBase {
    public void test() {
        CompletableFuture<Integer> future =
            CompletableFuture.supplyAsync(this::slowInit)
                .thenApplyAsync(this::slowIncrement)
                .thenApplyAsync(this::slowIncrement); // last is cancelled
        future.cancel(true); // mayInterruptIfRunning - no change
        System.out.println(future.isCancelled()); // true
        try {
            future.get(); // CancellationException
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new CancelTutor().test();
    }
}
