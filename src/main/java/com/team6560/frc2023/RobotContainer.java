// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6560.frc2023;
// import java.io.File;

import com.team6560.frc2023.commands.DriveCommand;
import com.team6560.frc2023.commands.MoveArmToAngle;
import com.team6560.frc2023.commands.MoveClawManually;
import com.team6560.frc2023.commands.MoveTelescopeToPosition;
import com.team6560.frc2023.commands.MoveWristManually;
import com.team6560.frc2023.commands.MoveWristToAngle;
import com.team6560.frc2023.commands.SetLights;
import com.team6560.frc2023.commands.auto.AutoBuilder;
import com.team6560.frc2023.controls.ManualControls;
import com.team6560.frc2023.subsystems.Arm;
import com.team6560.frc2023.subsystems.Claw;
import com.team6560.frc2023.subsystems.Drivetrain;
import com.team6560.frc2023.subsystems.Lights;
import com.team6560.frc2023.subsystems.Limelight;
import com.team6560.frc2023.subsystems.Telescope;
import com.team6560.frc2023.subsystems.Wrist;
import com.team6560.frc2023.subsystems.Wrist1;
import com.team6560.frc2023.subsystems.Wrist2;

import edu.wpi.first.cscore.CameraServerJNI.TelemetryKind;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
// import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class RobotContainer {
        Joystick armJoystick;

        Trigger POV0button;
        Trigger POV180button;

        Trigger button1;
        Trigger button2;
        Trigger button3;
        Trigger button4;
        Trigger button5;
        Trigger button6;
        Trigger button7;
        Trigger button8;
        Trigger button9;
        Trigger button10;
        Trigger button11;
        Trigger button12;

        // Wrist m_wrist;
        Claw m_claw;
        MoveClawManually intake;
        MoveClawManually outtake;
        MoveWristManually moveWristUp;
        MoveWristManually moveWristDown;
        MoveWristToAngle testPosition;

        MoveArmToAngle testPositionArm;
        // MoveTelescopeToPosition fullyExtendedPosition;

        Lights m_lights;
        SetLights cubeLights;
        SetLights coneLights;
        SetLights defaultLights;
        // The robot's subsystems and commands are defined here...

        // not public or private so Robot.java has access to it.
        Drivetrain drivetrain = null;

        private final Limelight limelight;

        private final DriveCommand driveCommand;

        // private final Intake intake;

        private final Arm arm;

        public final Wrist2 wrist2;

        // private final Telescope telescope;

        private final ManualControls manualControls;

        // A chooser for autonomous commands
        private final SendableChooser<Command> autoChooser;

        private final AutoBuilder autoBuilder;

        /**
         * The container for the robot. Contains subsystems, OI devices, and commands.
         */
        public RobotContainer() {
                m_lights = new Lights();
                coneLights = new SetLights(m_lights, "cone");
                cubeLights = new SetLights(m_lights, "cube");
                defaultLights = new SetLights(m_lights, "default");
                // m_wrist = new Wrist();
                m_claw = new Claw(m_lights);
                intake = new MoveClawManually(m_claw, -Constants.WristConstants.clawSpeed);
                outtake = new MoveClawManually(m_claw, Constants.WristConstants.clawSpeed);
                // telescope = new Telescope();
                arm = new Arm();
                manualControls = new ManualControls(new XboxController(0), new XboxController(1));
                wrist2 = new Wrist2();
                moveWristUp = new MoveWristManually(wrist2, 0.6);
                moveWristDown = new MoveWristManually(wrist2, -0.6);
                limelight = new Limelight(manualControls, () -> drivetrain == null ? null : drivetrain.getPose());
                drivetrain = new Drivetrain(() -> limelight.getBotPose());
                // testPosition = new MoveWristToAngle(wrist2, 60);
                // testPositionArm = new MoveArmToAngle(arm, 130);
                // Constants.WristConstants.testPos);
                // highConeArmPosition = new MoveArmToAngle(arm,
                // Constants.ArmConstants.highConeArmPosition);
                // fullyExtendedPosition = new MoveTelescopeToPosition(telescope,
                // Constants.TelescopeConstants.fullyExtendedPosition);

                autoBuilder = new AutoBuilder(drivetrain, m_claw, arm, wrist2);

                driveCommand = new DriveCommand(drivetrain, autoBuilder, limelight, manualControls);
                drivetrain.setDefaultCommand(driveCommand);

                // intake = new Intake();
                // intakeCommand = new IntakeCommand(intake, manualControls);
                // intake.setDefaultCommand(intakeCommand);

                autoChooser = new SendableChooser<Command>();

                // Add commands to the autonomous command chooser
                String defaultAuto = "Straight";
                // autoChooser.setDefaultOption("Straight", autoBuilder.straight());
                autoChooser.addOption("StraightwithIntake", autoBuilder.getPathStraightWithIntakeOut());
                autoChooser.addOption("AutoBalance", autoBuilder.getAutoBalanceCommand());
                autoChooser.addOption("Straight", autoBuilder.getStraight());
                autoChooser.addOption("MidAround1", autoBuilder.getMidAround());
                autoChooser.addOption("OuttakeMidMobilityLong", autoBuilder.OuttakeMidMobilityLong());

                // for (String f : (new File(Filesystem.getDeployDirectory().getPath() +
                // "/pathplanner")).list()) {
                // f = f.strip().replace(".path", "");
                // if (!f.equals(defaultAuto)) {
                // autoChooser.addOption(f, f);
                // System.out.println(f);
                // }
                // }

                // autoChooser.addOption("StraightSpin", "StraightSpin");
                // autoChooser.addOption("StraightAndBackSpin", "StraightAndBackSpin");
                // autoChooser.addOption("StraightAndBack", "StraightAndBack");
                // autoChooser.addOption("Hamid", "Hamid");
                // autoChooser.addOption("HamidSharp", "HamidSharp");
                // autoChooser.addOption("ChargingStationAuto", "ChargingStationAuto");
                // autoChooser.addOption("StraightwithIntake", "StraightwithIntake");
                // autoChooser.addOption("NewPath", "NewPath");

                // Put the chooser on the dashboard
                Shuffleboard.getTab("Auto Choose").add(autoChooser);
                armJoystick = new Joystick(Constants.TelescopeConstants.logitechID);

                POV0button = new POVButton(armJoystick, Constants.ArmJoystickConstants.buttonPOV0);
                POV180button = new POVButton(armJoystick, Constants.ArmJoystickConstants.buttonPOV180);

                button1 = new JoystickButton(armJoystick, Constants.ArmJoystickConstants.button1);
                button2 = new JoystickButton(armJoystick, Constants.ArmJoystickConstants.button2);
                button3 = new JoystickButton(armJoystick, Constants.ArmJoystickConstants.button3);
                button4 = new JoystickButton(armJoystick, Constants.ArmJoystickConstants.button4);
                button5 = new JoystickButton(armJoystick, Constants.ArmJoystickConstants.button5);
                button6 = new JoystickButton(armJoystick, Constants.ArmJoystickConstants.button6);
                button7 = new JoystickButton(armJoystick, Constants.ArmJoystickConstants.button7);
                button8 = new JoystickButton(armJoystick, Constants.ArmJoystickConstants.button8);
                button9 = new JoystickButton(armJoystick, Constants.ArmJoystickConstants.button9);
                button10 = new JoystickButton(armJoystick, Constants.ArmJoystickConstants.button10);
                button11 = new JoystickButton(armJoystick, Constants.ArmJoystickConstants.button11);
                button12 = new JoystickButton(armJoystick, Constants.ArmJoystickConstants.button12);
                configureBindings();
        }

        private void configureBindings() {
                // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
                // new Trigger(m_exampleSubsystem::exampleCondition)
                // .onTrue(new ExampleCommand(m_exampleSubsystem));

                // // Schedule `exampleMethodCommand` when the Xbox controller's B button is
                // pressed,
                // // cancelling on release.
                // m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());

                POV0button.whileTrue(moveWristUp);
                POV180button.whileTrue(moveWristDown);

                // // // RESERVED FOR INTAKE AND OUTTAKE
                button1.whileTrue(outtake);
                button2.whileTrue(intake);

                // // RESERVED FOR PRESET POSITIONS OF ENTIRE ARM
                // // Right hand buttons
                // button3.onTrue(coneLights);
                // button4.onTrue(cubeLights);
                button5.onTrue(coneLights);
                button6.onTrue(cubeLights);

                // // // Left hand buttons
                // button7.onTrue(coneLights);
                // button8.onTrue(cubeLights);
                // button9.onTrue(defaultLights);

                // button10.onTrue(testPosition);
                // Score Cube High !
                button8.onTrue(new SequentialCommandGroup(
                        new MoveWristToAngle(wrist2, 5, 0.3), new MoveArmToAngle(arm, 107,0.45), new MoveWristToAngle(wrist2, 92, 0.3)
                ));
                // Score Cone Mid / Cube Mid ! 
                button10.onTrue(new SequentialCommandGroup(
                        new MoveWristToAngle(wrist2, 5, 0.3), new MoveArmToAngle(arm, 104,0.45), new MoveWristToAngle(wrist2, 88, 0.3)
                ));
                // Score Cube Floor
                // button10.onTrue(new SequentialCommandGroup(
                //         new MoveWristToAngle(wrist2, 4), new MoveArmToAngle(arm, 178), new MoveWristToAngle(wrist2, 12)
                // ));
                // Pickup Cube Floor !
                button11.onTrue(new SequentialCommandGroup(
                        new MoveWristToAngle(wrist2, 5, 0.3), new MoveArmToAngle(arm, 154,0.6), new MoveWristToAngle(wrist2, 123, 0.3)
                ));
                // // Shoot Cone !
                button7.onTrue(new SequentialCommandGroup(
                        new MoveWristToAngle(wrist2, 5, 0.3), new MoveArmToAngle(arm, 128,0.45), new MoveWristToAngle(wrist2, 18, 0.3)
                ));

                // Zero Position !
                button12.onTrue(new SequentialCommandGroup(
                        new MoveWristToAngle(wrist2, 5, 0.3), new MoveArmToAngle(arm, 178,0.6)
                ));

                // Shoot Cube !
                button9.onTrue(new SequentialCommandGroup(
                        new MoveWristToAngle(wrist2, 5, 0.3), new MoveArmToAngle(arm, 120,0.45), new MoveWristToAngle(wrist2, 95, 0.3)
                ));
                // button11.onTrue(testPositionArm);
                // button12.whileTrue(fullyExtendedPosition);
        }

        /**
         * Use this to pass the autonomous command to the main {@link Robot} class.
         *
         * @return the command to run in autonomous
         */
        public Command getAutonomousCommand() {
                return autoChooser.getSelected();
                // return autoBuilder.getPathStraightWithIntakeOut();
                // return autoBuilder.getAutoCommand(autoChooser.getSelected());
                // return null;
        }
}
