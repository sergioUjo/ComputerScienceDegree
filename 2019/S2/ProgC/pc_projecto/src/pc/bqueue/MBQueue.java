package pc.bqueue;

import pc.util.UnexpectedException;

/**
 * Monitor-based implementation of queue.
 *
 * @param <E> Type of elements.
 */
public class MBQueue<E> implements BQueue<E> {

    protected E[] array;
    protected int head, size;

    /**
     * Constructor.
     *
     * @param initialCapacity Initial queue capacity.
     * @throws IllegalArgumentException if {@code capacity <= 0}
     */
    @SuppressWarnings("unchecked")
    public MBQueue(int initialCapacity) {
        head = 0;
        size = 0;
        array = (E[]) new Object[initialCapacity];
    }

    @Override
    public synchronized int capacity() {
        return array.length;
    }

    @Override
    public synchronized int size() {
        return size;
    }


    @Override
    public void add(E elem) {
        synchronized (this) {
            try {
                while (size == array.length) {
                    wait();
                }
                array[(head + size) % array.length] = elem;
                size++;
                notifyAll();
            } catch (InterruptedException e) {
                throw new UnexpectedException(e);
            }
        }
    }

    @Override
    public E remove() {
        synchronized (this) {
            try {
                while (size == 0) {
                    wait();
                }
                E elem = array[head];
                array[head] = null;
                head = (head + 1) % array.length;
                size--;
                notifyAll();
                return elem;
            } catch (InterruptedException e) {
                throw new UnexpectedException(e);
            }
        }
    }

    /**
     * Test instantiation.
     */
    public static final class Test extends BQueueTest {
        @Override
        <T> BQueue<T> createBQueue(int capacity) {
            return new MBQueue<>(capacity);
        }
    }
}
