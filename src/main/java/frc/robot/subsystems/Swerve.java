package frc.robot.subsystems;
import static frc.robot.Constants.Swerve.*;

import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.SwerveModule;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Swerve extends SubsystemBase{
    private AHRS m_gryo;
    private final SwerveModule[] m_swerveModules;
    private final SwerveDriveOdometry m_swerveDriveOdometry;
    public Swerve(){
        this.m_gryo = new AHRS(NavXComType.kI2C);
        //makes sure robot drives field relative
        zeroGyro();
        this.m_swerveModules = new SwerveModule[]{
            new SwerveModule(FLModule.kSwerveConstants),
            new SwerveModule(FRModule.kSwerveConstants),
            new SwerveModule(BLModule.kSwerveConstants),
            new SwerveModule(BRModule.kSwerveConstants)
        };
        //avoid but inverting error
        //TODO put this in its own thread
        Timer.delay(1.0);
        alignModules();
        this.m_swerveDriveOdometry = new SwerveDriveOdometry(
            Constants.Swerve.kSwerveKinematics,
            this.getYaw(),
            getModulePositions()
        );
    }
    @Override
    public void periodic(){
        this.m_swerveDriveOdometry.update(getYaw(), getModulePositions());
        RobotContainer.m_simField.setRobotPose(this.getPose());
        SmartDashboard.putData(RobotContainer.m_simField);
        for(int i = 0; i < 4; i++){
            SmartDashboard.putNumber("mod" + i, m_swerveModules[i].m_driveMotor.getMotorVoltage().getValueAsDouble());
        }
        if(this.getDefaultCommand() != null)
            SmartDashboard.putString("Command name ", this.getDefaultCommand().getName());
        
    }

    @Override
    public void simulationPeriodic(){
        for(int i = 0; i < this.m_swerveModules.length; i++){
            this.m_swerveModules[i].updateSim();
        }
    }

    public void drive(Translation2d translation, double rotationRad, boolean isFieldRelative){
        ChassisSpeeds chassisSpeeds;
        if(isFieldRelative){
            chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(translation.getX(), translation.getY(), rotationRad, getYaw());
        }
        else{
            chassisSpeeds = new ChassisSpeeds(translation.getX(), translation.getY(), rotationRad);
        }
        SwerveModuleState[] moduleStates = kSwerveKinematics.toSwerveModuleStates(ChassisSpeeds.discretize(chassisSpeeds, Constants.periodicSpeed));
        SwerveDriveKinematics.desaturateWheelSpeeds(moduleStates, Constants.Swerve.kMaxSpeedMetersPerSec);

        for(int i = 0; i < this.m_swerveModules.length; i++){
            this.m_swerveModules[i].setDesiredState(moduleStates[i]);
        }
    }

    public Pose2d getPose(){
        return this.m_swerveDriveOdometry.getPoseMeters();
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
        for(SwerveModule mod : this.m_swerveModules){
            mod.resetToAbsolute();
        }
    }
    public SwerveModulePosition[] getModulePositions(){
        SwerveModulePosition[] modPositions = new SwerveModulePosition[4];
        for(int i = 0; i < this.m_swerveModules.length; i++){
            modPositions[i] = this.m_swerveModules[i].getModulePosition();
        }
        return modPositions;
    }
}
