package frc.robot.subsystems;

import static edu.wpi.first.units.Units.FeetPerSecond;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.servohub.ServoHub.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class GripperSubsystem extends SubsystemBase {

    TalonFX intakeMotor;
    TalonFXConfiguration intakeMotorConfig;
    RelativeEncoder intakeMotorEncoder;

    SparkMax rotationMotor;
    SparkMaxConfig rotationMotorConfig;
    CANcoder rotationMotorEncoder;

    public GripperSubsystem(int intakeMotorPort,
            int rotationMotorPort,
            int rotationEncoderID,
            boolean isIntakeInverted,
            boolean isRotationInverted) {

        intakeMotor = new TalonFX(intakeMotorPort);
        intakeMotorConfig = new TalonFXConfiguration();
        intakeMotorConfig.MotorOutput.Inverted = isIntakeInverted ? InvertedValue.CounterClockwise_Positive
                : InvertedValue.Clockwise_Positive;
        intakeMotor.getConfigurator().apply(intakeMotorConfig);

        rotationMotor = new SparkMax(rotationMotorPort, MotorType.kBrushless);
        rotationMotorConfig = new SparkMaxConfig();
        rotationMotorConfig.inverted(isRotationInverted);
        rotationMotor.configure(rotationMotorConfig, SparkMax.ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        
        rotationMotorEncoder = new CANcoder(rotationEncoderID);
    }

    public void setIntakeSpeed(double speed) {
        intakeMotor.set(speed);
    }

    public void setRotationSpeed(double speed) {
        rotationMotor.set(speed);
    }
    
    public void stop(){
        intakeMotor.set(0);
        rotationMotor.set(0);
    }
}
