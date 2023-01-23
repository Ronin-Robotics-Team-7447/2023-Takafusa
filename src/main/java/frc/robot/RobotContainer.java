// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.JoystickConstants;
import frc.robot.Constants.Ports;
import frc.robot.commands.SwerveJoystickTeleop;
import frc.robot.commands.setFieldOriented;
import frc.robot.subsystems.SwerveDrivebase;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  // private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  // Subsystems
  private final SwerveDrivebase m_swerveDrivebase;

  // Commands
  private final setFieldOriented m_setFieldOriented;

  // Controller
  private Joystick m_joystick;
  public static JoystickButton xButton;


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Subsystem Assignment
    m_swerveDrivebase = new SwerveDrivebase();

    // Commands Assignment
    m_setFieldOriented = new setFieldOriented(m_swerveDrivebase);

    m_joystick = new Joystick(Ports.driverJoystickPort);
    xButton = new JoystickButton(m_joystick, JoystickConstants.xButton);
    
    m_swerveDrivebase.setDefaultCommand(new SwerveJoystickTeleop(m_swerveDrivebase,
      () -> -m_joystick.getRawAxis(JoystickConstants.driverJoystickYAxis),
      () -> m_joystick.getRawAxis(JoystickConstants.driverJoystickXAxis),
      () -> m_joystick.getRawAxis(JoystickConstants.driverJoystickRotAxis),
      () -> !m_joystick.getRawButton(JoystickConstants.driverJoystickFieldOrientedButtonIdx)));

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    xButton.onTrue(m_setFieldOriented);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  // public Command getAutonomousCommand() {
  //   // An ExampleCommand will run in autonomous
  //   return m_autoCommand;
  // }
}
