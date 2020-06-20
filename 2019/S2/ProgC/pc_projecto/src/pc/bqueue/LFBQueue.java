package pc.bqueue;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * Lock-free implementation of queue.
 *
 * @param <E> Type of elements.
 */
public class LFBQueue<E> implements BQueue<E> {
    private static final int ADD = 0;
    private static final int REMOVE = 1;
    private static final int SIZE = 2;
    private E[] array;
    private final AtomicInteger head, tail;
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
    public LFBQueue(int initialCapacity, boolean backoff) {
        head = new AtomicInteger(0);
        tail = new AtomicInteger(0);
        array = (E[]) new Object[initialCapacity];
        useBackoff = backoff;
        rooms = new Rooms(3, backoff);
    }

    @Override
    public int capacity() {
        return array.length;
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
        while (true) {
            rooms.enter(ADD);
            int p = tail.getAndIncrement();
            if (p - head.get() < array.length) {
                array[p % array.length] = elem;
                break;
            } else {
                // "undo"
                tail.getAndDecrement();
                rooms.leave(ADD);
            }
        }
        rooms.leave(ADD);
    }

    @Override
    public E remove() {
        E elem = null;
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
            return new LFBQueue<>(capacity, false);
        }
    }
}
