import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        start(Test1.class);
    }

    public static void start(String clsName) throws ClassNotFoundException {
        start(Class.forName(clsName));
    }
    public static void start(Class cls) {
        Object obj;
        Method beforeSuite = null;
        Method afterSuite = null;
        Set<Method> tests = new TreeSet<>(comparator);
        try {
            obj = cls.getConstructor().newInstance();
            for (Method method : cls.getDeclaredMethods()) {
                if (method.isAnnotationPresent(BeforeSuite.class)) {
                    if (beforeSuite == null) {
                        beforeSuite = method;
                    }
                    else {
                        throw new RuntimeException();
                    }
                } else if (method.isAnnotationPresent(AfterSuite.class)) {
                    if (afterSuite == null) {
                        afterSuite = method;
                    } else {
                        throw new RuntimeException();
                    }
                } else if (method.getAnnotation(Test.class) != null) {
                    tests.add(method);
                }
            }
            if (beforeSuite != null) {
                beforeSuite.invoke(obj);
            }
            for (Method method : tests) {
                method.invoke(obj);
            }
            if (afterSuite != null) {
                afterSuite.invoke(obj);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    private static Comparator<Method> comparator = (m1, m2) -> {
        Integer v1 = m1.getAnnotation(Test.class).value();
        Integer v2 = m2.getAnnotation(Test.class).value();
        return v1.compareTo(v2);
    };
}
