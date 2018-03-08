public class CompletableFutureBase {
    public Integer slowInit() {
        System.out.println("started task slowInit()");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public Integer slowIncrement(Integer i) {
        System.out.println("slowIncrement()");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(
            "finished increment with result " + (i + 1));
        return 1 + i;
    }

    class MyException extends RuntimeException {
        private int lastValue;

        MyException(int i) {
            lastValue = i;
        }
    }

    public Integer slowIncrementException(Integer i) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new MyException(i);
    }


}
