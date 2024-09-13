package second;

import java.util.function.Consumer;
import java.util.function.Function;

public class NullChecker<T> {
    private final T object;

    private NullChecker(T object) {
        this.object = object;
    }

    public static <T> NullChecker<T> from(T object) {
        return new NullChecker<>(object);
    }

    public T ifNull(T other) {
        return object == null ? other : object;
    }

    public <X extends Throwable> T ifNullThrow(X exception) throws X {
        if (object == null) {
            throw exception;
        }
        return object;
    }

    public NullChecker<T> map(Consumer<? super T> consumer) {
        if (object != null) {
            consumer.accept(object);
        }
        return this;
    }
}



