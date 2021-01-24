package com.codemanship.marsrover;

import org.junit.Test;
import refactoring.SimpleViewPoint.Position;

import static org.junit.Assert.assertEquals;
import static refactoring.SimpleViewPoint.Heading.*;

public class Position_ {

	@Test
	public void should_calculate_forward_position() {
		assertEquals(new Position(-1,0), new Position(0,0).forward(North).forward(West).forward(South));
		assertEquals(new Position(-1,-1), new Position(0,0).forward(West).forward(West).forward(East).forward(South));
		assertEquals(new Position(2,2), new Position(0,0).forward(South).forward(North).forward(North).forward(East).forward(East).forward(North));
	}

	@Test
	public void should_calculate_backward_position() {
		assertEquals(new Position(1,0), new Position(0,0).backward(North).backward(West).backward(South));
		assertEquals(new Position(1,1), new Position(0,0).backward(West).backward(West).backward(East).backward(South));
		assertEquals(new Position(-2,-2), new Position(0,0).backward(South).backward(North).backward(North).backward(East).backward(East).backward(North));
	}
}
