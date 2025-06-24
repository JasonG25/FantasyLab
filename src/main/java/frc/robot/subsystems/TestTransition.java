package frc.robot.subsystems;

import frc.robot.subsystems.TestStatemachine.TestState;
import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Timer;

public class TestTransition {
    private final TestState targetState;
    private final BooleanSupplier isTriggeredCondition;
    private final Runnable action;
    private final BooleanSupplier isSuccessCondition;
    private final BooleanSupplier isExpiredCondition;
    private double startTime = -1;
    private final double maxTime;

    public TestTransition(TestState targetState,
                          BooleanSupplier isTriggeredCondition,
                          Runnable action,
                          BooleanSupplier isSuccessCondition,
                          BooleanSupplier isExpiredCondition,
                          double maxTime) {
        this.targetState = targetState;
        this.isTriggeredCondition = isTriggeredCondition;
        this.action = action;
        this.isSuccessCondition = isSuccessCondition;
        this.isExpiredCondition = isExpiredCondition;
        this.maxTime = maxTime;
    }

    public boolean isTriggered() {
        return isTriggeredCondition.getAsBoolean();
    }

    public void performTransitionAction() {
        action.run();
    }

    public boolean isSuccess() {
        return isSuccessCondition.getAsBoolean();
    }

    public TestState getNextState() {
        return targetState;
    }

    public boolean isExpired() {
        return isExpiredCondition.getAsBoolean() || this.getTimeSpent() > maxTime;
    }

    public double getTimeSpent(){
        return Timer.getFPGATimestamp() - startTime;
    }

    public void startTimer(){
        startTime = Timer.getFPGATimestamp();
    }
}