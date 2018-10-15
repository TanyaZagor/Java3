public class Test1 {

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Start");
    }

    @Test(value = 3)
    public void test1() {
        System.out.println("Test 1");
    }

    @Test(value = 4)
    public void test2() {
        System.out.println("Test 2");
    }

    @Test(value = 7)
    public void test3() {
        System.out.println("Test 3");
    }

    @Test(value = 1)
    public void test4() {
        System.out.println("Test 4");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("Finish");
    }
}
