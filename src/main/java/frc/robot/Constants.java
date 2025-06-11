package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;
import frc.robot.subsystems.SwerveModule;

public final class Constants {
    public static final class Joystick{
        public static final double kStickDeadband = 0.05;
        public static final double kSlewRateLimit = 0.05;
        public static final int kPort = 0;
    }
    public static final class OI{
        
    }
    
    //all swerve subsystem constants, should just be IDs
    public record SwerveConstants(int kDriveMotorID, int kAngleMotorID, int kCancoderID){}

    public static final class Swerve{
        //distance from center of wheel to center of wheel (long side of car)
        public static final double wheelBase = Units.inchesToMeters(19.25);
        //distance from middle of tire to middle of other tire (width of car)
        public static final double trackWidth = Units.inchesToMeters(19);
        //used to define kinematics (optional)
        public static final double halfWheelBase = wheelBase / 2;
        public static final double halfTrackWidth = trackWidth / 2;
        public static final SwerveDriveKinematics kSwerveKinematics = new SwerveDriveKinematics(
            new Translation2d(halfWheelBase, halfTrackWidth),
            new Translation2d(halfWheelBase, -halfTrackWidth),
            new Translation2d(-halfWheelBase, halfTrackWidth),
            new Translation2d(-halfWheelBase, -halfTrackWidth)
        );
        public static final class FLModule{
            public static final int kDriveID = 0;
            public static final int kTurnID = 0;
            public static final int kEncoderID = 0;
            public static final SwerveConstants kSwerveConstants = new SwerveConstants(kDriveID, kTurnID, kEncoderID);
        }
        public static final class FRModule{
            public static final int kDriveID = 0;
            public static final int kTurnID = 0;
            public static final int kEncoderID = 0;
            public static final SwerveConstants kSwerveConstants = new SwerveConstants(kDriveID, kTurnID, kEncoderID);
        }
        public static final class BLModule{
            public static final int kDriveID = 0;
            public static final int kTurnID = 0;
            public static final int kEncoderID = 0;
            public static final SwerveConstants kSwerveConstants = new SwerveConstants(kDriveID, kTurnID, kEncoderID);
        }
        public static final class BRModule{
            public static final int kDriveID = 0;
            public static final int kTurnID = 0;
            public static final int kEncoderID = 0;
            public static final SwerveConstants kSwerveConstants = new SwerveConstants(kDriveID, kTurnID, kEncoderID);
        }
    }
    
}