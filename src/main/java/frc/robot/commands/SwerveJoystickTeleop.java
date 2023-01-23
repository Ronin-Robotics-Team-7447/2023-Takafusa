// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.JoystickConstants;
import frc.robot.Constants.SwerveTuningValues;
import frc.robot.subsystems.SwerveDrivebase;

public class SwerveJoystickTeleop extends CommandBase {
  private SwerveDrivebase swerveDrivebase;
  private Supplier<Double> xSpdFunction, ySpdFunction, turningSpdFunction;
  private Supplier<Boolean> fieldOrientedFunction;
  private SlewRateLimiter xLimiter, yLimiter, turningLimiter;

  /** Creates a new SwerveJoystick. */
  public SwerveJoystickTeleop(SwerveDrivebase swerveSubsystem,
      Supplier<Double> xSpdFunction, Supplier<Double> ySpdFunction, Supplier<Double> turningSpdFunction,
      Supplier<Boolean> fieldOrientedFunction) {
    this.swerveDrivebase = swerveSubsystem;
    this.xSpdFunction = xSpdFunction;
    this.ySpdFunction = ySpdFunction;
    this.turningSpdFunction = turningSpdFunction;
    this.fieldOrientedFunction = fieldOrientedFunction;
    this.xLimiter = new SlewRateLimiter(SwerveTuningValues.kTeleDriveMaxAccelerationUnitsPerSecond);
    this.yLimiter = new SlewRateLimiter(SwerveTuningValues.kTeleDriveMaxAccelerationUnitsPerSecond);
    this.turningLimiter = new SlewRateLimiter(SwerveTuningValues.kTeleDriveMaxAngularAccelerationUnitsPerSecond);
    addRequirements(swerveSubsystem);
}

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // 1. Get real-time joystick inputs
    double xSpeed = xSpdFunction.get();
    double ySpeed = ySpdFunction.get();
    double turningSpeed = turningSpdFunction.get();

    // 2. Apply deadband
    xSpeed = Math.abs(xSpeed) > JoystickConstants.Deadband ? xSpeed : 0.0;
    ySpeed = Math.abs(ySpeed) > JoystickConstants.Deadband ? ySpeed : 0.0;
    turningSpeed = Math.abs(turningSpeed) > JoystickConstants.Deadband ? turningSpeed : 0.0;

    // 3. Make the driving smoother
    xSpeed = xLimiter.calculate(xSpeed) * SwerveTuningValues.kTeleDriveMaxSpeedMetersPerSecond;
    ySpeed = yLimiter.calculate(ySpeed) * SwerveTuningValues.kTeleDriveMaxSpeedMetersPerSecond;
    turningSpeed = turningLimiter.calculate(turningSpeed)
            * SwerveTuningValues.kTeleDriveMaxAngularSpeedRadiansPerSecond;

    // 4. Construct desired chassis speeds
    ChassisSpeeds chassisSpeeds;
    if (fieldOrientedFunction.get()) {
        // Relative to field
        chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(
                xSpeed, ySpeed, turningSpeed, swerveDrivebase.getRotation2d());
    } else {
        // Relative to robot
        chassisSpeeds = new ChassisSpeeds(xSpeed, ySpeed, turningSpeed);
    }

    // 5. Convert chassis speeds to individual module states
    SwerveModuleState[] moduleStates = SwerveTuningValues.kDriveKinematics.toSwerveModuleStates(chassisSpeeds);

    // 6. Output each module states to wheels
    swerveDrivebase.setModuleStates(moduleStates);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    swerveDrivebase.stopModules();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
