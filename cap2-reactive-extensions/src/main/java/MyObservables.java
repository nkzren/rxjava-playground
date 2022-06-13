import io.reactivex.rxjava3.core.Observable;

public class MyObservables {

	public static void main(String[] args) {
		Observable<String> just = MyObservables.just("Banana");

		just.subscribe(System.out::println);
	}

	public static <T> Observable<T> never(T x) {
		return Observable.create(subscriber ->  {

		});
	}

	public static Observable<Integer> range(int start, int amount) {
		return Observable.create(subscriber -> {
			for (int i = 0; i < amount; i++) {
				subscriber.onNext(start + i);
			}
			subscriber.onComplete();
		});
	}

	public static <T> Observable<T> just(T x) {
		return Observable.create(subscriber -> {
			subscriber.onNext(x);
			subscriber.onComplete();
		});
	}

}
