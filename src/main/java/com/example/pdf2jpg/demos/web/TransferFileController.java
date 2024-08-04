
package com.example.pdf2jpg.demos.web;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@RequestMapping("/pdf2jpg")
@RestController

public class TransferFileController {


    @PostMapping({"/switch"})
    public void uploadFile(@RequestParam("file") MultipartFile multipartFile) {

//        String destPath = "/demo/temp/";

//        File file = convertUsingNIO(multipartFile, destPath);

        String destinationDir = "/demo/output/";

        File destinationDirFile = new File(destinationDir);

        if (!destinationDirFile.exists()) {
            System.out.println("文件输出路径不存在,新建路径");
            destinationDirFile.mkdirs();
        }

        try (PDDocument document = PDDocument.load(multipartFile.getInputStream())) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); page++) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
                ImageIO.write(bim, "jpg", new File(
                        destinationDir + multipartFile.getOriginalFilename() + "_image_" + (page + 1) + ".jpg"));
            }
        } catch (IOException e) {
            System.err.println("Exception while trying to create pdf document - " + e);
        }

    }

}
