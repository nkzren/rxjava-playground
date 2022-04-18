import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subscribers.DefaultSubscriber;

public class Main {
	public static void main(String[] args) {
		Flowable<Integer> integerFlowable = Flowable.fromArray(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		DefaultSubscriber<Integer> subscriber = new DefaultSubscriber<Integer>() {
			private int count = 0;

			@Override
			public void onNext(Integer integer) {
				System.out.println(integer);
				if (++count >= 5) {
					this.cancel();
				}
			}

			@Override
			public void onError(Throwable throwable) {

			}

			@Override
			public void onComplete() {
				System.out.println("Test");
			}
		};

		integerFlowable.subscribe(subscriber);
	}

}
