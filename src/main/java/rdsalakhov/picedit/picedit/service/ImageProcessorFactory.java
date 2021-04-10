package rdsalakhov.picedit.picedit.service;

import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Component
public class ImageProcessorFactory implements IImageProcessorFactory {
    public IImageProcessor getImageProcessor(BufferedImage img) {
        return new ImageProcessor(img);
    }
}
