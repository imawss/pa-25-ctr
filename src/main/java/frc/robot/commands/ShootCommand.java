package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.ElevatorPosition;
import frc.robot.GripperPosition;
import frc.robot.subsystems.GripperSubsystem;

public class ShootCommand extends SequentialCommandGroup {
    public ShootCommand(GripperSubsystem gripper, ElevatorPosition height, GripperPosition pos) {
        addCommands(
            new ElevatorCommand(height),
            new GripperShootCommand(gripper),
            new ElevatorCommand(ElevatorPosition.L1),
            new GripperCommand(GripperPosition.INTAKE)
        );
        
    }
    
}
