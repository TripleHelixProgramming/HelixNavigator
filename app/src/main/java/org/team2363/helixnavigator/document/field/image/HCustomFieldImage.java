package org.team2363.helixnavigator.document.field.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.scene.image.Image;

public class HCustomFieldImage implements HFieldImage {

    private final String name;
    private final double imageRes;
    private final double imageCenterX;
    private final double imageCenterY;
    private final String fileName;
    private final Image image;

    public HCustomFieldImage(String name,
            double imageRes,
            double imageCenterX,
            double imageCenterY,
            String fileName) {
        this.name = name;
        this.imageRes = imageRes;
        this.imageCenterX = imageCenterX;
        this.imageCenterY = imageCenterY;
        this.fileName = fileName;
        this.image = imageFromFileName(fileName);
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double getImageRes() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getImageCenterX() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getImageCenterY() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Image getImage() {
        // TODO Auto-generated method stub
        return null;
    }
    
    private static Image imageFromFileName(String fileName) {
        File file = new File(HCustomFieldImage.class.getResource(fileName).getFile());
        try {
            FileInputStream inputStream = new FileInputStream(file);
            return new Image(inputStream);
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
