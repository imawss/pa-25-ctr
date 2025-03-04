package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.GripperPosition;
import frc.robot.subsystems.GripperSubsystem;

public class PreShootCommand extends SequentialCommandGroup {
    public PreShootCommand() {
        addCommands(
            new GripperCommand(GripperPosition.INTAKE),
            new WaitCommand(0.5),
            new GripperCommand(GripperPosition.SHOOTING),
            new WaitCommand(0.2)
        );
    }
}
