package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.commands.ElevatorCommand;
import frc.robot.commands.ElevatorControlCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.GripperSubsystem;

public class RobotContainer {
        private static final double MAX_SPEED = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts
                                                                                                    // desired top speed
        private static final double MAX_ANGULAR_RATE = RotationsPerSecond.of(0.0008).in(RadiansPerSecond); // 3/4 of a
                                                                                                           // rotation
                                                                                                           // per second
                                                                                                           // max
                                                                                                           // angular
                                                                                                           // velocity

        /* Setting up bindings for necessary control of the swerve drive platform */
        private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
                        .withDeadband(MAX_SPEED * 0.1)
                        .withRotationalDeadband(MAX_ANGULAR_RATE * 0.1) // Add a 10% deadband
                        .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive
                                                                                 // motors

        private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
        private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

        private final Telemetry logger = new Telemetry(MAX_SPEED);

        private final CommandPS4Controller driverJoystick = new CommandPS4Controller(
                        Constants.OI.kDriverControllerPort);
        private final CommandXboxController operatorJoystick = new CommandXboxController(
                        Constants.OI.kOperatorControllerPort);

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
                                        return drive.withVelocityX(driverJoystick.getLeftY() * MAX_SPEED)
                                                        .withVelocityY(driverJoystick.getLeftX() * MAX_SPEED)
                                                        .withRotationalRate(
                                                                        driverJoystick.getRightX() * MAX_ANGULAR_RATE);
                                }));

                driverJoystick.square().whileTrue(drivetrain.applyRequest(() -> brake));
                driverJoystick.circle().whileTrue(drivetrain.applyRequest(
                                () -> point
                                                .withModuleDirection(new Rotation2d(-driverJoystick.getLeftY(),
                                                                -driverJoystick.getLeftX()))));

                driverJoystick.share().and(driverJoystick.triangle())
                                .whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
                driverJoystick.share().and(driverJoystick.square())
                                .whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
                driverJoystick.options().and(driverJoystick.triangle())
                                .whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
                driverJoystick.options().and(driverJoystick.square())
                                .whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

                driverJoystick.L1().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

                // operatorJoystick.cross().onTrue(new ElevatorCommand(ElevatorPosition.L2));

                // operatorJoystick.L1().whileTrue(new RunCommand(() ->
                // elevatorSubsystem.setHeight(Math.min(elevatorSubsystem.getHeight() + 0.05,
                // Constants.ElevatorSystem.kL4Height)), elevatorSubsystem));

                drivetrain.registerTelemetry(logger::telemeterize);
                elevatorSubsystem.setDefaultCommand(
                                new ElevatorControlCommand(
                                                elevatorSubsystem,
                                                operatorJoystick.getHID(),
                                                1));
        }

        public Command getAutonomousCommand() {
                return autoChooser.getSelected();
        }
}
