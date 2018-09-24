import java.util.Arrays;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        String[] arr = new String[]{"1", "2", "3"};
        System.out.println(Arrays.toString(arr));
        swap(arr, 0, 1);
        System.out.println(Arrays.toString(arr));

        ArrayList<String> arrList  = toArrayList(arr);
        System.out.println(arrList.toString());

        Box<Apple> appleBox1 = new Box<>();
        for (int i = 0; i < 5; i++) {
            appleBox1.addFruit(new Apple());
        }
        Box<Apple> appleBox2 = new Box<>();
        for (int i = 0; i < 6; i++) {
            appleBox2.addFruit(new Apple());
        }
        System.out.println("AppleBox1 weight: " + appleBox1.getWeight());
        System.out.println("AppleBox2 weight: " + appleBox2.getWeight());
        System.out.println("AppleBox1 > AppleBox2: " + appleBox1.compare(appleBox2));

        appleBox1.emptyBox(appleBox2);
        System.out.println(appleBox2.getWeight());
    }

    static <T> void swap(T[] arr, int x, int y) {
        T e = arr[x];
        arr[x] = arr[y];
        arr[y] = e;
    }
    static <T> ArrayList<T> toArrayList(T[] arr) {
        ArrayList<T> arrList = new ArrayList<>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            arrList.add(arr[i]);
        }
        return arrList;
    }

}
