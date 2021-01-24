package refactoring;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CameraViewPoint implements ViewPoint {
    private Camera camera;
    private ImageProcessor imageProcessor;

    @Override
    public ViewPoint forward() {
        return null;
    }

    @Override
    public ViewPoint backward() {
        return null;
    }

    @Override
    public ViewPoint turnLeft() {
        camera.turnLeft(90);
        return this;
    }

    @Override
    public ViewPoint turnRight() {
        camera.turnRight(90);
        return this;
    }
}
