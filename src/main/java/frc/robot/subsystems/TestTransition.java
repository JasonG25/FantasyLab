package frc.robot.subsystems;

import frc.robot.subsystems.TestSubsystem.TestState;
import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
        SmartDashboard.putBoolean(this.toString(), isTriggeredCondition.getAsBoolean());
        return isTriggeredCondition.getAsBoolean();
    }

    public void performTransitionAction() {
        if(! this.isSuccess()){
            SmartDashboard.putBoolean(this.toString()+"is running", true);
            action.run();
        }
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

    @Override
    public String toString(){
        return "Transforming to" + targetState.toString();
    }
}