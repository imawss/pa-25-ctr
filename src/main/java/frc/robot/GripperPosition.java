package frc.robot;

public enum GripperPosition {
    INTAKE(Constants.GripperSystem.kIntakeRotation),
    SHOOTING(Constants.GripperSystem.kShootingRotation);

    private final double angle;

    GripperPosition(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }
}
