import java.util.concurrent.BrokenBarrierException;

public class Car extends Thread {
    private static int CARS_COUNT = 0;

    private Race race;
    private int speed;

    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        setName("Участник #" + CARS_COUNT);
    }
    @Override
    public void run() {
        try {
            System.out.println(getName() + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(getName() + " готов");
            Race.start.countDown();
            Race.start.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }

        int position = Race.results.incrementAndGet();
        System.out.println(position == 1 ? getName() + " - WIN"
                : getName() + " - занял " + position + " место");
        try {
            Race.finish.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
