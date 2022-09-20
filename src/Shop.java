

public class Shop extends Thread{
    private static final int MAX_SIZE = 10;
    private volatile long[] dailyReport = new long[MAX_SIZE];
    private static final int TRADING_TIME = 2000;


    public Shop(String name) {
        super(name);
    }


    public long[] getDailyReport() {
        return dailyReport;
    }

    @Override
    public void run() {
        System.out.println(getName() + " начал торговлю");
        try {
            Thread.sleep(TRADING_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dailyReport = generateMass(MAX_SIZE);


    }
    public static long[] generateMass(int maxSize) {
        long[] mass = new long[maxSize];
        for (int i = 0; i < mass.length; i++) {
            mass[i] = (long) (Math.random() * 10000);
        }
        return mass;
    }

}
