
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] arr = {2, 1, 4 , 5, 7};
        int[] arrAfter = findFour(arr);
        System.out.println(Arrays.toString(arrAfter));
        System.out.println(findFourAndOne(arr));
    }
    public static int[] findFour(int[] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] == 4) {
                return Arrays.copyOfRange(arr, i + 1, arr.length);
            }
        }
        throw new RuntimeException("No 4");
    }

    public static boolean findFourAndOne(int[] arr){
        boolean result = false;
        for (int i : arr) {
            if (i == 4 || i == 1){
                result = true;
            }
        }
        return result;
    }
}
