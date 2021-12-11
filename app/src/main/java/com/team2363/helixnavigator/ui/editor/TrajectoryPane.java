package com.team2363.helixnavigator.ui.editor;

import com.team2363.lib.file.TextFileReader;
import com.team2363.lib.json.JSON;
import com.team2363.lib.json.JSONEntry;
import com.team2363.lib.json.JSONParser;
import com.team2363.lib.json.JSONObject;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class TrajectoryPane extends StackPane {
    private final ImageView imageView = new ImageView();

    public TrajectoryPane(ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height) {
        // try {
        //     String jsonString = new TextFileReader("A:/Users/Justin Babilino/Documents/Triple Helix/Programming/2021/vscode/helixnavigator/src/main/resources/pathtest.json").read();
        //     JSON json = new JSONParser(jsonString).getJSON();

        //     Canvas c = new Canvas(1500, 1000);
        //     c.setOnScroll(e -> {
        //         System.out.println("scrolling");
        //         double scaleFactor = c.getScaleX() + e.getDeltaY() / 1000.0;
        //         c.setTranslateX(e.getX() / scaleFactor);
        //         c.setTranslateY(e.getY() / scaleFactor);
        //         c.setScaleX(c.getScaleX() + scaleFactor);
        //         c.setScaleY(c.getScaleY() + scaleFactor);
        //     });
        //     GraphicsContext g = c.getGraphicsContext2D();
        //     g.setStroke(Color.color(0, 0, 0));
        //     g.setLineWidth(2);
        //     g.beginPath();
        //     JSONEntry[] points = json.getRoot().getArray();
        //     for (JSONEntry point : points) {
        //         double x = point.getKeyedEntry("x").getDouble();
        //         double y = point.getKeyedEntry("y").getDouble();
        //         System.out.println(x + ", " + y);
        //         g.lineTo((x-2.5)*150, (y-4)*150);
        //         g.stroke();
        //     }

        //     g.closePath();
        //     getChildren().add(c);
        // } catch (Exception e) {
        //     System.out.println(e);
        // }
    }
}