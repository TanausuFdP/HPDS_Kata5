package com.codemanship.marsrover;

import org.junit.Before;
import org.junit.Test;
import refactoring.Camera;
import refactoring.CameraViewPoint;
import refactoring.ImageProcessor;
import refactoring.ViewPoint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CameraViewPoint_ {
    private CameraViewPoint cameraViewPoint;

    @Before
    public void setUp(){
        Camera camera = mock(Camera.class);
        ImageProcessor imageProcessor = mock(ImageProcessor.class);
        cameraViewPoint = new CameraViewPoint(camera, imageProcessor);
    }

    @Test
    public void when_turning_left_should_return_a_new_ViewPoint(){
        ViewPoint newViewPoint = cameraViewPoint.turnLeft();
        assertThat(newViewPoint).isNotNull().isNotEqualTo(cameraViewPoint);
        verify(cameraViewPoint.getCamera()).turnLeft(90);
    }

    @Test
    public void should_be_able_to_turn_right(){
        Camera camera = mock(Camera.class);
        when(camera.turnRight(90)).thenReturn(camera);
        ImageProcessor imageProcessor = mock(ImageProcessor.class);
        CameraViewPoint cameraViewPoint = new CameraViewPoint(camera, imageProcessor);
        cameraViewPoint.turnRight();
        verify(camera).turnRight(90);
    }
}
