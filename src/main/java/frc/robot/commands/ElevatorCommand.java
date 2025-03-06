package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.ElevatorPosition;
import frc.robot.subsystems.ElevatorSubsystem;

@SuppressWarnings("unused")
public class ElevatorCommand extends Command {

    ElevatorSubsystem elevatorSubsystem = ElevatorSubsystem.getInstance();
    ElevatorPosition pos;

    public ElevatorCommand(ElevatorPosition height) {
        addRequirements(elevatorSubsystem);
        pos = height;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        elevatorSubsystem.resetElevator();
        System.out.println("ElevatorCommand initialized.");
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        elevatorSubsystem.goToPreset(pos);
        System.out.println(elevatorSubsystem.getHeight());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            elevatorSubsystem.setSpeed(0);
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Math.abs(elevatorSubsystem.getHeight() - pos.getHeight()) < 0.01;
    }

}
