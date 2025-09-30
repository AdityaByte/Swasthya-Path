package in.ayush.swasthyapath.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import in.ayush.swasthyapath.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfService {

    private final ExecutorService executor;

    // generatePdfAsync returns a filepath.
    public CompletableFuture<String> generatePdfAsync(ResponseData responseData, String filename) throws IOException, DocumentException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Patient patientData = responseData.getPatient();
                HealthResponse healthResponse = responseData.getHealthResponse();

                // Creating the folder if not exists.
                File dir = new File("pdfs");
                if (!dir.exists()) dir.mkdirs();

                // Path of PDF file

                File file = new File("pdfs", filename + ".pdf");

                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(file));

                document.open();

                // Defining the fonts.
                Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLUE);
                Font sectionFont = new Font(Font.FontFamily.HELVETICA, 13, Font.UNDERLINE, BaseColor.BLACK);
                Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
                Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

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

                addTableRow(table, "Name", patientData.getName(), boldFont, normalFont);
                addTableRow(table, "Age", String.valueOf(patientData.getAge()), boldFont, normalFont);
                addTableRow(table, "Gender", String.valueOf(patientData.getGender()), boldFont, normalFont);
                addTableRow(table, "Height", patientData.getHeight() + " cm", boldFont, normalFont);
                addTableRow(table, "Weight", patientData.getWeight() + " kg", boldFont, normalFont);
                addTableRow(table, "Prakruti", String.valueOf(patientData.getPrakruti()), boldFont, normalFont);
                addTableRow(table, "Vikruti", String.valueOf(patientData.getVikruti()), boldFont, normalFont);
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
