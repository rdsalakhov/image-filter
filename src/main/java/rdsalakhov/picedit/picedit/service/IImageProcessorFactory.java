package rdsalakhov.picedit.picedit.service;

import java.awt.image.BufferedImage;

public interface IImageProcessorFactory {
    IImageProcessor getImageProcessor(BufferedImage img);
}
