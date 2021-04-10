package rdsalakhov.picedit.picedit.service;

import java.awt.image.BufferedImage;

public interface IImageProcessor {
    void convertToNegative();

    void convertToBlackAndWhite();

    void addColorGreenChannel(int delta);

    BufferedImage getResult();
}
