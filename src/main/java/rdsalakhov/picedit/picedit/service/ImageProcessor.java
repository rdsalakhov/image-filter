package rdsalakhov.picedit.picedit.service;

import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;


public class ImageProcessor implements IImageProcessor {
    private int[] pixels;
    private int width;
    private int height;

    public ImageProcessor(BufferedImage img){
        this.height = img.getHeight();
        this.width  = img.getWidth();
        this.pixels = copyFromBufferedImage(img);
    }

    @Override
    public void  convertToNegative() {
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                // Применяем логическое отрицание и отбрасываем старший байт
                pixels[i*width + j] = ~pixels[i*width + j] & 0xFFFFFF;
    }

    @Override
    public void convertToBlackAndWhite() {
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                // находим среднюю арифметическую интенсивность пикселя по всем цветам
                int intens = (getRed(pixels[i * width + j]) +
                        getGreen(pixels[i * width + j]) +
                        getBlue(pixels[i * width + j])) / 3;
                // ... и записываем ее в каждый цвет за раз , сдвигая байты RGB на свои места
                pixels[i * width + j] = intens + (intens << 8) + (intens << 16);
            }
    }

    @Override
    public void addColorGreenChannel(int delta) {
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                int newGreen =  getGreen(pixels[i * width + j]) + delta;
                if (newGreen > 255) newGreen=255;  // Отсекаем при превышении границ байта
                if (newGreen < 0)   newGreen=0;
                // В итоговом пикселе R и B цвета оставляем без изменений: & 0xFF00FF
                // Полученный новый G (зеленый) засунем в "серединку" RGB: | (newGreen << 8)
                pixels[i * width + j] = pixels[i * width + j] & 0xFF00FF | (newGreen << 8);
            }
    }

    @Override
    public BufferedImage getResult(){
        BufferedImage img = copyToBufferedImage();
        return img;
    }

    private int getRed(int color){
        return color >> 16;
    }
    private int getGreen(int color){
        return (color >> 8) & 0xFF;
    }
    private int getBlue(int color){
        return color  & 0xFF;
    }

    private int[] copyFromBufferedImage(BufferedImage bi)  {
        int[] pict = new int[height*width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                pict[i*width + j] = bi.getRGB(j, i) & 0xFFFFFF; // 0xFFFFFF: записываем только 3 младших байта RGB
        return pict;
    }

    private BufferedImage copyToBufferedImage()  {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                bi.setRGB(j, i, pixels[i*width +j]);
        return bi;
    }
}
