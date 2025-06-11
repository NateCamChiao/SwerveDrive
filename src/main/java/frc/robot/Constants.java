package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
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
        public static final double wheelBase = 0;
        public static final double trackWidth = 0;
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