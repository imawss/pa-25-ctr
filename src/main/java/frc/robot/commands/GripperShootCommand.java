package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.GripperSubsystem;

@SuppressWarnings("unused")
public class GripperShootCommand extends SequentialCommandGroup {
    public GripperShootCommand(GripperSubsystem gripper) {
        addCommands(
                new InstantCommand(() -> gripper.setIntakeSpeed(0.5)).withTimeout(0.2),
                new InstantCommand(() -> gripper.stop()).withTimeout(0.1));
    }
}
