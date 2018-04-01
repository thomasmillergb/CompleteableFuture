import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class App {


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1000);

        runThreadRipper(executorService,100);
        runThreadRipper(executorService,1000);
        runThreadRipper(executorService,10000);
        runThreadRipper(executorService,100000);
        runThreadRipper(executorService,1000000);
        runThreadRipper(executorService,10000000);
    }

    private static void runThreadRipper(ExecutorService executorService,  int ammount) {

        CompletableFuture[] array = IntStream.range(0, ammount)
                .parallel()
                .mapToObj(i -> getSleep(i, executorService)).toArray(CompletableFuture[]::new);

        try {
            CompletableFuture.anyOf(array).thenAccept(System.out::println).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static CompletableFuture getSleep(int i, ExecutorService executorService) {
        return CompletableFuture.supplyAsync(() -> {
            try {
//                System.out.println(i);
//                Thread.sleep((long) (1000 - i ) *20)
                Thread.sleep((long) (Math.random() * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return i;
        }, executorService);
    }


}
