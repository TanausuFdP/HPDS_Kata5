package refactoring;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Rover {
	private Heading heading;
	private String facing;
	private Position position;
	private Map<Order, Action> actions = new HashMap<>();

	public Rover(String facing, int x, int y) {
		this.facing = facing;
		this.heading = Heading.of(facing);
		this.position = new Position(x, y);
	}

	public Rover(Heading heading, int x, int y) {
		this(heading, new Position(x, y));
	}

	public Rover(Heading heading, Position position) {
		this.heading = heading;
		this.position = position;
		this.actions.put(Order.Left, ()-> this.heading = this.heading.turnLeft());
		this.actions.put(Order.Right, ()-> this.heading = this.heading.turnRight());
		this.actions.put(Order.Forward, ()-> this.position = this.position.forward(this.heading));
		this.actions.put(Order.Backward, ()-> this.position = this.position.backward(this.heading));
	}

	public Heading heading(){
		return this.heading;
	}

	public Position position(){
		return this.position;
	}

	public void go(Order... orders){
		go(Arrays.stream(orders));
	}

	public void go(String instructions){
		go(Arrays.stream(instructions.split("")).
				map(s->Order.of(s)).filter(order -> order != null));
	}

	@FunctionalInterface
	public interface Action {
		void execute();
	}

	private void go(Stream<Order> orders){
		orders.forEach(o->actions.get(o).execute());
	}

	public static class Position {
		private final int x;
		private final int y;

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public Position forward(Heading heading) {
			return move(heading,1);
		}

		public Position backward(Heading heading) {
			return move(heading, -1);
		}

		public Position move(Heading heading, int i) {
			switch(heading){
				case North:
					return new Position(this.x,this.y+i);
				case East:
					return new Position(this.x+i,this.y);
				case South:
					return new Position(this.x,this.y-i);
				case West:
					return new Position(this.x-i,this.y);
				default:
					return null;
			}
		}

		@Override
		public boolean equals(Object object) {
			return isSameClass(object) && equals((Position) object);
		}

		private boolean equals(Position position) {
			return position == this || (x == position.x && y == position.y);
		}

		private boolean isSameClass(Object object) {
			return object != null && object.getClass() == Position.class;
		}

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

	public enum Heading {
		North, East, South, West;

		public static Heading of(String label) {
			return of(label.charAt(0));
		}

		public static Heading of(char label) {
			if (label == 'N') return North;
			if (label == 'S') return South;
			if (label == 'W') return West;
			if (label == 'E') return East;
			return null;
		}

		public Heading turnRight() {
			return values()[add(+1)];
		}

		public Heading turnLeft() {
			return values()[add(-1)];
		}

		private int add(int offset) {
			return (this.ordinal() + offset + values().length) % values().length;
		}

	}


}

