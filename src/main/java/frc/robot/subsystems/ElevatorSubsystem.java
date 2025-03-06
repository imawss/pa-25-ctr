package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.ElevatorPosition;
import frc.robot.commands.GripperCommand;

@SuppressWarnings("unused")
public class ElevatorSubsystem extends SubsystemBase {
    SparkMax masterMotor;
    SparkMaxConfig masterMotorConfig;
    SparkMax slaveMotor;
    SparkMaxConfig slaveMotorConfig;
    RelativeEncoder masterMotorEncoder;
    RelativeEncoder slaveMotorEncoder;
    //DigitalInput topLimitSwitch;
    //DigitalInput bottomLimitSwitch;
    PIDController pidController;
    double targetHeight = 0.0;
    double maxSpeed = 0.4; 
    boolean isElevatorAtZero = true;

    private static ElevatorSubsystem instance;

    private ElevatorSubsystem(int masterMotorID, int slaveMotorID, boolean isMotorsInverted, double kP, double kI,
            double kD, int topLimitSwitchID, int bottomLimitSwitchID) {
        masterMotor = new SparkMax(masterMotorID, MotorType.kBrushless);
        masterMotorConfig = new SparkMaxConfig();
        masterMotorConfig.inverted(isMotorsInverted);
        masterMotorConfig.idleMode(IdleMode.kBrake);
        masterMotor.configure(masterMotorConfig, SparkMax.ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        slaveMotor = new SparkMax(slaveMotorID, MotorType.kBrushless);
        slaveMotorConfig = new SparkMaxConfig();
        slaveMotorConfig.inverted(isMotorsInverted);
        slaveMotorConfig.idleMode(IdleMode.kBrake);
        slaveMotorConfig.follow(masterMotor);
        slaveMotor.configure(slaveMotorConfig, SparkMax.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        masterMotorEncoder = masterMotor.getEncoder();
        slaveMotorEncoder = slaveMotor.getEncoder();

        //topLimitSwitch = new DigitalInput(topLimitSwitchID);
        //bottomLimitSwitch = new DigitalInput(bottomLimitSwitchID);

        pidController = new PIDController(kP, kI, kD);
        resetElevator();
    }

    public static ElevatorSubsystem getInstance() {
        if (instance == null) {
            instance = new ElevatorSubsystem(Constants.ElevatorSystem.MASTER_ELEVATOR_MOTOR_PORT,
                    Constants.ElevatorSystem.SLAVE_ELEVATOR_MOTOR_PORT, true, Constants.ElevatorSystem.kP,
                    Constants.ElevatorSystem.kI, Constants.ElevatorSystem.kD,
                    Constants.ElevatorSystem.TOP_LIMIT_SWITCH_ID,
                    Constants.ElevatorSystem.BOTTOM_LIMIT_SWITCH_ID);
        }
        return instance;
    }

    public void resetElevator() {
        masterMotorEncoder.setPosition(0);
        slaveMotorEncoder.setPosition(0);
        isElevatorAtZero = true;
        System.out.println("Elevator has been reset");
    }

    public double getHeight() {
        return (masterMotorEncoder.getPosition() * Constants.ElevatorSystem.gearCircumference * 2
                / Constants.ElevatorSystem.kGearRatio / 10);
    }

    public void setHeight(double centimeters) {
        targetHeight = centimeters;
        isElevatorAtZero = false;
    }

    public double getSpeed() {
        return (masterMotorEncoder.getPosition() * Constants.ElevatorSystem.gearCircumference * 2
                / Constants.ElevatorSystem.kGearRatio / 100);
    }

    public void setSpeed(double speed) {
        masterMotor.set(speed);
        slaveMotor.set(speed);
    }

    public void periodic() {
        double output = Math.max(-maxSpeed, Math.min(maxSpeed, pidController.calculate(getHeight(), targetHeight)));

        /* 
        if (getSpeed() > 0 && topLimitSwitch.get()) {
            masterMotor.set(0);
            targetHeight = getHeight();
        }

        if (getSpeed() < 0 && bottomLimitSwitch.get()) {
            masterMotor.set(0);
            resetElevator();
        }
        */

        masterMotor.set(output);
    }

    public void goToPreset(ElevatorPosition position) {
        setHeight(position.getHeight());
    }

    public boolean isElevatorAtZero() {
        return isElevatorAtZero;
    }
}
