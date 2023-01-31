// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.dacubeking.AutoBuilder.robot.robotinterface.AutonomousContainer;
import com.dacubeking.AutoBuilder.robot.robotinterface.CommandTranslator;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Swerve;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    final Swerve swerve = Swerve.getInstance();

    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    AutonomousContainer.getInstance().initialize(
      true, //isHolonomic - Is the robot using a holonomic drivetrain? (ex: swerve or mecanum)
      new CommandTranslator(
        swerve::setAutoPath, //The consumer to call to set the new trajectory
        swerve::stopMovement, //The runnable to call to stop the robot from moving
        swerve::setAutoRotation, //The consumer to call to set the autonomous rotation (can be null is the robot is not holonomic)
        swerve::isFinished, //The boolean supplier to call to check if the trajectory is done. This lambada should return false until the path has been fully (and is within error of the final position/rotation) driven.
        swerve::getAutoElapsedTime, //The double supplier to call to get the elapsed time of the trajectory. This lambada must return 0.0 immediately after a new trajectory is set and should return the elapsed time of the current trajectory that is being driven.
        swerve::resetPosition, //The consumer to call to set the initial pose of the robot at the start of autonomous
        false //Whether to run the commands on the main thread. If this is true, the commands will be run on the main thread. If this is false, the commands will be run on the autonomous thread. If you are unsure, it is safer to leave this as true. If you've designed your robot code to be thread safe, you can set this to false. It will allow the methods you call to be blocking which can simplify some code.
      ), 
      false, //crashOnError – Should the robot crash on error? If this is enabled, and an auto fails to load, the robot will crash. If this is disabled, the robot will skip the invalid auto and continue to the next one.
      this //timedRobot – The timed robot (should be able to just use the 'this' keyword) to use to create the period function for the autos. This can be null if you're running autos asynchronously.
      );
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  // @Override
  // public void autonomousInit() {
  //   m_autonomousCommand = m_robotContainer.getAutonomousCommand();

  //   // schedule the autonomous command (example)
  //   if (m_autonomousCommand != null) {
  //     m_autonomousCommand.schedule();
  //   }
  // }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
