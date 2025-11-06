package in.ayush.swasthyapath.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

public class BackgroundPageEvent extends PdfPageEventHelper {

    private Image backgroundImage;

    public BackgroundPageEvent() {
        try {
            InputStream bgStream = new ClassPathResource("static/images/bg-watermark.png").getInputStream();
            backgroundImage = Image.getInstance(bgStream.readAllBytes());

            // Making it slightly bigger so that it can fit correctly.
            float width = PageSize.A4.getWidth() + 40;
            float height = PageSize.A4.getHeight() + 40;
            backgroundImage.scaleToFit(width, height);

            // Center align the watermark on page
            backgroundImage.setAbsolutePosition(
                    (PageSize.A4.getWidth() - width) / 2,
                    (PageSize.A4.getHeight() - height) / 2
            );

            backgroundImage.setTransparency(new int[]{0x00, 0x0A});

        } catch (IOException | BadElementException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        if (backgroundImage != null) {
            PdfContentByte canvas = writer.getDirectContentUnder();
            try {
                canvas.addImage(backgroundImage);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    }
}
