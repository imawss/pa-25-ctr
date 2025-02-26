package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ElevatorSubsystem extends SubsystemBase {

    SparkMax masterElevatorMotor;
    SparkMaxConfig masterElevatorMotorConfig;

    SparkMax slaveElevatorMotor;
    SparkMaxConfig slaveElevatorMotorConfig;

    public ElevatorSubsystem(int masterMotorID, int slaveMotorID, boolean isMotorsInverted){
        masterElevatorMotor = new SparkMax(masterMotorID, MotorType.kBrushless);
        masterElevatorMotorConfig = new SparkMaxConfig();
        masterElevatorMotorConfig.inverted(isMotorsInverted);
        

        slaveElevatorMotor = new SparkMax(slaveMotorID, MotorType.kBrushless);
        slaveElevatorMotorConfig = new SparkMaxConfig();
        slaveElevatorMotorConfig.inverted(isMotorsInverted);
    }

    public void resetElevator(){
    }

    public void setHeight(double meters){

    }
    
}
