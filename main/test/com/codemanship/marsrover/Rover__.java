package com.codemanship.marsrover;

import org.junit.Test;
import refactoring.Rover;
import refactoring.SimpleViewPoint;
import refactoring.SimpleViewPoint.Position;

import static org.assertj.core.api.Assertions.assertThat;
import static refactoring.SimpleViewPoint.Heading.*;
import static refactoring.Rover.Order.*;

public class Rover__ {

    @Test
    public void could_be_initialized_with_legacy_constructor() {
        Rover rover = new Rover(new SimpleViewPoint(North, 5, 5));
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getHeading()).isEqualTo(North);
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getPosition()).isEqualTo(new Position(5,5));
    }

    @Test
    public void could_be_initialized_using_new_constructors() {
        Rover rover = new Rover(new SimpleViewPoint(North, 4, 4));
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getPosition()).isEqualTo(new Position(4,4));
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getPosition()).isEqualTo(new Position(4,4));
    }

    @Test
    public void could_turn_left() {
        Rover rover = new Rover(new SimpleViewPoint(North, 3, 3));
        rover.go(Left);
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getHeading()).isEqualTo(West);
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getPosition()).isEqualTo(new Position(3,3));
    }

    @Test
    public void could_turn_right() {
        Rover rover = new Rover(new SimpleViewPoint(East, 5, 1));
        rover.go(Right);
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getHeading()).isEqualTo(South);
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getPosition()).isEqualTo(new Position(5,1));
    }

    @Test
    public void could_go_forward() {
        Rover rover = new Rover(new SimpleViewPoint(South, -1, 1));
        rover.go(Forward);
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getHeading()).isEqualTo(South);
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getPosition()).isEqualTo(new Position(-1,0));
    }

    @Test
    public void could_go_backward() {
        Rover rover = new Rover(new SimpleViewPoint(West, -4, 4));
        rover.go(Backward);
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getHeading()).isEqualTo(West);
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getPosition()).isEqualTo(new Position(-3,4));
    }

    @Test
    public void could_execute_many_orders() {
        Rover rover = new Rover(new SimpleViewPoint(West, 3, 1));
        rover.go(Backward, Left, Forward, Right, Forward);
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getHeading()).isEqualTo(West);
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getPosition()).isEqualTo(new Position(3,0));
    }

    @Test
    public void could_execute_legacy_instructions() {
        Rover rover = new Rover(new SimpleViewPoint(West, 3, 1));
        rover.go("BLFRF");
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getHeading()).isEqualTo(West);
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getPosition()).isEqualTo(new Position(3,0));
    }


    @Test
    public void could_ignore_legacy_instructions() {
        Rover rover = new Rover(new SimpleViewPoint(West, 3, 1));
        rover.go("BL*FRF");
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getHeading()).isEqualTo(West);
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getPosition()).isEqualTo(new Position(3,0));
    }

    @Test
    public void could_not_move_forward_if_runs_into_an_obstacle(){
        Rover rover = new Rover(new SimpleViewPoint(West, 3, 1));
        SimpleViewPoint.Obstacle obstacle = new SimpleViewPoint.Obstacle(new Position(4, 3));
        ((SimpleViewPoint) rover.getViewPoint()).addObstacle(obstacle);
        rover.go("RFFRFF");
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getHeading()).isEqualTo(West);
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getPosition()).isEqualTo(new Position(3,1));
        rover.go("RFRFF");
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getHeading()).isEqualTo(East);
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getPosition()).isEqualTo(new Position(5,2));
    }

    @Test
    public void could_not_move_backward_if_runs_into_an_obstacle(){
        Rover rover = new Rover(new SimpleViewPoint(West, 3, 1));
        SimpleViewPoint.Obstacle obstacle = new SimpleViewPoint.Obstacle(new Position(2, 3));
        ((SimpleViewPoint) rover.getViewPoint()).addObstacle(obstacle);
        rover.go("LBBLBB");
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getHeading()).isEqualTo(West);
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getPosition()).isEqualTo(new Position(3,1));
        rover.go("LBLBB");
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getHeading()).isEqualTo(East);
        assertThat(((SimpleViewPoint) rover.getViewPoint()).getPosition()).isEqualTo(new Position(1,2));
    }
}
