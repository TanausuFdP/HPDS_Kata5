package refactoring;

public class Camera {
    private CameraView frontView;
    private CameraView rearView;
    private int angle;

    public Camera(int angle) {
        this.angle = angle;
    }

    public Camera turnLeft(int angle){
        this.angle -= angle;
        return this;
    }

    public Camera turnRight(int angle){
        this.angle += angle;
        return this;
    }
}
