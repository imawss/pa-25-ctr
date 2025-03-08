package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.GripperPosition;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.GripperSubsystem;

public class AutoShootCommand extends SequentialCommandGroup {
    public AutoShootCommand(ElevatorSubsystem elevator, GripperSubsystem gripper) {
        addCommands(
                new InstantCommand(() -> gripper.goToPreset(GripperPosition.SHOOTING), gripper),
                new InstantCommand(() -> elevator.setHeight(Constants.ElevatorSystem.L2ButtonID), elevator),
                new WaitCommand(0.5),
                new InstantCommand(() -> gripper.setIntakeSpeed(1.0), gripper),
                new WaitCommand(0.3),
                new InstantCommand(() -> gripper.setIntakeSpeed(0), gripper),

                new InstantCommand(() -> elevator.setHeight(Constants.ElevatorSystem.kL1Height), elevator),
                new WaitCommand(0.5),
                new InstantCommand(() -> gripper.goToPreset(GripperPosition.INTAKE), gripper));
    }
}
