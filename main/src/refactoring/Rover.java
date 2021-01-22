package refactoring;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public class Rover {
	private ViewPoint viewPoint;
	private Map<Order, Action> actions = new HashMap<>();
	{
		this.actions.put(Order.Left, ViewPoint::turnLeft);
		this.actions.put(Order.Right, ViewPoint::turnRight);
		this.actions.put(Order.Forward, ViewPoint::forward);
		this.actions.put(Order.Backward, ViewPoint::backward);
	}

	public Rover(ViewPoint viewPoint){ this.viewPoint = viewPoint; }

	public void go(Order... orders){
		go(Arrays.stream(orders));
	}

	public void go(String instructions){
		go(Arrays.stream(instructions.split("")).
				map(Order::of).filter(Objects::nonNull));
	}

	private ViewPoint go(Stream<Order> ordersStream){
		return ordersStream.filter(Objects::nonNull).reduce(viewPoint, this::execute, (V1, V2) -> null);
	}

	private ViewPoint execute(ViewPoint viewPoint, Order order){
		if(viewPoint == null) return null;
		return actions.get(order).execute(viewPoint);
	}

	@FunctionalInterface
	public interface Action {
		ViewPoint execute(ViewPoint viewPoint);
	}

	public enum Order {
		Forward, Backward, Left, Right;

		public static Order of(String label){
			return of(label.charAt(0));
		}

		public static Order of(char label){
			if (label == 'F') return Forward;
			if (label == 'B') return Backward;
			if (label == 'L') return Left;
			if (label == 'R') return Right;
			return null;
		}
	}


}

