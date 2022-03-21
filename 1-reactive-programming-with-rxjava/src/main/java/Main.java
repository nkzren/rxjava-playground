import io.reactivex.rxjava3.core.Observable;

public class Main {

	public static void thisIsTotallySynchronous() {
		System.out.println("start");

		Observable.create(s -> {
			Thread.sleep(100L);
			s.onNext("Hello World!");
			s.onComplete();
		}).subscribe(System.out::println);

		System.out.println("end");
	}

	public static void synchronousComputations() {
		Observable<Integer> observable = Observable.create(s -> {
			s.onNext(1);
			s.onNext(2);
			s.onNext(3);
			s.onNext(4);
			s.onComplete();
		});

		observable
				.map(i -> Thread.currentThread().getName() + " number: " + i)
				// System.out.println acts as the onNext function here
				.subscribe(System.out::println);

	}

	/**
	 * Observables are lazy: nothing happens until it is subscribed to
	 */
	private static void observableIsLazy() {
		Observable<Object> observable = Observable.create(s -> {
			s.onNext("first");
			s.onNext("second");
		});
		System.out.println("Nothing happened yet");
		try {
			Thread.sleep(500L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Still nothing happened");

		observable.subscribe(System.out::println);

	}

	public static void main(String[] args) {
		thisIsTotallySynchronous();

		synchronousComputations();

		observableIsLazy();
	}

}
