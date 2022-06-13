import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class FlatMapOperator {
	public static void main(String[] args) throws Exception {
		Observable
				.timer(1, TimeUnit.SECONDS)
				.subscribeOn(Schedulers.computation())
				.flatMap(i -> {
					System.out.println(Thread.currentThread() + i.toString());
					return Observable.just(1, 2, 3);
				})
				.subscribe(System.out::println);

		TimeUnit.SECONDS.sleep(2);

		Observable<String> lipsum = Observable
				.just("Lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit");
		lipsum
				.delay(word -> Observable.timer(word.length() * 100L, TimeUnit.MILLISECONDS))
				.subscribe(System.out::println);

		TimeUnit.SECONDS.sleep(2);
		System.out.println("FlatMap equivalent");

		lipsum
				.flatMap(word -> Observable.timer(word.length() * 100L, TimeUnit.MILLISECONDS).map(x -> word))
						.subscribe(System.out::println);

		TimeUnit.SECONDS.sleep(5);

		// flatMap does not preserve the original order of events!
	}
}
