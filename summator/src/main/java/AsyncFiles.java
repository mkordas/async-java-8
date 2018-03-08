import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;

/**
 * This is utility class for working with files asynchronously.
 */
public class AsyncFiles {

    public static String[] listFilesInFolder(String folder, String ext) {
        return new File(folder).list((dir, name) -> name.matches(".*\\.txt"));
    }

    // buffer should be increased in case if file is big
    static public CompletableFuture<String> readFromFile(String file)  {
        AsynchronousFileChannel fileChannel = null;
        try {
            fileChannel = AsynchronousFileChannel.open(Paths.get(file));
        } catch (IOException e) {
            CompletableFuture f = new CompletableFuture();
            f.completeExceptionally(e);
            return f;
        }
        ByteBuffer buffer = ByteBuffer.allocate(100);
        CompletableFuture<Integer> future = read2(fileChannel, buffer, 0, file);
        return future.thenApply(size->new String(buffer.array()));
    }

    static public CompletableFuture<String> writeToFile(String file, String data)  {
        AsynchronousFileChannel fileChannel = null;
        try {
            fileChannel = AsynchronousFileChannel.open(Paths.get(file),
                    StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            CompletableFuture f = new CompletableFuture();
            f.completeExceptionally(e);
            return f;
        }
        ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
        CompletableFuture<Integer> future =
                write(fileChannel, buffer, 0, file);
        return future.thenApply(size->new String(buffer.array()));
    }

    static public <A> CompletableFuture<Integer> read2(
            AsynchronousFileChannel fileChannel,
            ByteBuffer buffer,
            long position,
            A attachment){

        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        fileChannel.read(buffer, position, attachment,
                new CompletionHandler<Integer, A>() {
                    @Override
                    public void completed(Integer size, A attachment) {
                        completableFuture.complete(size);
                    }
                    @Override
                    public void failed(Throwable t, A attachment) {
                        completableFuture.completeExceptionally(t);
                    }
                });
        return completableFuture;
    }

    static public <A> CompletableFuture<Integer> write(
            AsynchronousFileChannel fileChannel,
            ByteBuffer buffer,
            long position,
            A attachment){

        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        fileChannel.write(buffer, position, attachment,
                new CompletionHandler<Integer, A>() {
                    @Override
                    public void completed(Integer size, A attachment) {
                        completableFuture.complete(size);
                    }
                    @Override
                    public void failed(Throwable t, A attachment) {
                        completableFuture.completeExceptionally(t);
                    }
                });
        return completableFuture;
    }
}
