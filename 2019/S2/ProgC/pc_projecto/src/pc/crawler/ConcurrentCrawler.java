package pc.crawler;


import pc.util.UnexpectedException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Concurrent crawler.
 */
public class ConcurrentCrawler extends SequentialCrawler {
    private Set<String> visited = ConcurrentHashMap.newKeySet();
    private AtomicInteger rid = new AtomicInteger(0);


    public static void main(String[] args) throws IOException {
        int threads = args.length > 0 ? Integer.parseInt(args[0]) : 4;
        String url = args.length > 1 ? args[1] : "http://localhost:8123";

        ConcurrentCrawler cc = new ConcurrentCrawler(threads);
        cc.setVerboseOutput(true);
        cc.crawl(url);
        cc.stop();
    }

    /**
     * The fork-join pool.
     */
    private final ForkJoinPool pool;

    /**
     * Constructor.
     *
     * @param threads number of threads.
     * @throws IOException if an I/O error occurs
     */
    public ConcurrentCrawler(int threads) throws IOException {
        pool = new ForkJoinPool(threads);
    }

    @Override
    public void crawl(String root) {
        long t = System.currentTimeMillis();
        log("Starting at %s", root);
        pool.invoke(new TransferTask(root));
        t = System.currentTimeMillis() - t;
        log("Done: %d transfers in %d ms (%.2f transfers/s)",
                visited.size(), t, (1e+03 * visited.size()) / t);
    }

    /**
     * Stop the crawler.
     */
    public void stop() {
        pool.shutdown();
    }

    @SuppressWarnings("serial")
    private class TransferTask extends RecursiveTask<Void> {

        final String path;

        TransferTask(String path) {
            this.path = path;
        }

        @Override
        protected Void compute() {
            List<RecursiveTask<Void>> forks = new ArrayList<>();
            try {
                URL url = new URL(path);
                List<String> links = performTransfer(rid.getAndIncrement(), url);
                for (String link : links) {
                    String newURL = new URL(url, new URL(url, link).getPath()).toString();
                    if (visited.add(newURL)) {
                        RecursiveTask<Void> task = new TransferTask(newURL);
                        forks.add(task);
                        task.fork();
                    }
                }
            } catch (Exception e) {
                throw new UnexpectedException(e);
            }
            for (RecursiveTask<Void> task : forks) {
                task.join();
            }
            return null;
        }

    }
}
