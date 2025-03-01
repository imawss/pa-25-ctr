package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;

import java.lang.invoke.ConstantCallSite;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ElevatorSubsystem extends SubsystemBase {

    SparkMax masterMotor;
    SparkMaxConfig masterMotorConfig;

    SparkMax slaveMotor;
    SparkMaxConfig slaveMotorConfig;

    RelativeEncoder masterMotorEncoder;        
    RelativeEncoder slaveMotorEncoder;      

    DigitalInput topLimitSwitch;
    DigitalInput bottomLimitSwitch;
    
    PIDController pidController;
    double targetHeight = 0.0;

    public ElevatorSubsystem(int masterMotorID, int slaveMotorID, boolean isMotorsInverted, double kP, double kI, double kD, int topLimitSwitchID, int bottomLimitSwitchID) {
        masterMotor = new SparkMax(masterMotorID, MotorType.kBrushless);
        masterMotorConfig = new SparkMaxConfig();
        masterMotorConfig.inverted(isMotorsInverted);
        masterMotorConfig.idleMode(IdleMode.kBrake);
        masterMotor.configure(masterMotorConfig, SparkMax.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        slaveMotor = new SparkMax(slaveMotorID, MotorType.kBrushless);
        slaveMotorConfig = new SparkMaxConfig();
        slaveMotorConfig.inverted(isMotorsInverted);
        masterMotorConfig.idleMode(IdleMode.kBrake);
        slaveMotorConfig.follow(masterMotorID);
        slaveMotor.configure(slaveMotorConfig, SparkMax.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        masterMotorEncoder = masterMotor.getEncoder();
        slaveMotorEncoder = slaveMotor.getEncoder();
        
        topLimitSwitch = new DigitalInput(topLimitSwitchID);
        bottomLimitSwitch = new DigitalInput(bottomLimitSwitchID);

        pidController = new PIDController(kP, kI, kD);
    }

    public void resetElevator(){
        masterMotorEncoder.setPosition(0);
        slaveMotorEncoder.setPosition(0);
        System.out.println("Elevator has been reset");
    }

    public void setHeight(double meters){
        targetHeight = meters;
    }

    public double getHeight(){
        return (masterMotorEncoder.getPosition() / Constants.ElevatorSystem.kGearRatio) * Constants.ElevatorSystem.gearCircumference;
    }

    public void periodic(){
        double output = pidController.calculate(masterMotorEncoder.getPosition(), targetHeight);
        if (getSpeed() > 0) {
            if (topLimitSwitch.get()) {
                // We are going up and top limit is tripped so stop
                masterMotor.set(0);
            } else {
                // We are going up but top limit is not tripped so go at commanded speed
                masterMotor.set(output);
            }
        } else {
            if (bottomLimitSwitch.get()) {
                // We are going down and bottom limit is tripped so stop
                masterMotor.set(0);
            } else {
                // We are going down but bottom limit is not tripped so go at commanded speed
                masterMotor.set(output);
            }
        }
        masterMotor.set(output);
    }

    public double getSpeed () {
        return (masterMotorEncoder.getVelocity() * Constants.ElevatorSystem.gearCircumference) / Constants.ElevatorSystem.kGearRatio;
    }

    public void setSpeed(double speed) {
        masterMotor.set(speed);
    }

    public void stop() {
        masterMotor.set(0);
    }
    
}
