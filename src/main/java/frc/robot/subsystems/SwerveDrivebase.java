// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Ports;
import frc.robot.Constants.SwerveTuningValues;

public class SwerveDrivebase extends SubsystemBase {
  private AHRS gyro;
  SwerveModule m_frontLeft;
  SwerveModule m_frontRight;
  SwerveModule m_backLeft;
  SwerveModule m_backRight;


  /** Creates a new SwerveDrivebase. */
  public SwerveDrivebase() {
    m_frontLeft = new SwerveModule(
      Ports.flDrivePort,
      Ports.flTurningPort,
      Ports.flDriveEncoderReversed,
      Ports.flTurningEncoderReversed,
      Ports.flDriveAbsoluteEncoderPort,
      Ports.flDriveAbsoluteEncoderOffsetRad,
      Ports.flDriveEncoderReversed);

    m_frontRight = new SwerveModule(
      Ports.frDrivePort,
      Ports.frTurningPort,
      Ports.frDriveEncoderReversed,
      Ports.frTurningEncoderReversed,
      Ports.frDriveAbsoluteEncoderPort,
      Ports.frDriveAbsoluteEncoderOffsetRad,
      Ports.frDriveEncoderReversed);

    m_backLeft = new SwerveModule(
      Ports.blDrivePort,
      Ports.blTurningPort,
      Ports.blDriveEncoderReversed,
      Ports.blTurningEncoderReversed,
      Ports.blDriveAbsoluteEncoderPort,
      Ports.blDriveAbsoluteEncoderOffsetRad,
      Ports.blDriveEncoderReversed);

    m_backRight = new SwerveModule(
      Ports.brDrivePort,
      Ports.brTurningPort,
      Ports.brDriveEncoderReversed,
      Ports.brTurningEncoderReversed,
      Ports.brDriveAbsoluteEncoderPort,
      Ports.brDriveAbsoluteEncoderOffsetRad,
      Ports.brDriveEncoderReversed);

    gyro = new AHRS(SPI.Port.kMXP);

    new Thread(() -> {
      try {
          Thread.sleep(1000);
          zeroHeading();
      } catch (Exception e) {
      }
    }).start();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void zeroHeading() {
    gyro.reset();
}

  public double getHeading() {
    return Math.IEEEremainder(gyro.getAngle(), 360);
  }

  public Rotation2d getRotation2d() {
    return Rotation2d.fromDegrees(getHeading());
  }
  public void stopModules() {
    m_frontLeft.stop();
    m_frontRight.stop();
    m_backLeft.stop();
    m_backRight.stop();
  }

  public void setModuleStates(SwerveModuleState[] desiredStates) {
    SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, SwerveTuningValues.kPhysMaxSpeedMetersPerSecond);
    m_frontLeft.setDesiredState(desiredStates[0]);
    m_frontRight.setDesiredState(desiredStates[1]);
    m_backLeft.setDesiredState(desiredStates[2]);
    m_backRight.setDesiredState(desiredStates[3]);
  }
}
