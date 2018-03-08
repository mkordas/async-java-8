import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;

public class ComposeTutor extends CompletableFutureBase {

    public CompletableFuture<Integer> getSlow() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(1);
    }

    public CompletableFuture<Integer> incSlow(int i) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(i + 1);
    }

    public static void main(String[] args) {
        ComposeTutor t = new ComposeTutor();
        t.getSlow()
            .thenApply(r -> t.incSlow(r))
            .thenAccept(System.out::println);
    }

    /**
     * 1) Use future1.thenCompose() to put result of future1 into thenCompose
     * 2) perform slowIncrement for thenCompose
     */
    public void promiseTestCompose2() throws Exception {
        CompletableFuture<Integer> future1 =
            CompletableFuture.supplyAsync(this::slowInit) // 1
                .thenApply(this::slowIncrement); // 2

        CompletableFuture<Integer> thenCompose = null;

        int result = thenCompose.get();
        System.out.println(result);
        assertEquals(result, 3);
    }

}
