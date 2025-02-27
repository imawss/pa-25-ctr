package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ElevatorSubsystem extends SubsystemBase {

    SparkMax masterMotor;
    SparkMaxConfig masterMotorConfig;

    SparkMax slaveMotor;
    SparkMaxConfig slaveMotorConfig;

    RelativeEncoder masterMotorEncoder;        
    RelativeEncoder slaveMotorEncoder;        

    public ElevatorSubsystem(int masterMotorID, int slaveMotorID, boolean isMotorsInverted){
        masterMotor = new SparkMax(masterMotorID, MotorType.kBrushless);
        masterMotorConfig = new SparkMaxConfig();
        masterMotorConfig.inverted(isMotorsInverted);
        masterMotor.configure(masterMotorConfig, SparkMax.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        slaveMotor = new SparkMax(slaveMotorID, MotorType.kBrushless);
        slaveMotorConfig = new SparkMaxConfig();
        slaveMotorConfig.inverted(isMotorsInverted);
        slaveMotor.configure(slaveMotorConfig, SparkMax.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        masterMotorEncoder = masterMotor.getEncoder();
        slaveMotorEncoder = slaveMotor.getEncoder();
    }

    public void resetElevator(){
    }

    public void setHeight(double meters){

    }
    
}
