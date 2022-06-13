import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.internal.schedulers.RxThreadFactory;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Concurrency {

	static ExecutorService threadPoolA = Executors.newFixedThreadPool(10, new RxThreadFactory("pool-A"));
	static Scheduler schedulerA = Schedulers.from(threadPoolA);
	static ExecutorService threadPoolB = Executors.newFixedThreadPool(10, new RxThreadFactory("pool-B"));
	static Scheduler schedulerB = Schedulers.from(threadPoolB);

	/*
		Observable s/ Scheduler -> Naturalmente sincrono e single thread

		Observable c/ subscribeOn -> Pequena qtde de processamentos pesados. Sequencial, mas faz com que a
		task seja executada em outra thread sem bloquear a main

		Observable + flatMap c/ subscribeOn dentro dos items -> Sem garantia de ordem, mas processa todos os eventos
		paralelamente
	 */

	public static void main(String[] args) throws Exception {
		Observable.just("first", "second", "third", "fourth")
				.doOnNext(it -> System.out.println(Thread.currentThread() + ": " + it))
				.subscribeOn(schedulerA)
				.flatMap(item -> {
					System.out.println(Thread.currentThread() + ": Starting process of " + item);
					return process(item).subscribeOn(schedulerB);
				})
				.reduce(Integer::sum)
				.blockingSubscribe(value -> System.out.println(Thread.currentThread() + ": Total characters is " + value));
	}

	private static Observable<Integer> process(String productName) {
		return Observable.fromCallable(() -> {
			System.out.println(Thread.currentThread() + ": Processing " + productName);
			int value = doSomethingAsync(productName);
			System.out.println(Thread.currentThread() + ": Done " + productName);
			return value;
		});
	}

	private static int doSomethingAsync(String s) {
		return s.length();
	}
}
