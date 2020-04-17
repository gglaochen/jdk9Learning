package Flow.demo;

import java.util.concurrent.Flow;

/**
 * 订阅者
 *
 * @author ChenHanLin 2020/4/17
 */
public class DockerXDemoSubscriber<T> implements Flow.Subscriber<T> {
    private String name;
    private Flow.Subscription subscription;
    final long bufferSize;
    long count;

    public DockerXDemoSubscriber(long bufferSize, String name) {
        this.bufferSize = bufferSize;
        this.name = name;
    }

    /**
     * 在给定的 Subscription 想要使用 Subscriber 其他方法的前提下，必须先调这个方法
     */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        // 在消费一般的时候重新请求
        //count = bufferSize - bufferSize / 2;
        (this.subscription = subscription).request(bufferSize);
        System.out.println("开始 onSubscribe 订阅");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取 Subscription 的下一个元素
     */
    @Override
    public void onNext(T item) {
//        if (--count <= 0) subscription.request(count = bufferSize - bufferSize / 2);
        System.out.println("  #####  " + Thread.currentThread().getName() + " name: " + name + "  item:  " + item + "  ######  ");
        System.out.println(name + "  received: " + item);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 当 Publisher 或者 Subscription 遇到了不可恢复的错误时，会调用这个方法，然后 Subscription 就不能再调用 Subscriber 的其他方法了
     */
    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * 完成方法
     * 在调用这个方法后，Subscription 就不能再调用 Subscriber 的其他方法了
     */
    @Override
    public void onComplete() {
        System.out.println("Completed");
    }

    public String getName() {
        return name;
    }

    public Flow.Subscription getSubscription() {
        return subscription;
    }
}
