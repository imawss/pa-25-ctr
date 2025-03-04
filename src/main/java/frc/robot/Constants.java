package frc.robot;

public class Constants {
    public class ElevatorSystem {
        // MEAUERMENTS (cm)
        public static final double kGearRatio = 12.5;
        public static final double kL1Height = 0;
        public static final double kL2Height = 50;
        public static final double kL3Height = 50;
        public static final double kL4Height = 50;
        public static final double gearCircumference = Math.PI * 4;
        // BUTTONS
        public static final int resetElevatorButton = 0;
        public static final int L2ButtonID = 0;
        public static final int L3ButtonID = 0;
        public static final int L4ButtonID = 0;
        // IDs
        public static final int MASTER_ELEVATOR_MOTOR_PORT = 0;
        public static final int SLAVE_ELEVATOR_MOTOR_PORT = 0;
        public static final int TOP_LIMIT_SWITCH_ID = 0;
        public static final int BOTTOM_LIMIT_SWITCH_ID = 0;
        // PID
        public static final double kP = 0;
        public static final double kI = 0;
        public static final double kD = 0;
    }

    public class GripperSystem {
        // MEAUERMENTS (cm)
        public static final double kGearRatio = 50;
        public static final double kMaxDegree = 0;
        public static final double kIntakeRotation = 50;
        public static final double kShootingRotation = 50;
        public static final double kMinDegree = 50;
        public static final double gearCircumference = Math.PI * 4;
        public static final double kOffset = 0;
        // BUTTONS
        public static final int IntakeButtonID = 0;
        public static final int ShootButtonID = 0;
        public static final int ManuelIntakeID = 0; // trigger buttons
        public static final int ManuelShootID = 0; // trigger buttons
        // IDs
        public static final int INTAKE_MOTOR_PORT = 0;
        public static final int ROTATION_MOTOR_PORT = 0;
        public static final int CANCODER_ID = 0;
        public static final int LIMIT_SWITCH_ID = 0;
        //PID
        public static final double kP = 0;
        public static final double kI = 0;
        public static final double kD = 0;
        //boolean
        public static final boolean kIsIntakeInverted = false;
        public static final boolean kIsRotationInverted = false;
    }
}
