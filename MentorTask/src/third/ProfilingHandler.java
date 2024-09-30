package third;

import java.lang.reflect.Method;

public class ProfilingHandler {
    public static void profileMethod(Object object, String methodName, Class<?>... parameterTypes) {
        try {
            Method method = object.getClass().getMethod(methodName, parameterTypes);

            if (method.isAnnotationPresent(Profiling.class)) {
                long startTime = System.currentTimeMillis();

                System.out.println("Начало выполнения метода: " + method.getName());

                Object result = method.invoke(object, 10);

                long endTime = System.currentTimeMillis();
                System.out.println("Метод " + method.getName() + " завершён за " + (endTime - startTime) + " миллисекунд.");
                System.out.println("Результат: " + result);
            } else {
                System.out.println("Метод не помечен аннотацией @Profiling.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
