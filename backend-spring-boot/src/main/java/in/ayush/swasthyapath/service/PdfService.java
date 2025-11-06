package in.ayush.swasthyapath.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import in.ayush.swasthyapath.dto.*;
import in.ayush.swasthyapath.utils.BackgroundPageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;

@Service
@Slf4j
public class PdfService {

    @Autowired
    private ExecutorService executor;
    private final Font titleFont;
    private final Font sectionFont;
    private final Font normalFont;
    private final Font boldFont;

    public PdfService() {
        try {
            // Loading fonts.
            InputStream fontStream = new ClassPathResource("fonts/Poppins-Regular.ttf").getInputStream();
            InputStream mediumFontStream = new ClassPathResource("fonts/Poppins-Medium.ttf").getInputStream();
            InputStream boldFontStream = new ClassPathResource("fonts/Poppins-Bold.ttf").getInputStream();

            BaseFont baseFont = BaseFont.createFont("Poppins-Regular.ttf",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true,
                    fontStream.readAllBytes(), null);

            BaseFont baseFontMedium = BaseFont.createFont("Poppins-Medium.ttf",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true,
                    mediumFontStream.readAllBytes(), null);

            BaseFont baseFontBold = BaseFont.createFont("Poppins-Bold.ttf",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true,
                    boldFontStream.readAllBytes(), null);

            this.titleFont = new Font(baseFontBold, 18, Font.BOLD, new BaseColor(56, 142, 60));
            this.sectionFont = new Font(baseFontMedium, 13, Font.UNDERLINE, BaseColor.BLACK);
            this.normalFont = new Font(baseFont, 11, Font.NORMAL, new BaseColor(66, 66, 66));
            this.boldFont = new Font(baseFontBold, 12, Font.BOLD, new BaseColor(33, 33, 33));

        } catch(Exception e) {
            throw new RuntimeException("Failed to load custom fonts", e);
        }
    }

    // generatePdfAsync returns a filepath.
    public CompletableFuture<String> generatePdfAsync(ResponseData responseData, String filename) throws IOException, DocumentException {
        return CompletableFuture.supplyAsync(() -> {
            try {

                log.info("Generating PDF for patient: {}", responseData.getPatient().getName());

                Patient patientData = responseData.getPatient();
                HealthResponse healthResponse = responseData.getHealthResponse();

                // Creating the folder if not exists.
                File dir = new File("pdfs");
                if (!dir.exists()) dir.mkdirs();

                // Path of PDF file

                File file = new File("pdfs", filename + ".pdf");

                Document document = new Document();
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
                writer.setPageEvent(new BackgroundPageEvent());

                document.open();


                // Patient Information.
                Paragraph patientInformationTitle = new Paragraph("Patient Information", titleFont);
                patientInformationTitle.setAlignment(Element.ALIGN_CENTER);
                document.add(patientInformationTitle);
                document.add(new Paragraph("\n"));

                PdfPTable table = new PdfPTable(2);
                table.setWidthPercentage(100);
                table.setSpacingBefore(10f);
                table.setSpacingAfter(10f);
                table.setWidths(new float[]{1f, 2f});

                addTableRow(table, "Name", patientData.getName(), sectionFont, normalFont);
                addTableRow(table, "Age", String.valueOf(patientData.getAge()), sectionFont, normalFont);
                addTableRow(table, "Gender", String.valueOf(patientData.getGender()), sectionFont, normalFont);
                addTableRow(table, "Height", String.format("%.1fcm", patientData.getHeight()), sectionFont, normalFont);
                addTableRow(table, "Weight", String.format("%.2fkg", patientData.getWeight()), sectionFont, normalFont);
                addTableRow(table, "Prakruti", String.valueOf(patientData.getPrakruti()), sectionFont, normalFont);
                addTableRow(table, "Vikruti", String.valueOf(patientData.getVikruti()), sectionFont, normalFont);
                document.add(table);
                document.add(new Paragraph("\n"));

                // Main content
                Paragraph healthTitle = new Paragraph("Health Plan", titleFont);
                healthTitle.setAlignment(Element.ALIGN_CENTER);
                document.add(healthTitle);
                document.add(new Paragraph("\n"));

                for(Meal meal: healthResponse.getDayPlan()) {
                    document.add(new Paragraph(meal.getMeal() + ":", sectionFont));
                    document.add(new Paragraph("Items: " + meal.getItems(), normalFont));
                    document.add(new Paragraph("Calories: " + meal.getCalories(), normalFont));
                    document.add(new Paragraph("Rasa: " + meal.getRasa(), normalFont));
                    document.add(new Paragraph("Guna: " + meal.getProperty(), normalFont));
                    document.add(new Paragraph("\n"));
                }


                document.add(new Paragraph("Guidelines:", titleFont));
                for (String guideline : healthResponse.getGuidelines()) {
                    document.add(new Paragraph("- " + guideline, normalFont));
                }

                document.close();

                log.info("PDF successfully created at: {}", file.getAbsolutePath());

                return file.getAbsolutePath();
            } catch (Exception ex) {
                throw new CompletionException(ex);
            }
        }, executor);
    }


    private void addTableRow(PdfPTable table, String key, String value, Font keyFont, Font valueFont) {
        PdfPCell cellKey = new PdfPCell(new Phrase(key, keyFont));
        cellKey.setBorder(Rectangle.NO_BORDER);
        table.addCell(cellKey);

        PdfPCell cellValue = new PdfPCell(new Phrase(value, valueFont));
        cellValue.setBorder(Rectangle.NO_BORDER);
        table.addCell(cellValue);
    }
}
