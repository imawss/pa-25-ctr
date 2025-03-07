package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.GripperPosition;
import frc.robot.subsystems.GripperSubsystem;

public class GripperControlCommand extends Command {
    private final GripperSubsystem gripperSubsystem;
    private final XboxController joystick;

    public GripperControlCommand(GripperSubsystem gripperSubsystem, XboxController joystick) {
        this.gripperSubsystem = gripperSubsystem;
        this.joystick = joystick;
        addRequirements(gripperSubsystem);
    }

    @Override
    public void execute() {
        if (joystick.getLeftBumperButton()) {           
            gripperSubsystem.setIntakeSpeed(-0.1);
        } else if (joystick.getRightBumperButton()) {
            gripperSubsystem.setIntakeSpeed(0.1);
        } else {
            gripperSubsystem.stop();
        }

        switch(joystick.getPOV()){
            case 0:
                gripperSubsystem.goToPreset(GripperPosition.SHOOTING);
                break;
            case 180:
                gripperSubsystem.goToPreset(GripperPosition.INTAKE);
                break;
            default:
                break;
        }
            
        
    }

}
