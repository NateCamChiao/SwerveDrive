package frc.robot;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.Swerve;

public class RobotContainer {
    public static NetworkTableInstance ntInstance = NetworkTableInstance.getDefault();
    public static Field2d m_simField = new Field2d();
    private Swerve s_swerve = new Swerve();
    private SendableChooser<Command> autoChooser = new SendableChooser<Command>();

    public RobotContainer(){
        configureAutoOptions();
        configureBindings();

        SmartDashboard.putData(RobotContainer.m_simField);
        SmartDashboard.putNumber("test", 0);
    }

    public void configureAutoOptions(){

    }

    public void configureBindings(){
        Joystick joystick = new Joystick(Constants.Joystick.kPort);
        s_swerve.setDefaultCommand(
            new DriveCommand(
                s_swerve, 
                () -> -joystick.getRawAxis(Constants.Joystick.kXAxis), 
                () -> joystick.getRawAxis(Constants.Joystick.kYAxis),
                () -> joystick.getRawAxis(Constants.Joystick.kRotationAxis)*1.4, 
                true
            )
        );
    }

    public Command getAutoCommand(){
        return autoChooser.getSelected();
    }
}