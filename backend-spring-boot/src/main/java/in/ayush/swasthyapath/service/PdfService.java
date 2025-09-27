//package in.ayush.swasthyapath.service;
//
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//import in.ayush.swasthyapath.dto.BasicAssessment;
//import in.ayush.swasthyapath.dto.HealthResponse;
//import in.ayush.swasthyapath.dto.AyurvedaAssessment;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//@Service
//public class PdfService {
//
//    public String generatePdf(AyurvedaAssessment ayurvedaAssessment, HealthResponse healthResponse, String fileName) throws IOException, DocumentException {
//
//        BasicAssessment basicAssessment = ayurvedaAssessment.getBasicInfo();
//
//        // Creating the folder if not exists.
//        File dir = new File("pdfs");
//        if (!dir.exists()) dir.mkdirs();
//
//        // Path of PDF file
//        String filePath = "pdfs/" + fileName + ".pdf";
//
//        Document document = new Document();
//        PdfWriter.getInstance(document, new FileOutputStream(filePath));
//
//        document.open();
//
//        // Defining the fonts.
//        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLUE);
//        Font sectionFont = new Font(Font.FontFamily.HELVETICA, 13, Font.UNDERLINE, BaseColor.GREEN);
//        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
//        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
//
//        // Patient Information.
//        Paragraph patientInformationTitle = new Paragraph("Patient Information", titleFont);
//        patientInformationTitle.setAlignment(Element.ALIGN_CENTER);
//        document.add(patientInformationTitle);
//        document.add(new Paragraph("\n"));
//
//        PdfPTable table = new PdfPTable(2);
//        table.setWidthPercentage(100);
//        table.setSpacingBefore(10f);
//        table.setSpacingAfter(10f);
//        table.setWidths(new float[]{1f, 2f});
//
//        addTableRow(table, "Name", basicAssessment.getName(), boldFont, normalFont);
//        addTableRow(table, "Age", String.valueOf(basicAssessment.getAge()), boldFont, normalFont);
//        addTableRow(table, "Gender", String.valueOf(basicAssessment.getGender()), boldFont, normalFont);
//        addTableRow(table, "Height", basicAssessment.getHeight() + " cm", boldFont, normalFont);
//        addTableRow(table, "Weight", basicAssessment.getWeight() + " kg", boldFont, normalFont);
//        addTableRow(table, "Activity Level", String.valueOf(basicAssessment.getActivityLevel()), boldFont, normalFont);
//        addTableRow(table, "Meal Frequency", String.valueOf(basicAssessment.getMealFrequency()), boldFont, normalFont);
//        addTableRow(table, "Sleeping Schedule", String.valueOf(basicAssessment.getSleepingSchedule()), boldFont, normalFont);
//        addTableRow(table, "Hours of Sleep", String.valueOf(basicAssessment.getHoursOfSleep()), boldFont, normalFont);
//        addTableRow(table, "Water Intake", String.valueOf(basicAssessment.getWaterIntake()) + " glasses/day", boldFont, normalFont);
//        addTableRow(table, "Preferred Food Genre", String.valueOf(basicAssessment.getPreferredFoodGenre()), boldFont, normalFont);
//
//        document.add(table);
//        document.add(new Paragraph("\n"));
//
//        // Main content
//        Paragraph healthTitle = new Paragraph("Health Plan", titleFont);
//        healthTitle.setAlignment(Element.ALIGN_CENTER);
//        document.add(healthTitle);
//        document.add(new Paragraph("\n"));
//
//
//        document.add(new Paragraph("Breakfast: ", sectionFont));
//        document.add(new Paragraph(healthResponse.getDayPlan().getBreakfast(), normalFont));
//        document.add(new Paragraph("Lunch: ", sectionFont));
//        document.add(new Paragraph(healthResponse.getDayPlan().getLunch(), normalFont));
//        document.add(new Paragraph("Dinner: ", sectionFont));
//        document.add(new Paragraph(healthResponse.getDayPlan().getDinner(), normalFont));
//        document.add(new Paragraph("Snacks: ", sectionFont));
//        document.add(new Paragraph(healthResponse.getDayPlan().getSnacks(), normalFont));
//        document.add(new Paragraph("\n"));
//
//
//        document.add(new Paragraph("Guidelines:", titleFont));
//        for (String guideline : healthResponse.getGuidelines()) {
//            document.add(new Paragraph("- " + guideline, normalFont));
//        }
//
//        document.close();
//
//        return "/pdfs/" + fileName + ".pdf";
//    }
//
//
//    private void addTableRow(PdfPTable table, String key, String value, Font keyFont, Font valueFont) {
//        PdfPCell cellKey = new PdfPCell(new Phrase(key, keyFont));
//        cellKey.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cellKey);
//
//        PdfPCell cellValue = new PdfPCell(new Phrase(value, valueFont));
//        cellValue.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cellValue);
//    }
//}
