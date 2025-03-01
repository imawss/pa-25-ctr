package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorCommand extends Command {

    ElevatorSubsystem elevatorSubsystem;
    double targetHeight;

    public ElevatorCommand(double height) {
        elevatorSubsystem = new ElevatorSubsystem(Constants.ElevatorSystem.MASTER_ELEVATOR_MOTOR_PORT,
                Constants.ElevatorSystem.SLAVE_ELEVATOR_MOTOR_PORT,
                false, Constants.ElevatorSystem.kP,
                Constants.ElevatorSystem.kI,
                Constants.ElevatorSystem.kD, Constants.ElevatorSystem.TOP_LIMIT_SWITCH_ID,
                Constants.ElevatorSystem.BOTTOM_LIMIT_SWITCH_ID);
        addRequirements(elevatorSubsystem);
        targetHeight = height;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        elevatorSubsystem.resetElevator();
        elevatorSubsystem.setHeight(targetHeight);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

}
