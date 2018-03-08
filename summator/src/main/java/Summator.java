import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * TASK:
 *
 * Create summator for all data in .txt files in project folder.
 * Files contains integer numbers separated by spaces
 * (there could be trailing spaces which could be removed with String.trim).
 * In case of error in file (NumberFormatException) file should be ignored.
 * Result should be saved to file result.dat.
 * Result should be 2800, 5.txt should be ignored.
 */
public class Summator {

    static CompletableFuture<Integer> process(String data) {
        return CompletableFuture.completedFuture(
                Arrays.asList(data.split(" ")).stream()
                        .mapToInt(Integer::parseInt)
                        .sum()
        );
    }

    public static void main(String[] args) throws IOException {

        // Here you can find the demonstration of already implemented methods

        // use AsyncFiles.listFilesInFolder to get list of all files
        String[] files = AsyncFiles.listFilesInFolder(".", "txt");
        Arrays.asList(files).forEach(System.out::println);

        // use Summator.process to calculate the sum of all values in file
        // execute it in separate thread (use Async)
        CompletableFuture.supplyAsync(()->"700 200 100")
                .thenCompose(Summator::process)
                .thenAccept(System.out::println);

        // use AsyncFiles.readFromFile to perform asynchronous read
        AsyncFiles.readFromFile(files[0])
            .thenAccept(System.out::println)
            .join();

        // use AsyncFiles.writeToFile to perform asynchronous write
        AsyncFiles.writeToFile("result.dat", "123")
            .join();

    }
}
