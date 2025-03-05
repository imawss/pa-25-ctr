package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.ElevatorPosition;
import frc.robot.GripperPosition;
import frc.robot.subsystems.GripperSubsystem;

@SuppressWarnings("unused")
public class ShootCommand extends SequentialCommandGroup {
    public ShootCommand(GripperSubsystem gripper, ElevatorPosition height) {
        addCommands(
            new ElevatorCommand(height),
            new GripperShootCommand(gripper).withTimeout(1.2),
            new ElevatorCommand(ElevatorPosition.L1)
        );
        
    }
    
}
