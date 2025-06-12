package frc.robot.subsystems;
import static frc.robot.Constants.Swerve.*;

import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;

import frc.robot.Constants;
import frc.robot.subsystems.SwerveModule;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.Timer;
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
        //avoid but inverting error
        //TODO put this in its own thread
        Timer.delay(1.0);
        alignModules();
        this.swerveDriveOdometry = new SwerveDriveOdometry(
            Constants.Swerve.kSwerveKinematics,
            this.getYaw(),
            getModulePositions()
        );
    }
    @Override
    public void periodic(){
        this.swerveDriveOdometry.update(getYaw(), getModulePositions());
    }

    public void drive(Translation2d translation, double rotationRad, boolean isFieldRelative){
        ChassisSpeeds chassisSpeeds;
        if(isFieldRelative){
            chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(translation.getX(), translation.getY(), rotationRad, getYaw());
        }
        else{
            chassisSpeeds = new ChassisSpeeds(translation.getX(), translation.getY(), rotationRad);
        }
        SwerveModuleState[] moduleStates = kSwerveKinematics.toSwerveModuleStates(chassisSpeeds);
        for(int i = 0; i < this.swerveModules.length; i++){
            this.swerveModules[i].setDesiredState(moduleStates[i]);
        }
    }
    //gyro

    public void zeroGyro(){
        this.m_gryo.zeroYaw();
    }

    public Rotation2d getYaw(){
        return Rotation2d.fromDegrees(360 - m_gryo.getAngle());
    }
    //sweve modules
    public void alignModules(){
        for(SwerveModule mod : this.swerveModules){
            mod.resetToAbsolute();
        }
    }
    public SwerveModulePosition[] getModulePositions(){
        SwerveModulePosition[] modPositions = new SwerveModulePosition[4];
        for(int i = 0; i < this.swerveModules.length; i++){
            modPositions[i] = this.swerveModules[i].getModulePosition();
        }
        return modPositions;
    }
}
