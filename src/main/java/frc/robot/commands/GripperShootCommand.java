package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.GripperSubsystem;

public class GripperShootCommand extends SequentialCommandGroup {
    public GripperShootCommand(GripperSubsystem gripper) {
        addCommands(
                new InstantCommand(() -> gripper.setIntakeSpeed(0.5)),
                new WaitCommand(0.5),
                new InstantCommand(() -> gripper.stop()));
    }
}
