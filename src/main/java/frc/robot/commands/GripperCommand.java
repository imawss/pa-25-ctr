package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.GripperPosition;
import frc.robot.subsystems.GripperSubsystem;

public class GripperCommand extends Command {
    private final GripperSubsystem gripper;
    private final GripperPosition pos;

    public GripperCommand(GripperPosition position) {
        gripper = new GripperSubsystem(Constants.GripperSystem.INTAKE_MOTOR_PORT,
                Constants.GripperSystem.ROTATION_MOTOR_PORT,
                Constants.GripperSystem.CANCODER_ID, Constants.GripperSystem.kIsIntakeInverted,
                Constants.GripperSystem.kIsRotationInverted);
        pos = position;
        addRequirements(gripper);
    }

    @Override
    public void initialize() {
        gripper.goToPreset(pos);
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return Math.abs(gripper.getRotationAngle() - pos.getAngle()) < 2.0;
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            gripper.stop(); 
        }
    }
}
