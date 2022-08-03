package org.team2363.helixnavigator.document.drive;

import java.util.List;

import org.team2363.helixnavigator.global.Standards.DefaultSwerveDrive;
import org.team2363.helixnavigator.global.Standards.DefaultSwerveModule;

import com.jlbabilino.json.DeserializedJSONConstructor;
import com.jlbabilino.json.DeserializedJSONObjectValue;
import com.jlbabilino.json.DeserializedJSONTarget;
import com.jlbabilino.json.SerializedJSONObjectValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HSwerveDrive extends HHolonomicDrive {
    
    private final ObservableList<HSwerveModule> modules = FXCollections.observableArrayList();

    @DeserializedJSONConstructor
    public HSwerveDrive() {
        HSwerveModule module0 = new HSwerveModule();
        module0.setX(+DefaultSwerveDrive.WHEELBASE_X);
        module0.setY(+DefaultSwerveDrive.WHEELBASE_Y);
        module0.setWheelRadius(DefaultSwerveModule.WHEEL_RADIUS);
        module0.setWheelMaxAngularVelocity(DefaultSwerveModule.WHEEL_MAX_ANGULAR_VELOCITY);
        module0.setWheelMaxTorque(DefaultSwerveModule.WHEEL_MAX_TORQUE);
        HSwerveModule module1 = new HSwerveModule();
        module0.setX(+DefaultSwerveDrive.WHEELBASE_X);
        module0.setY(-DefaultSwerveDrive.WHEELBASE_Y);
        module0.setWheelRadius(DefaultSwerveModule.WHEEL_RADIUS);
        module0.setWheelMaxAngularVelocity(DefaultSwerveModule.WHEEL_MAX_ANGULAR_VELOCITY);
        module0.setWheelMaxTorque(DefaultSwerveModule.WHEEL_MAX_TORQUE);
        HSwerveModule module2 = new HSwerveModule();
        module0.setX(-DefaultSwerveDrive.WHEELBASE_X);
        module0.setY(+DefaultSwerveDrive.WHEELBASE_Y);
        module0.setWheelRadius(DefaultSwerveModule.WHEEL_RADIUS);
        module0.setWheelMaxAngularVelocity(DefaultSwerveModule.WHEEL_MAX_ANGULAR_VELOCITY);
        module0.setWheelMaxTorque(DefaultSwerveModule.WHEEL_MAX_TORQUE);
        HSwerveModule module3 = new HSwerveModule();
        module0.setX(-DefaultSwerveDrive.WHEELBASE_X);
        module0.setY(-DefaultSwerveDrive.WHEELBASE_Y);
        module0.setWheelRadius(DefaultSwerveModule.WHEEL_RADIUS);
        module0.setWheelMaxAngularVelocity(DefaultSwerveModule.WHEEL_MAX_ANGULAR_VELOCITY);
        module0.setWheelMaxTorque(DefaultSwerveModule.WHEEL_MAX_TORQUE);
        modules.addAll(module0, module1, module2, module3);
    }

    @DeserializedJSONTarget
    public void setModules(@DeserializedJSONObjectValue(key = "modules") List<? extends HSwerveModule> value) {
        modules.setAll(value);
    }
    @SerializedJSONObjectValue(key = "modules")
    public ObservableList<HSwerveModule> getModules() {
        return modules;
    }
}