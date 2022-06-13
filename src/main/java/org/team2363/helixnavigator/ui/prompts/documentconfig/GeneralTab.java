package org.team2363.helixnavigator.ui.prompts.documentconfig;

import org.team2363.helixnavigator.document.HDocument;
import org.team2363.helixnavigator.document.field.image.HFieldImage;
import org.team2363.helixnavigator.document.field.image.HReferenceFieldImage;
import org.team2363.helixnavigator.global.DefaultFieldImages;
import org.team2363.helixnavigator.global.DefaultResourceUnavailableException;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedAcceleration;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedAngle;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedAngularSpeed;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedLength;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedMass;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedMomentOfInertia;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedSpeed;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedTime;
import org.team2363.helixnavigator.global.Standards.SupportedUnits.SupportedTorque;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class GeneralTab extends Tab {

    private static final ObservableList<String> fieldImageChoices = FXCollections.observableArrayList();

    static {
        fieldImageChoices.add("None");
        fieldImageChoices.addAll(DefaultFieldImages.listNames());
    }

    private final HDocument document;

    private final Text fieldImageText = new Text("Field Image:");
    private final ChoiceBox<String> fieldImageChoiceBox = new ChoiceBox<>();

    private final Text timeUnitText = new Text("Time Unit:");
    private final ChoiceBox<SupportedTime> timeUnitChoiceBox = new ChoiceBox<>(SupportedTime.SUPPORTED_UNITS);
    private final Text lengthUnitText = new Text("Length Unit:");
    private final ChoiceBox<SupportedLength> lengthUnitChoiceBox = new ChoiceBox<>(SupportedLength.SUPPORTED_UNITS);
    private final Text massUnitText = new Text("Mass Unit:");
    private final ChoiceBox<SupportedMass> massUnitChoiceBox = new ChoiceBox<>(SupportedMass.SUPPORTED_UNITS);
    private final Text speedUnitText = new Text("Speed Unit:");
    private final ChoiceBox<SupportedSpeed> speedUnitChoiceBox = new ChoiceBox<>(SupportedSpeed.SUPPORTED_UNITS);
    private final Text accelerationUnitText = new Text("Acceleration Unit:");
    private final ChoiceBox<SupportedAcceleration> accelerationUnitChoiceBox = new ChoiceBox<>(SupportedAcceleration.SUPPORTED_UNITS);
    private final Text angleUnitText = new Text("Angle Unit:");
    private final ChoiceBox<SupportedAngle> angleUnitChoiceBox = new ChoiceBox<>(SupportedAngle.SUPPORTED_UNITS);
    private final Text angularSpeedUnitText = new Text("Angular Speed Unit:");
    private final ChoiceBox<SupportedAngularSpeed> angularSpeedUnitChoiceBox = new ChoiceBox<>(SupportedAngularSpeed.SUPPORTED_UNITS);
    private final Text torqueUnitText = new Text("Torque Unit:");
    private final ChoiceBox<SupportedTorque> torqueUnitChoiceBox = new ChoiceBox<>(SupportedTorque.SUPPORTED_UNITS);
    private final Text momentOfInertiaUnitText = new Text("Moment of Inertia Unit:");
    private final ChoiceBox<SupportedMomentOfInertia> momentOfInertiaUnitChoiceBox = new ChoiceBox<>(SupportedMomentOfInertia.SUPPORTED_UNITS);

    private final GridPane optionsGrid = new GridPane();
    
    public GeneralTab(HDocument document) {
        this.document = document;
        setText("General");
        setContent(optionsGrid);
        setClosable(false);

        GridPane.setConstraints(fieldImageText, 0, 0);
        GridPane.setConstraints(fieldImageChoiceBox, 1, 0);

        GridPane.setConstraints(timeUnitText, 0, 1);
        GridPane.setConstraints(timeUnitChoiceBox, 1, 1);
        GridPane.setConstraints(lengthUnitText, 0, 2);
        GridPane.setConstraints(lengthUnitChoiceBox, 1, 2);
        GridPane.setConstraints(massUnitText, 0, 3);
        GridPane.setConstraints(massUnitChoiceBox, 1, 3);
        GridPane.setConstraints(speedUnitText, 0, 4);
        GridPane.setConstraints(speedUnitChoiceBox, 1, 4);
        GridPane.setConstraints(accelerationUnitText, 0, 5);
        GridPane.setConstraints(accelerationUnitChoiceBox, 1, 5);
        GridPane.setConstraints(angleUnitText, 0, 6);
        GridPane.setConstraints(angleUnitChoiceBox, 1, 6);
        GridPane.setConstraints(angularSpeedUnitText, 0, 7);
        GridPane.setConstraints(angularSpeedUnitChoiceBox, 1, 7);
        GridPane.setConstraints(torqueUnitText, 0, 8);
        GridPane.setConstraints(torqueUnitChoiceBox, 1, 8);
        GridPane.setConstraints(momentOfInertiaUnitText, 0, 9);
        GridPane.setConstraints(momentOfInertiaUnitChoiceBox, 1, 9);

        optionsGrid.setHgap(10.0);
        optionsGrid.setVgap(10.0);
        optionsGrid.getChildren().addAll(fieldImageText, fieldImageChoiceBox, timeUnitText, timeUnitChoiceBox,
                lengthUnitText, lengthUnitChoiceBox, massUnitText, massUnitChoiceBox,
                speedUnitText, speedUnitChoiceBox, accelerationUnitText, accelerationUnitChoiceBox,
                angleUnitText, angleUnitChoiceBox, angularSpeedUnitText, angularSpeedUnitChoiceBox,
                torqueUnitText, torqueUnitChoiceBox, momentOfInertiaUnitText, momentOfInertiaUnitChoiceBox);
        optionsGrid.setPadding(new Insets(10.0, 0.0, 10.0, 0.0));

        // we are trusting that this is the only thing that could change
        // the field image
        fieldImageChoiceBox.setItems(fieldImageChoices);
        HFieldImage currentImage = this.document.getFieldImage();
        if (currentImage == null) {
            fieldImageChoiceBox.getSelectionModel().select(0);
        } else {
            fieldImageChoiceBox.getSelectionModel().select(currentImage.getName());
        }
        fieldImageChoiceBox.setOnAction(event -> {
            String newName = fieldImageChoiceBox.getValue();
            HFieldImage newImage = null;
            if (!newName.equals("None")) {
                try {
                    newImage = new HReferenceFieldImage(newName);
                } catch (DefaultResourceUnavailableException e) {
                }
            }
            this.document.setFieldImage(newImage);
        });

        timeUnitChoiceBox.getSelectionModel().select(this.document.getUnitPreferences().getTimeUnit());
        lengthUnitChoiceBox.getSelectionModel().select(this.document.getUnitPreferences().getLengthUnit());
        massUnitChoiceBox.getSelectionModel().select(this.document.getUnitPreferences().getMassUnit());
        speedUnitChoiceBox.getSelectionModel().select(this.document.getUnitPreferences().getSpeedUnit());
        accelerationUnitChoiceBox.getSelectionModel().select(this.document.getUnitPreferences().getAccelerationUnit());
        angleUnitChoiceBox.getSelectionModel().select(this.document.getUnitPreferences().getAngleUnit());
        angularSpeedUnitChoiceBox.getSelectionModel().select(this.document.getUnitPreferences().getAngularSpeedUnit());
        torqueUnitChoiceBox.getSelectionModel().select(this.document.getUnitPreferences().getTorqueUnit());
        momentOfInertiaUnitChoiceBox.getSelectionModel().select(this.document.getUnitPreferences().getMomentOfInertiaUnit());

        this.document.getUnitPreferences().timeUnitProperty().bind(timeUnitChoiceBox.valueProperty());
        this.document.getUnitPreferences().lengthUnitProperty().bind(lengthUnitChoiceBox.valueProperty());
        this.document.getUnitPreferences().massUnitProperty().bind(massUnitChoiceBox.valueProperty());
        this.document.getUnitPreferences().speedUnitProperty().bind(speedUnitChoiceBox.valueProperty());
        this.document.getUnitPreferences().accelerationUnitProperty().bind(accelerationUnitChoiceBox.valueProperty());
        this.document.getUnitPreferences().angleUnitProperty().bind(angleUnitChoiceBox.valueProperty());
        this.document.getUnitPreferences().angularSpeedUnitProperty().bind(angularSpeedUnitChoiceBox.valueProperty());
        this.document.getUnitPreferences().torqueUnitProperty().bind(torqueUnitChoiceBox.valueProperty());
        this.document.getUnitPreferences().momentOfInertiaUnitProperty().bind(momentOfInertiaUnitChoiceBox.valueProperty());
    }
}