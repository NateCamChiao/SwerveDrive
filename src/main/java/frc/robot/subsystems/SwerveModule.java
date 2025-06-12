package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.Constants.Swerve;
// import static frc.robot.Constants.Swerve;
import frc.robot.Constants.SwerveConstants;

public class SwerveModule {
    private TalonFX m_angleMotor, m_driveMotor;
    private CANcoder m_encoder;
    private DutyCycleOut m_driveDutyCycle;
    private PositionVoltage m_anglePositionVoltage;

    public SwerveModule(SwerveConstants moduleIDConstants){
        m_angleMotor = new TalonFX(moduleIDConstants.kDriveMotorID());
        m_driveMotor = new TalonFX(moduleIDConstants.kAngleMotorID());
        m_encoder = new CANcoder(moduleIDConstants.kCancoderID());

        m_angleMotor.getConfigurator().apply(Swerve.kCTREConfigs.swerveAngleFXConfig);
        m_driveMotor.getConfigurator().apply(Swerve.kCTREConfigs.swerveDriveFXConfig);
        m_encoder.getConfigurator().apply(Swerve.kCTREConfigs.swerveCanCoderConfig);
        //zero drive encoder
        m_driveMotor.getConfigurator().setPosition(0);
    }
    //sets integrated encoder to absolute position
    //eliminates need to manually align swerves
    public void resetToAbsolute(){
        //difference between absolute angle and relative angle
        double absoultePosition = m_encoder.getPosition().getValueAsDouble() - m_angleMotor.getPosition().getValueAsDouble();
        this.m_angleMotor.setPosition(absoultePosition);
    }


    public void setDesiredState(SwerveModuleState desiredState){
        desiredState.optimize(getAngle());
        setAngle(desiredState.angle);
        //takes difference between angle setpoint and current angle
        //then take cos of angle to limit speed when angle difference is large
        setDriveSpeed(desiredState.angle.minus(getAngle()).getCos());
    }

    public void setAngle(Rotation2d angle){
        m_angleMotor.setControl(m_anglePositionVoltage.withPosition(angle.getRotations()));
    }

    public void setDriveSpeed(double speedMetersPerSecond){
        m_driveDutyCycle.Output = speedMetersPerSecond / Swerve.kMaxSpeedMetersPerSec;
        m_driveMotor.setControl(m_driveDutyCycle);
    }
    //gets module positions for odometry
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
        return m_driveMotor.getPosition().getValueAsDouble() * Swerve.wheelCircumference;
    }
}
