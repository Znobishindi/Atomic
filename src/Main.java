import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        LongAdder sum1 = new LongAdder();
        LongAdder sum2 = new LongAdder();
        LongAdder sum3 = new LongAdder();
        LongAdder sumAll = new LongAdder();
        final ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        Shop shop1 = new Shop("Магазин 1");
        Shop shop2 = new Shop("Магазин 2");
        Shop shop3 = new Shop("Магазин 3");

        shop1.start();
        shop2.start();
        shop3.start();

        shop2.join();
        shop1.join();
        shop3.join();

        Arrays.stream(shop1.getDailyReport()).forEach(i -> service.submit(() -> sum1.add(i)));
        Arrays.stream(shop2.getDailyReport()).forEach(i -> service.submit(() -> sum2.add(i)));
        Arrays.stream(shop3.getDailyReport()).forEach(i -> service.submit(() -> sum3.add(i)));
        service.awaitTermination(100,TimeUnit.MILLISECONDS);

        sumAll.add(sum1.sum());
        sumAll.add(sum2.sum());
        sumAll.add(sum3.sum());

        System.out.printf("Выручка %s составила %d\n", shop1.getName(), sum1.sum());
        System.out.printf("Выручка %s составила %d\n", shop2.getName(), sum2.sum());
        System.out.printf("Выручка %s составила %d\n", shop3.getName(), sum3.sum());
        System.out.printf("Общая выручка магазинов составила %d\n", sumAll.sum());
        service.shutdown();

    }


}
