import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.Test;


public class ExecutorTutor extends CompletableFutureBase {
    private void test() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletableFuture<Integer> future =
            CompletableFuture.supplyAsync(this::slowInit, executorService)
                .thenApplyAsync(this::slowIncrement, executorService);
    }

    private CompletableFuture<String> getCF() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
            cf.completeExceptionally(e);
        }
        cf.complete("result");

        return cf;
    }

    public CompletableFuture<String> getCF2() {
        return CompletableFuture.completedFuture("ended");
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new ExecutorTutor().test();
    }

    @Test
    public void getNow() throws ExecutionException, InterruptedException {
        //if (getCF().isDone()) res = getCF().get(); else res = "not ready";
        getCF().getNow("not ready");
    }

    @Test
    public void obtrude() {
        getCF().obtrudeValue("nothing");
    }
}
