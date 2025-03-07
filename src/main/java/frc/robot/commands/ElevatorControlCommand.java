package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.ElevatorPosition;

public class ElevatorControlCommand extends Command {
    private final ElevatorSubsystem elevator;
    private final XboxController joystick;
    private final int joystickAxis;
    private final double manualSpeed = 0.4; 
    private final double heightStep = 0.5; 

    public ElevatorControlCommand(ElevatorSubsystem elevator, XboxController joystick, int joystickAxis) {
        this.elevator = elevator;
        this.joystick = joystick;
        this.joystickAxis = joystickAxis;
        addRequirements(elevator);
    }

    @Override
    public void execute() {
        double joystickValue = joystick.getRawAxis(joystickAxis);
        double currentHeight = elevator.getHeight();
        
        if (Math.abs(joystickValue) > 0.1) { 
            double newTarget = currentHeight + joystickValue * heightStep;
            elevator.setHeight(newTarget);
        }

        if (joystick.getAButton()) {
            elevator.goToPreset(ElevatorPosition.L2);
        } 
    }

    @Override
    public boolean isFinished() {
        return false; 
    }

    @Override
    public void end(boolean interrupted) {
        elevator.setSpeed(0);
    }
}
