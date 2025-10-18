package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AlignToReef;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.DriveToPoint;
import frc.robot.subsystems.Swerve;

public class RobotContainer {
    public static NetworkTableInstance ntInstance = NetworkTableInstance.getDefault();
    public static Field2d m_simField = new Field2d();
    private Swerve s_swerve = new Swerve();
    private Joystick joystick = new Joystick(Constants.Joystick.kPort);
    JoystickButton btn = new JoystickButton(joystick, 2);
    JoystickButton driveForwardBtn = new JoystickButton(joystick, 3);
    JoystickButton alignToReefLeftBtn = new JoystickButton(joystick, 5);
    JoystickButton alignToReefRightBtn = new JoystickButton(joystick, 6);


    private SlewRateLimiter joystickRateLimiter = new SlewRateLimiter(Constants.Joystick.kSlewRateLimit);
    private SlewRateLimiter rotDirectionLimiter = new SlewRateLimiter(Constants.Joystick.kSlewRateLimit);
    private SendableChooser<Command> autoChooser = new SendableChooser<Command>();
    private SendableChooser<Double> robotSpeedChooser = new SendableChooser<Double>();

    public RobotContainer(){
        configureAutoOptions();
        configureBindings();
        s_swerve.setDefaultCommand(
            new DriveCommand(
                s_swerve, 
                () -> joystick.getRawAxis(Constants.Joystick.kXAxis), 
                () -> -joystick.getRawAxis(Constants.Joystick.kYAxis),
                () -> -joystick.getRawAxis(Constants.Joystick.kRotationAxis)*1.4, 
                true
            )
        );



        SmartDashboard.putData(RobotContainer.m_simField);
        SmartDashboard.putNumber("test", 0);
    }

    public void configureAutoOptions(){
        autoChooser.setDefaultOption("Default Auto", new InstantCommand());
        autoChooser.addOption("auto1", new InstantCommand());
        SmartDashboard.putData(autoChooser);

        robotSpeedChooser.setDefaultOption("100%", Double.valueOf(1.0));
        robotSpeedChooser.addOption("75%", 0.75);
        robotSpeedChooser.addOption("50%", 0.5);
        robotSpeedChooser.addOption("25%", 0.25);
        SmartDashboard.putData(robotSpeedChooser);
    }

    public void configureBindings(){
        this.driveForwardBtn.onTrue(new DriveToPoint(s_swerve, new Transform2d(
            new Translation2d(1, 0),
            Rotation2d.fromDegrees(0)
        )));

        this.alignToReefLeftBtn.whileTrue(
            new AlignToReef(s_swerve, () -> new Transform2d(0.1,0.1, Rotation2d.fromDegrees(0)), true)
        );
        this.alignToReefLeftBtn.whileTrue(
            new AlignToReef(s_swerve, s_swerve::tagAlignmentSupplier, false)
        );
    }

    public Command getAutoCommand(){
        return AutoBuilder.buildAuto("Rotate while moving (a)");
    }
}