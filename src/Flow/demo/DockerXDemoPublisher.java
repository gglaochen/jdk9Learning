package Flow.demo;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow;
import java.util.concurrent.Future;

/**
 * 发布者
 *
 * @author ChenHanLin 2020/4/17
 */
public class DockerXDemoPublisher<T> implements Flow.Publisher<T>, AutoCloseable {
    private final ExecutorService executor;
    private CopyOnWriteArrayList<DockerXDemoSubscription> list = new CopyOnWriteArrayList<>();

    public DockerXDemoPublisher(ExecutorService executor) {
        this.executor = executor;
    }

    public void submit(T item) {
        System.out.println("****** 开始发布元素 item：" + item + "*********");
        list.forEach(x -> {
            x.future = executor.submit(() -> {
                x.subscriber.onNext(item);
            });
        });
    }

    @Override
    public void close() throws Exception {
        list.forEach(e -> {
            e.future = executor.submit(() -> {
                e.subscriber.onComplete();
            });
        });
    }

    /**
     * 订阅方法
     */
    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        // 1.订阅后会执行->subscription的request方法->subscriber的onNext方法
        subscriber.onSubscribe(new DockerXDemoSubscription<>(subscriber, executor));
        // 2.添加到 发布者维护的Subscription列表中(发布数据 和 cancel的时候需要用到)
        list.add(new DockerXDemoSubscription<>(subscriber, executor));
    }

    /**
     * 连接发布者和订阅者的中间人
     */
    static class DockerXDemoSubscription<T> implements Flow.Subscription {
        private final Flow.Subscriber<? super T> subscriber;
        private final ExecutorService executor;
        //维护future主要是为了执行Subscription::cancel的时候可以直接终止正在进行的任务
        private Future<?> future;
        private T item;
        private boolean completed;

        public DockerXDemoSubscription(Flow.Subscriber<? super T> subscriber, ExecutorService executor) {
            this.subscriber = subscriber;
            this.executor = executor;
        }

        /**
         * 获取n个元素
         */
        @Override
        public void request(long n) {
            if (n != 0 && !completed) {
                if (n < 0) {
                    IllegalArgumentException ex = new IllegalArgumentException();
                    executor.execute(() -> subscriber.onError(ex));
                } else {
                    future = executor.submit(() -> {
                        subscriber.onNext(item);
                    });
                }
            } else {
                subscriber.onComplete();
            }
        }

        /**
         * Subscription停止接收消息
         */
        @Override
        public void cancel() {
            completed = true;
            if (future != null && !future.isCancelled()) {
                this.future.cancel(true);
            }
        }
    }
}
