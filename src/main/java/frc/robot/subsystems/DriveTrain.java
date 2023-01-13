// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
  Spark m_leftFront;
  Spark m_leftRear;
  Spark m_rightFront;
  Spark m_rightRear;
  
  /** Creates a new DriveTrain. */
  public DriveTrain() {
    Spark m_leftFront = new Spark(Constants.LeftFront);
    Spark m_leftRear = new Spark(Constants.LeftBack);
    Spark m_rightFront = new Spark(Constants.RightFront);
    Spark m_rightRear = new Spark(Constants.RightBack);

    MotorControllerGroup m_left = new MotorControllerGroup(m_leftFront, m_leftRear);
    MotorControllerGroup m_right = new MotorControllerGroup(m_rightFront, m_rightRear);
    m_left.setInverted(true);
    // m_right.setInverted(true);

    DifferentialDrive m_driveTrain = new DifferentialDrive(m_left, m_right);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
