package frc.robot.subsystems;
import static frc.robot.Constants.Swerve.*;

import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;

import frc.robot.Constants;
import frc.robot.subsystems.SwerveModule;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Swerve extends SubsystemBase{
    private AHRS m_gryo;
    private final SwerveModule[] swerveModules;
    private final SwerveDriveOdometry swerveDriveOdometry;
    public Swerve(){
        this.m_gryo = new AHRS(NavXComType.kI2C);
        //makes sure robot drives field relative
        zeroGyro();
        this.swerveModules = new SwerveModule[]{
            new SwerveModule(FLModule.kSwerveConstants),
            new SwerveModule(FRModule.kSwerveConstants),
            new SwerveModule(BLModule.kSwerveConstants),
            new SwerveModule(BRModule.kSwerveConstants)
        };
        this.swerveDriveOdometry = new SwerveDriveOdometry(
            Constants.Swerve.kSwerveKinematics,
            this.getYaw(),
            getModulePositions()
        );

    }
    @Override
    public void periodic(){

    }

    public SwerveModulePosition[] getModulePositions(){
        SwerveModulePosition[] modPositions = new SwerveModulePosition[4];
        for(int i = 0; i < this.swerveModules.length; i++){
            modPositions[i] = this.swerveModules[i].getModulePosition();
        }
        return modPositions;
    }

    //gyro

    public void zeroGyro(){
        this.m_gryo.zeroYaw();
    }

    public Rotation2d getYaw(){
        return Rotation2d.fromDegrees(360 - m_gryo.getAngle());
    }

}
