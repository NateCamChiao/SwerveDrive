package frc.robot;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;
import frc.robot.subsystems.SwerveModule;
import frc.robot.utils.COTSFalconSwerveConstants;

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
        public static final double kMaxSpeedMetersPerSec = 6.14;
        public static final COTSFalconSwerveConstants kSwerveSpecialtyModule = COTSFalconSwerveConstants.SDSMK4i(COTSFalconSwerveConstants.driveGearRatios.SDSMK4i_L1);
        public static final double wheelCircumference = kSwerveSpecialtyModule.wheelCircumference;
        //distance from center of wheel to center of wheel (long side of car)
        public static final double kWheelBase = Units.inchesToMeters(19.25);
        //distance from middle of tire to middle of other tire (width of car)
        public static final double kTrackWidth = Units.inchesToMeters(19);
        //used to define kinematics (optional)
        public static final double halfWheelBase = kWheelBase / 2;
        public static final double halfTrackWidth = kTrackWidth / 2;
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

        public static final CTREConfigs kCTREConfigs = new CTREConfigs();
        public static final class CTRE {
            /* Module Gear Ratios */
            public static final double kDriveGearRatio = Swerve.kSwerveSpecialtyModule.driveGearRatio;
            public static final double kAngleGearRatio = Swerve.kSwerveSpecialtyModule.angleGearRatio;

            /* Motor Inverts */
            public static final InvertedValue kAngleMotorInvert = InvertedValue.CounterClockwise_Positive;
            public static final InvertedValue kDriveMotorInvert = InvertedValue.Clockwise_Positive;

            /* Angle Encoder Invert */
            public static final SensorDirectionValue kCanCoderInvert = SensorDirectionValue.CounterClockwise_Positive;

            /* Swerve Current Limiting */
            public static final int kAngleCurrentLimit = 25;
            public static final int kAngleCurrentThreshold = 40;
            public static final double kAngleCurrentThresholdTime = 0.1;
            public static final boolean kAngleEnableCurrentLimit = true;

            public static final int kDriveCurrentLimit = 35;
            public static final int kDriveCurrentThreshold = 60;
            public static final double kDriveCurrentThresholdTime = 0.1;
            public static final boolean kDriveEnableCurrentLimit = true;

            /* These values are used by the drive falcon to ramp in open loop and closed loop driving.
            * We found a small open loop ramp (0.25) helps with tread wear, tipping, etc */
            public static final double kOpenLoopRamp = .25;
            public static final double kClosedLoopRamp = 0;

            /* Angle Motor PID Values */
            public static final double angleKP = Swerve.kSwerveSpecialtyModule.angleKP;
            public static final double angleKI = Swerve.kSwerveSpecialtyModule.angleKI;
            public static final double angleKD = Swerve.kSwerveSpecialtyModule.angleKD;
            public static final double angleKF = Swerve.kSwerveSpecialtyModule.angleKF;

            /* Drive Motor PID Values */
            public static final double driveKP = 1;
            public static final double driveKI = 0.0;
            public static final double driveKD = 0.0;
            public static final double driveKF = 0.0;

            /* Drive Motor Characterization Values 
            * Divide SYSID values by 12 to convert from volts to percent output for CTRE */
            public static final double driveKS = (0.32 / 12);
            public static final double driveKV = (1.51 / 12);
            public static final double driveKA = (0.27 / 12);
             /* Neutral Modes */
            public static final NeutralModeValue kAngleNeutralMode = NeutralModeValue.Coast;
            public static final NeutralModeValue kDriveNeutralMode = NeutralModeValue.Brake;
        }
    }
    
}