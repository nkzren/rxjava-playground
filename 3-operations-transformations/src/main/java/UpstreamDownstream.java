import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @see <a href="https://stackoverflow.com/questions/53441858/explain-about-downstream-and-upstream-in-rxjava">TLDR: Downstream and Upstream</a>
 */
public class UpstreamDownstream {
	public static void main(String[] args) throws InterruptedException {
		/*
					upstream			downstream
			source <--------- operator -----------> consumer/further operators
		*/

		// Everything on the main thread
		Observable.just(1,2,3,4,5)
				.map(n -> n*3)
				.subscribe(triple -> System.out.println(Thread.currentThread() + " " + triple));

		System.out.println("SubscribeOn");

		// SubscribeOn - affects downstream and upstream
		Observable.just(1, 2, 3, 4, 5)
				.map(n -> {
					System.out.println(Thread.currentThread());
					return n * 3;
				})
				.subscribeOn(Schedulers.computation())
				.map(triple -> {
					System.out.println(Thread.currentThread());
					return "Tripled value: " + triple;
				})
				.subscribe(triple -> System.out.println(Thread.currentThread() + " " + triple));

		System.out.println("ObserveOn");

		// ObserveOn - affects only downstream
		Observable.just(1, 2, 3, 4, 5)
				.map(n -> n * 3)
				.observeOn(Schedulers.computation())
				.map(triple -> {
					System.out.println(Thread.currentThread());
					return "Tripled value: " + triple;
				})
				.subscribe(triple -> System.out.println(Thread.currentThread() + " " + triple));

		Thread.sleep(3000);
	}
}
