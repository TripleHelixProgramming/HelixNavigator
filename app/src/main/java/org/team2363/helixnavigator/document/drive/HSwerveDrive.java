package org.team2363.helixnavigator.document.drive;

import java.util.List;

import org.team2363.helixnavigator.global.Standards.DefaultDrive;
import org.team2363.helixnavigator.global.Standards.DefaultSwerveDrive;

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
    }

    @Override
    public HolonomicDriveType getHolonomicDriveType() {
        return HolonomicDriveType.SWERVE;
    }
    @Override
    public boolean isSwerveHolonomicDrive() {
        return true;
    }

    @DeserializedJSONTarget
    public void setModules(@DeserializedJSONObjectValue(key = "modules") List<? extends HSwerveModule> value) {
        modules.setAll(value);
    }
    @SerializedJSONObjectValue(key = "modules")
    public ObservableList<HSwerveModule> getModules() {
        return modules;
    }

    public static HSwerveDrive defaultSwerveDrive() {
        HSwerveDrive swerveDrive = new HSwerveDrive();

        swerveDrive.setMass(DefaultDrive.MASS);
        swerveDrive.setMomentOfInertia(DefaultDrive.MOMENT_OF_INERTIA);
        swerveDrive.setBumpers(HDrive.defaultBumpers());

        HSwerveModule module0 = HSwerveModule.defaultSwerveModule();
        module0.setX(+DefaultSwerveDrive.WHEELBASE_X);
        module0.setY(+DefaultSwerveDrive.WHEELBASE_Y);
        HSwerveModule module1 = new HSwerveModule();
        module0.setX(+DefaultSwerveDrive.WHEELBASE_X);
        module0.setY(-DefaultSwerveDrive.WHEELBASE_Y);
        HSwerveModule module2 = new HSwerveModule();
        module0.setX(-DefaultSwerveDrive.WHEELBASE_X);
        module0.setY(+DefaultSwerveDrive.WHEELBASE_Y);
        HSwerveModule module3 = new HSwerveModule();
        module0.setX(-DefaultSwerveDrive.WHEELBASE_X);
        module0.setY(-DefaultSwerveDrive.WHEELBASE_Y);

        swerveDrive.getModules().addAll(module0, module1, module2, module3);

        return swerveDrive;
    }
}