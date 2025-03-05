package frc.robot;

import static edu.wpi.first.units.Units.*;

import java.security.GeneralSecurityException;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.FollowPathCommand;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.commands.ShootCommand;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.GripperSubsystem;

public class RobotContainer {
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.0008).in(RadiansPerSecond); // 3/4 of a rotation per second
                                                                                        // max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final CommandPS4Controller driverJoystick = new CommandPS4Controller(Constants.OI.kDriverControllerPort);
    private final CommandPS4Controller operatorJoystick = new CommandPS4Controller(Constants.OI.kOperatorControllerPort);

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    public final GripperSubsystem gripperSubsystem = GripperSubsystem.getInstance();
    public final ElevatorSubsystem elevatorSubsystem = ElevatorSubsystem.getInstance();

    private final SendableChooser<Command> autoChooser;

    public RobotContainer() {
        autoChooser = AutoBuilder.buildAutoChooser("Tests");
        configureBindings();
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
                drivetrain.applyRequest(() -> {
                    return drive.withVelocityX(driverJoystick.getLeftY() * MaxSpeed)
                            .withVelocityY(driverJoystick.getLeftX() * MaxSpeed)
                            .withRotationalRate(-driverJoystick.getHID().getRawAxis(4) * MaxAngularRate);
                }));

        driverJoystick.square().whileTrue(drivetrain.applyRequest(() -> brake));
        driverJoystick.circle().whileTrue(drivetrain.applyRequest(
                () -> point
                        .withModuleDirection(new Rotation2d(-driverJoystick.getLeftY(), -driverJoystick.getLeftX()))));

        driverJoystick.share().and(driverJoystick.triangle()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        driverJoystick.share().and(driverJoystick.square()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        driverJoystick.options().and(driverJoystick.triangle())
                .whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        driverJoystick.options().and(driverJoystick.square())
                .whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        driverJoystick.L1().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        operatorJoystick.R2().whileTrue(new InstantCommand(() -> elevatorSubsystem.setSpeed(0.1)));
        operatorJoystick.R1().whileTrue(new InstantCommand(() -> elevatorSubsystem.setSpeed(-0.1)));
        operatorJoystick.square().onTrue(new ShootCommand(gripperSubsystem, ElevatorPosition.L1));

        drivetrain.registerTelemetry(logger::telemeterize);
    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }
}
