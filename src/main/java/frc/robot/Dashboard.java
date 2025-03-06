package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.GripperSubsystem;

public class Dashboard {
    GripperSubsystem gripper = GripperSubsystem.getInstance();
    ElevatorSubsystem elevator = ElevatorSubsystem.getInstance();

    public Dashboard() {
        ConfigureShuffleBoard();
    }

    public void ConfigureShuffleBoard() {
        @SuppressWarnings("unused")
        ShuffleboardTab tab = Shuffleboard.getTab("Robot");

        /**
         * tab.addCameraStream("Camera", Constants.CAMERA_NAME)
         * .withProperties(Map.of("Show Controls", false, "Show Crosshair", false))
         * .withWidget("CameraStream")
         * .withSize(10, 5)
         * .withPosition(0, 0);
         */
    }
}
