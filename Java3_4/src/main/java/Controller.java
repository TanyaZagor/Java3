public class Controller {

    private char currentSymbol = 'A';

    public char getCurrentSymbol() {
        return currentSymbol;
    }

    public void setCurrentSymbol(char symbol) {
        if(symbol == 'A')
            currentSymbol = 'B';
        else if (symbol =='B')
            currentSymbol = 'C';
        else if (symbol == 'C')
            currentSymbol = 'A';
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        new AbcThreads('A', controller).start();
        new AbcThreads('B', controller).start();
        new AbcThreads('C', controller).start();
    }

}
