package refactoring;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
public class SimpleViewPoint implements ViewPoint {
    private Position originalPosition;
    private Heading originalHeading;
    private Position position;
    private Heading heading;
    private static final Map<Position, Obstacle> obstacles = new HashMap<>();

    public SimpleViewPoint(String facing, int x, int y){
        this.heading = Heading.of(facing);
        this.originalHeading = Heading.of(facing);
        this.position = new Position(x, y);
        this.originalPosition = new Position(x, y);
    }

    public SimpleViewPoint(Heading heading, int x, int y){
        this(heading, new Position(x, y));
    }

    public SimpleViewPoint(Heading heading, Position position) {
        this.heading = heading;
        this.originalHeading = heading;
        this.position = position;
        this.originalPosition = position;
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.put(obstacle.getPosition(), obstacle);
    }

    @Override
    public ViewPoint forward() {
        Position newPosition = position.forward(heading);
        if(newPosition == null){
            this.heading = this.originalHeading;
            this.position = this.originalPosition;
            return null;
        }
        position = newPosition;
        return this;
    }

    @Override
    public ViewPoint backward() {
        Position newPosition = position.backward(heading);
        if(newPosition == null){
            this.heading = this.originalHeading;
            this.position = this.originalPosition;
            return null;
        }
        position = newPosition;
        return this;
    }

    @Override
    public ViewPoint turnLeft() {
        heading = heading.turnLeft();
        return this;
    }

    @Override
    public ViewPoint turnRight() {
        heading = heading.turnRight();
        return this;
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
            Position newPosition;
            switch(heading){
                case North:
                    newPosition = new Position(this.x,this.y+i);
                    if(thereIsAnObstacle(newPosition)) return null;
                    return newPosition;
                case East:
                    newPosition = new Position(this.x+i,this.y);
                    if(thereIsAnObstacle(newPosition)) return null;
                    return newPosition;
                case South:
                    newPosition = new Position(this.x,this.y-i);
                    if(thereIsAnObstacle(newPosition)) return null;
                    return newPosition;
                case West:
                    newPosition = new Position(this.x-i,this.y);
                    if(thereIsAnObstacle(newPosition)) return null;
                    return newPosition;
                default:
                    return null;
            }
        }

        private boolean thereIsAnObstacle(Position position){ return obstacles.containsKey(position);}

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

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

    }

    public static class Obstacle {

        private final Position position;

        public Obstacle(Position position) {
            this.position = position;
        }

        public Position getPosition() {
            return position;
        }
    }
}
