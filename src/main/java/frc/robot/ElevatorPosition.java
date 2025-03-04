package frc.robot;

public enum ElevatorPosition {
    L1(Constants.ElevatorSystem.kL1Height),
    L2(Constants.ElevatorSystem.kL2Height), 
    L3(Constants.ElevatorSystem.kL3Height),
    L4(Constants.ElevatorSystem.kL4Height);

    private final double height;

    ElevatorPosition(double height) {
        this.height = height;
    }

    public double getHeight() {
        return height;
    }
}
