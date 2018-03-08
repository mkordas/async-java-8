import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HandleTutor extends CompletableFutureBase {

    @Test
    public void testHandle() throws Exception {
        CompletableFuture<String> cf =
            CompletableFuture.supplyAsync(() -> {
                    if (Math.random() < 0.01)
                        throw new RuntimeException("failed");
                    return "success";
                }
            );

        cf.cancel(true);

        System.out.println(cf.handle((String res, Throwable ex) ->
            {
                if (ex != null) System.out.println(ex);
                else System.out.println(res);
                return ex != null ? "default value" : res;
            }
        ).get());
    }

}
