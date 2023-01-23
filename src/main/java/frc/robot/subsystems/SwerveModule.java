// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SwerveTuningValues;

public class SwerveModule extends SubsystemBase {
  private CANSparkMax m_driveMotor;
  private CANSparkMax m_turningMotor;

  private RelativeEncoder m_driveEncoder;
  private RelativeEncoder m_turningEncoder; 

  private PIDController m_turningPIDController;
  
  private AnalogInput m_absoluteEncoder; 
  private boolean m_absoluteEncoderReversed;
  private double m_absoluteEncoderOffsetRad; 

  /** Creates a new SwerveModule. */
  public SwerveModule(int driveMotorID, int turningMotorID, boolean driveMotorReversed, boolean turningMotorReversed
    , int absoluteEncoderID, double absoluteEncoderOffset, boolean absoluteEncoderReversed) {
    this.m_absoluteEncoderOffsetRad = absoluteEncoderOffset;
    this.m_absoluteEncoderReversed = absoluteEncoderReversed;

    // Using Thrifty Absolute Encoders, used Analog from WPILIB, not RevRobotics
    m_absoluteEncoder = new AnalogInput(absoluteEncoderID);

    m_driveMotor = new CANSparkMax(driveMotorID, MotorType.kBrushless);
    m_turningMotor = new CANSparkMax(turningMotorID, MotorType.kBrushless);

    m_driveMotor.setInverted(driveMotorReversed);
    m_turningMotor.setInverted(turningMotorReversed);

    // Built in encoders from SparkMax
    m_driveEncoder = m_driveMotor.getEncoder();
    m_turningEncoder = m_turningMotor.getEncoder();

    m_driveEncoder.setPositionConversionFactor(SwerveTuningValues.kDriveEncoderRot2Meter);
    m_driveEncoder.setVelocityConversionFactor(SwerveTuningValues.kDriveEncoderRPM2MeterPerSec);
    m_turningEncoder.setPositionConversionFactor(SwerveTuningValues.kTurningEncoderRot2Rad);
    m_turningEncoder.setVelocityConversionFactor(SwerveTuningValues.kDriveEncoderRPM2MeterPerSec);

    m_turningPIDController = new PIDController(SwerveTuningValues.kPTurning, 0, 0);
    m_turningPIDController.enableContinuousInput(-Math.PI, Math.PI);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public double getDrivePosition() {
    return m_driveEncoder.getPosition();
  }

  public double getTurningPosition() {
    return m_turningEncoder.getPosition();
  }

  public double getDriveVelocity() {
    return m_driveEncoder.getVelocity();
  }

  public double getTurningVelocity() {
    return m_turningEncoder.getVelocity();
  }

  public double getAbsoluteEncoderRAD() {
    double angle = m_absoluteEncoder.getVoltage() / RobotController.getVoltage5V();
    angle *= (Math.PI * 2.0);
    angle -= m_absoluteEncoderOffsetRad;
    return angle * (m_absoluteEncoderReversed ? -1.0 : 1.0);
  }

  public void resetEncoders() {
    m_driveEncoder.setPosition(0);
    m_turningEncoder.setPosition(getAbsoluteEncoderRAD());
  }

  public SwerveModuleState getState() {
    return new SwerveModuleState(getDriveVelocity(), new Rotation2d(getTurningPosition()));
  }

  public void setDesiredState(SwerveModuleState state) {
    if (Math.abs(state.speedMetersPerSecond) < 0.001) {
        stop();
        return;
    }
    state = SwerveModuleState.optimize(state, getState().angle);
    m_driveMotor.set(state.speedMetersPerSecond / SwerveTuningValues.kPhysMaxSpeedMetersPerSecond);
    m_turningMotor.set(m_turningPIDController.calculate(getTurningPosition(), state.angle.getRadians()));
    // SmartDashboard.putString("Swerve[" + m_absoluteEncoder.getChannel() + "] state", state.toString());
  }

  public void stop() {
    m_driveMotor.set(0);
    m_turningMotor.set(0);
  }
}
