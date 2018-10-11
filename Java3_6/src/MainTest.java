import org.junit.Assert;
import org.junit.Test;

public class MainTest {
    private Main main;
    @Test
    public void testFindFour(){
        main = new Main();
        Assert.assertArrayEquals(main.findFour(new int[]{2, 1, 4, 5, 7}), new int[]{5, 7});
        Assert.assertArrayEquals(main.findFour(new int[]{4, 1, 1, 5, 7}), new int[]{1, 1, 5, 7});
        Assert.assertArrayEquals(main.findFour(new int[]{2, 1, 2, 5, 4}), new int[]{});
    }
    @Test(expected = RuntimeException.class)
    public void testFindFourException() {
        main = new Main();
        Assert.assertArrayEquals(main.findFour(new int[]{2, 1, 3, 5, 7}), new int[]{});
    }
    @Test
    public void testFindFourAndOne() {
        Assert.assertFalse(main.findFourAndOne(new int[]{2, 7, 7, 5, 7}));
        Assert.assertTrue(main.findFourAndOne(new int[]{2, 1, 0, 5, 7}));
        Assert.assertTrue(main.findFourAndOne(new int[]{2, 9, 4, 5, 7}));
        Assert.assertTrue(main.findFourAndOne(new int[]{2, 1, 4, 5, 7}));
    }
}
