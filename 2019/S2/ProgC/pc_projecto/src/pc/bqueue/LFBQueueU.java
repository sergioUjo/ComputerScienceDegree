package pc.bqueue;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Lock-free implementation of queue - unbounded variant.
 *
 * @param <E> Type of elements.
 */
public class LFBQueueU<E> implements BQueue<E> {

    private static final int ADD = 0;
    private static final int REMOVE = 1;
    private static final int SIZE = 2;
    private E[] array;
    private final AtomicInteger head;
    private final AtomicInteger tail;
    private final AtomicBoolean addElementFlag;
    private final Rooms rooms;
    private final boolean useBackoff;


    /**
     * Constructor.
     *
     * @param initialCapacity Initial queue capacity.
     * @param backoff         Flag to enable/disable the use of back-off.
     * @throws IllegalArgumentException if {@code capacity <= 0}
     */
    @SuppressWarnings("unchecked")
    public LFBQueueU(int initialCapacity, boolean backoff) {
        head = new AtomicInteger(0);
        tail = new AtomicInteger(0);
        addElementFlag = new AtomicBoolean();
        array = (E[]) new Object[initialCapacity];
        useBackoff = backoff;
        rooms = new Rooms(3, backoff);
    }

    @Override
    public int capacity() {
        return UNBOUNDED;
    }

    @Override
    public int size() {
        rooms.enter(SIZE);
        int result = tail.get() - head.get();
        rooms.leave(SIZE);
        return result;
    }

    @Override
    public void add(E elem) {
        rooms.enter(ADD);
        while (true) {
            if (addElementFlag.compareAndSet(false, true)) {
                int p = tail.getAndIncrement();
                if (p >= array.length) {
                    E[] temp = array;
                    array = (E[]) new Object[array.length * 2];
                    for (int i = 0; i < temp.length; i++) {
                        array[i % array.length] = temp[i];
                    }
                }
                array[p % array.length] = elem;
                addElementFlag.set(false);
                break;
            }
        }
        rooms.leave(ADD);
    }

    @Override
    public E remove() {
        E elem;
        while (true) {
            rooms.enter(REMOVE);
            int p = head.getAndIncrement();
            if (p < tail.get()) {
                int pos = p % array.length;
                elem = array[pos];
                array[pos] = null;
                break;
            } else {
                // "undo"
                head.getAndDecrement();
                rooms.leave(REMOVE);
            }
        }
        rooms.leave(REMOVE);
        return elem;
    }

    /**
     * Test instantiation.
     */
    public static final class Test extends BQueueTest {
        @Override
        <T> BQueue<T> createBQueue(int capacity) {
            return new LFBQueueU<>(capacity, false);
        }
    }
}
