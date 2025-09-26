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
import edu.wpi.first.networktables.StructArrayPublisher;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.RobotContainer.ntInstance;

public class Swerve extends SubsystemBase{
    private AHRS m_gryo;
    private final SwerveModule[] m_swerveModules;
    private final SwerveDriveOdometry m_swerveDriveOdometry;

    //logging SwerveModuleStates (advantagescope)
    private final StructArrayPublisher<SwerveModuleState> measuredModuleStatePub = ntInstance.getStructArrayTopic("Measured Module States", SwerveModuleState.struct).publish();
    private final StructArrayPublisher<SwerveModuleState> desiredModuleStatePub = ntInstance.getStructArrayTopic("Desired Module States", SwerveModuleState.struct).publish();
    private final StructPublisher<ChassisSpeeds> measuredChassisPub = ntInstance.getStructTopic("Measured Chassis Speeds", ChassisSpeeds.struct).publish();
    private final StructPublisher<ChassisSpeeds> desiredChassisPub = ntInstance.getStructTopic("Desired Chassis Speeds", ChassisSpeeds.struct).publish();
    public Swerve(){
        this.m_gryo = new AHRS(NavXComType.kMXP_SPI);
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

        SwerveModuleState[] swerveModuleStates = new SwerveModuleState[4];
        for(int i = 0; i < 4; i++){
            SmartDashboard.putNumber("mod" + i, m_swerveModules[i].m_driveMotor.getMotorVoltage().getValueAsDouble());
            swerveModuleStates[i] = m_swerveModules[i].getSwerveModuleState();

            SmartDashboard.putNumber("Mod " + i, m_swerveModules[i].m_angleMotor.getPosition().getValueAsDouble());
            SmartDashboard.putNumber("Mod encoder" + i, m_swerveModules[i].m_encoder.getPosition().getValueAsDouble());
        }
        if(this.getDefaultCommand() != null)
            SmartDashboard.putString("Command name ", this.getDefaultCommand().getName());
        
        //updating publishers
        this.measuredModuleStatePub.set(swerveModuleStates);
        this.measuredChassisPub.set(
            Constants.Swerve.kSwerveKinematics.toChassisSpeeds(swerveModuleStates)
        );        
        SmartDashboard.putNumber("gyro", getYaw().getDegrees());
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

        //updating publishers
        this.desiredChassisPub.set(chassisSpeeds);
        this.desiredModuleStatePub.set(moduleStates);
    }

    public Pose2d getPose(){
        return this.m_swerveDriveOdometry.getPoseMeters();
    }
    //gyro

    public void zeroGyro(){
        this.m_gryo.zeroYaw();
    }

    public Rotation2d getYaw(){
        return Rotation2d.fromDegrees(-m_gryo.getYaw());
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
