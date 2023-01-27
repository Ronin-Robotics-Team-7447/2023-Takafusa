// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */
  Spark m_intake;
  public Intake (){
    m_intake = new Spark(Constants.takeInOut);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void IntakeSpeed(double speed) {
    m_intake.set(speed);
  }
}
