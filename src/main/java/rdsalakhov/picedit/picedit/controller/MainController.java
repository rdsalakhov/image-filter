package rdsalakhov.picedit.picedit.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rdsalakhov.picedit.picedit.service.IImageProcessor;
import rdsalakhov.picedit.picedit.service.ImageProcessorFactory;
import rdsalakhov.picedit.picedit.service.ImageRenderer;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController()
public class MainController {
    private final ImageRenderer renderer;
    private final ImageProcessorFactory processorFactory;

    @Autowired
    public MainController(ImageRenderer renderer, ImageProcessorFactory processorFactory) {
        this.renderer = renderer;
        this.processorFactory = processorFactory;
    }

    @PostMapping(path = "/negative",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] NegativeFilter(@RequestParam("file") MultipartFile file) throws IOException {
        BufferedImage image = renderer.getBufferedImage(file.getInputStream());
        IImageProcessor imageBuilder = processorFactory.getImageProcessor(image);

        imageBuilder.convertToNegative();
        ByteArrayOutputStream jpegStream = renderer.getJpegStream(imageBuilder.getResult());

        return jpegStream.toByteArray();
    }

    @PostMapping(path = "/green",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] GreenFilter(@RequestParam("file") MultipartFile file, Integer delta) throws IOException {
        BufferedImage image = renderer.getBufferedImage(file.getInputStream());
        IImageProcessor imageBuilder = processorFactory.getImageProcessor(image);

        imageBuilder.addColorGreenChannel(delta);
        ByteArrayOutputStream jpegStream = renderer.getJpegStream(imageBuilder.getResult());

        return jpegStream.toByteArray();
    }

    @PostMapping(path = "/monochrome",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] MonochromeFilter(@RequestParam("file") MultipartFile file) throws IOException {
        BufferedImage image = renderer.getBufferedImage(file.getInputStream());
        IImageProcessor imageBuilder = processorFactory.getImageProcessor(image);

        imageBuilder.convertToBlackAndWhite();
        ByteArrayOutputStream jpegStream = renderer.getJpegStream(imageBuilder.getResult());

        return jpegStream.toByteArray();
    }
}
