package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.GripperPosition;
import frc.robot.subsystems.GripperSubsystem;

public class GripperControlCommand extends Command {
    private final GripperSubsystem gripperSubsystem;
    private final XboxController joystick;

    public GripperControlCommand(GripperSubsystem gripperSubsystem, XboxController joystick) {
        this.gripperSubsystem = gripperSubsystem;
        this.joystick = joystick;
        addRequirements(gripperSubsystem);
    }

    @Override
    public void execute() {
        if (joystick.getLeftBumperButton()) {
            double currentAngle = gripperSubsystem.getRotationAngle();
            gripperSubsystem.setAngle(currentAngle - 1.0);
        } else if (joystick.getRightBumperButton()) {
            double currentAngle = gripperSubsystem.getRotationAngle();
            gripperSubsystem.setAngle(currentAngle + 1.0);
        } else {
            gripperSubsystem.stop();
        }

        if (joystick.getLeftTriggerAxis() > 0.1) {
            gripperSubsystem.setIntakeSpeed(-joystick.getLeftTriggerAxis()/ 2);
        } else if (joystick.getRightTriggerAxis() > 0.1) {
            gripperSubsystem.setIntakeSpeed(joystick.getRightTriggerAxis() / 2);
        } else {
            gripperSubsystem.stop();
        }

        gripperSubsystem.periodic();
        if (joystick.getPOV() == 0) {
            gripperSubsystem.goToPreset(GripperPosition.SHOOTING);
        } else if (joystick.getPOV() == 180) {
            gripperSubsystem.goToPreset(GripperPosition.INTAKE);
        }
        System.out.println("Gripper Angle: " + gripperSubsystem.getRotationAngle());

    }

}
