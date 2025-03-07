package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.ElevatorPosition;

public class ElevatorControlCommand extends Command {
    private final ElevatorSubsystem elevator;
    private final XboxController joystick;

    private static final double TRIGGER_THRESHOLD = 0.1;
    private static final double MAX_SPEED = 0.4;
    private double lastTargetHeight;

    public ElevatorControlCommand(ElevatorSubsystem elevator, XboxController joystick) {
        this.elevator = elevator;
        this.joystick = joystick;
        addRequirements(elevator);
        lastTargetHeight = elevator.getHeight();
    }

    @Override
    public void execute() { 
        if (joystick.getAButtonPressed()) {
            elevator.goToPreset(ElevatorPosition.L1);
        } else if (joystick.getXButtonPressed()) {
            elevator.goToPreset(ElevatorPosition.L2);
        } else if (joystick.getYButtonPressed()) {
            elevator.goToPreset(ElevatorPosition.L3);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        elevator.setHeight(lastTargetHeight); 
    }
}
