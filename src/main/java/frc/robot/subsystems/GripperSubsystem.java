package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Rotation;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.GripperPosition;

@SuppressWarnings("unused")
public class GripperSubsystem extends SubsystemBase {

    private TalonFX intakeMotor;
    private TalonFXConfiguration intakeMotorConfig;

    private SparkMax rotationMotor;
    private SparkMaxConfig rotationMotorConfig;
    private RelativeEncoder rotationMotorEncoder;

    private PIDController pidController;

    private final double kP = Constants.GripperSystem.kP;
    private final double kI = Constants.GripperSystem.kI;
    private final double kD = Constants.GripperSystem.kD;
    private final double minAngle = Constants.GripperSystem.kMinDegree;
    private final double maxAngle = Constants.GripperSystem.kMaxDegree;
    private final double offset = Constants.GripperSystem.kOffset;

    private static GripperSubsystem instance;

    private GripperSubsystem(int intakeMotorPort, int rotationMotorPort, boolean isIntakeInverted,
            boolean isRotationInverted) {
        intakeMotor = new TalonFX(intakeMotorPort);
        intakeMotorConfig = new TalonFXConfiguration();
        intakeMotorConfig.MotorOutput.Inverted = isIntakeInverted ? InvertedValue.CounterClockwise_Positive
                : InvertedValue.Clockwise_Positive;
        intakeMotor.getConfigurator().apply(intakeMotorConfig);

        rotationMotor = new SparkMax(rotationMotorPort, MotorType.kBrushless);
        rotationMotorConfig = new SparkMaxConfig();
        rotationMotorConfig.inverted(isRotationInverted);
        rotationMotorConfig.idleMode(IdleMode.kBrake);
        rotationMotor.configure(rotationMotorConfig, SparkMax.ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        rotationMotorEncoder = rotationMotor.getEncoder();

        pidController = new PIDController(kP, kI, kD);
        pidController.setTolerance(2.0);
    }

    public static GripperSubsystem getInstance() {
        if (instance == null) {
            instance = new GripperSubsystem(Constants.GripperSystem.INTAKE_MOTOR_PORT,
                    Constants.GripperSystem.ROTATION_MOTOR_PORT,
                    Constants.GripperSystem.kIsIntakeInverted, Constants.GripperSystem.kIsRotationInverted);
        }
        return instance;
    }

    public double getRotationAngle() {
        double rawAngle = rotationMotorEncoder.getPosition() * 360;
        return (rawAngle / Constants.GripperSystem.kGearRatio) + offset;
    }

    public void moveToAngle(double targetAngle) {
        targetAngle = Math.max(minAngle, Math.min(targetAngle, maxAngle));

        double currentAngle = getRotationAngle();
        double targetEncoderAngle = (targetAngle - offset) * Constants.GripperSystem.kGearRatio;
        double output = pidController.calculate((currentAngle - offset) * Constants.GripperSystem.kGearRatio, targetEncoderAngle);

        rotationMotor.set(output);
    }

    public void setRotationSpeed(double speed) {
        double currentAngle = getRotationAngle();
        if ((speed > 0 && currentAngle >= maxAngle) || (speed < 0 && currentAngle <= minAngle)) {
            rotationMotor.set(0);
        } else {
            rotationMotor.set(speed);
        }
    }

    public void setIntakeSpeed(double speed){
        intakeMotor.set(speed);
    }

    public void goToPreset(GripperPosition position) {
        moveToAngle(position.getAngle());
    }

    public void stop() {
        intakeMotor.set(0);
        rotationMotor.set(0);
    }
}
