package rdsalakhov.picedit.picedit.service;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;

@Component
public class ImageRenderer {
    public ByteArrayOutputStream getJpegStream(BufferedImage img) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(img, "JPEG", stream);
        return stream;
    }

    public ByteArrayOutputStream getPngStream(BufferedImage img) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(img, "PNG", stream);
        return stream;
    }

    public BufferedImage getBufferedImage(InputStream stream) throws IOException {
        return ImageIO.read(stream);
    }
}
