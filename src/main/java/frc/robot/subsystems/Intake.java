// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
    /** Creates a new Intake. */
  CANSparkMax m_intake;

  public Intake (){
    m_intake = new CANSparkMax(Constants.takeInOut, MotorType.kBrushless);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void IntakeSpeed(double speedOut) {
    m_intake.set(Constants.speed_in);
  }

  public void OuttakeSpeed() {
    m_intake.set(Constants.speed_out);
  }

  public void HoldSpeed() {
    m_intake.set(Constants.hold_speed);
  }

  public void HighIntakeSpeed() {
    m_intake.set(Constants.highSpeed_in);
  }

  public void LowIntakeSpeed() {
    m_intake.set(Constants.highSpeed_out);
  }



}
