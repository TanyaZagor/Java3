public class AbcThreads extends Thread {
    private final int COUNT = 5;
    private char symbol;
    private Controller controller;

    public AbcThreads(char symbol, Controller controller) {
        this.symbol = symbol;
        this.controller = controller;
    }

    @Override
    public void run() {
        synchronized (controller) {
            for (int i = 0; i < COUNT; i++) {

                try {
                    while (controller.getCurrentSymbol() != symbol) {
                        controller.wait();
                    }
                    System.out.print(symbol);
                    controller.setCurrentSymbol(symbol);
                    controller.notifyAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
