/*// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

public class TF2AutoBalance extends CommandBase {

  Swerve expensiveDrive;
  double roll;
  double error;
  double kDriveP;
  boolean haveIBeenTilted = false;
  boolean isCloserToCenter = false;
  double previousVelocity = 0;
  double driveSpeed = 0;
  int counter = 0;


  
  public TF2AutoBalance(Swerve expensiveDrive) {
    this.expensiveDrive = expensiveDrive;
    addRequirements(expensiveDrive);

  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    roll = expensiveDrive.getNavX().getPitch();

    if (Math.abs(roll) > 10) {
      haveIBeenTilted = true;
    }

    error = 0 - roll;

    if (haveIBeenTilted == false) {
      driveSpeed = 0.5;
      expensiveDrive.drive(0.5, 
      0.2, 
      0.0,  
      true, 
      true);

    } else {

      driveSpeed = Math.copySign(driveSpeed, error);

      double currentVelocity = expensiveDrive.getFrontRight().getCurrentVelocityMetersPerSecond();

      if (Math.signum(currentVelocity) != Math
          .signum(previousVelocity)) {
        System.out.println("counter = " + counter++);
        System.out.println(currentVelocity);
        System.out.println(previousVelocity);
        System.out.println("error =" + error);
        System.out.println("driveSpeed before = " + driveSpeed);
        driveSpeed *= 0.2;

        System.out.println("driveSpeed after = " + driveSpeed);
      }
      if (Math.abs(roll) < 8.75) {
        expensiveDrive.drive(0, 0, 0, true, true);
      } else {
        if (Math.abs(driveSpeed) < 0.33) {
          driveSpeed = Math.copySign(0.33, driveSpeed);
        }
        expensiveDrive.drive(driveSpeed, 0, 0, true, true);
      }
    }

    previousVelocity = expensiveDrive.getFrontRight().getCurrentVelocityMetersPerSecond();

  }

  @Override
  public void end(boolean interrupted) {
    System.out.println("DONE BALANCING");



  }



  @Override
  public boolean isFinished() {
    return false;
  }
}*/