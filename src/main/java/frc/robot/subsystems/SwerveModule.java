package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import frc.robot.Constants;

public class SwerveModule {
    private TalonFX m_angleMotor, m_driveMotor;
    private CANcoder m_angleEncoder;
    public SwerveModule(Constants.SwerveConstants moduleIDConstants){
        m_angleMotor = new TalonFX(moduleIDConstants.kDriveMotorID());
        m_driveMotor = new TalonFX(moduleIDConstants.kAngleMotorID());
        m_angleEncoder = new CANcoder(moduleIDConstants.kCancoderID());
    }

    public void resetToAbsolute(){
        
    }

    public SwerveModulePosition getModulePosition(){
        return new SwerveModulePosition(
            getDriveDistMeters(),
            getAngle()
        );
    }

    public Rotation2d getAngle(){
        return Rotation2d.fromRotations(m_angleMotor.getPosition().getValueAsDouble());
    }

    public double getDriveDistMeters(){
        return m_driveMotor.getPosition().getValueAsDouble() / Constants.Swerve.wheelCircumference;
    }
}
