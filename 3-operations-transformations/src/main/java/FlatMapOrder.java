import io.reactivex.rxjava3.core.Observable;

import java.time.DayOfWeek;
import java.util.concurrent.TimeUnit;

public class FlatMapOrder {
	static Observable<String> loadRecordsFor(DayOfWeek dayOfWeek) {
		return switch (dayOfWeek) {
			case SUNDAY -> Observable
					.interval(100, TimeUnit.MILLISECONDS)
					.take(5)
					.map(i -> "Sun-" + i);
			case MONDAY -> Observable
					.interval(40, TimeUnit.MILLISECONDS)
					.take(5)
					.map(i -> "Mon-" + i);
			default -> Observable.empty();
		};
	}

	public static void main(String[] args) throws Exception {

		// flatMap items are unordered, and are pushed downstrean whenever they are emitted
		Observable
				.just(DayOfWeek.SUNDAY, DayOfWeek.MONDAY)
				.flatMap(FlatMapOrder::loadRecordsFor)
				.subscribe(System.out::println);

		TimeUnit.SECONDS.sleep(2);


		// concatMap keeps order of emitted events
		Observable
				.just(DayOfWeek.SUNDAY, DayOfWeek.MONDAY)
				.concatMap(FlatMapOrder::loadRecordsFor)
				.subscribe(System.out::println);

		TimeUnit.SECONDS.sleep(2);
	}
}
