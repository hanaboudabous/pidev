package tn.esprit.spring.services;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
 
import javax.servlet.http.HttpServletResponse;
 
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import tn.esprit.spring.entity.Payment;

 
 
public class PaymentPdf {
    private List<Payment> listPayments;
     
    public PaymentPdf(List<Payment> listPayments) {
        this.listPayments = listPayments;
    }
 
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(4);
         
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
         
        cell.setPhrase(new Phrase("Payment ID", font));
         
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Payment type", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Payment date", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Payment Amount", font));
        table.addCell(cell);
    }
     
    private void writeTableData(PdfPTable table) {
        for (Payment payment : listPayments) {
            table.addCell(String.valueOf(payment.getPayment_ID()));
            table.addCell((String.valueOf(payment.getType_Payment())));
            table.addCell((String.valueOf(payment.getDate_Payment())));
            table.addCell((String.valueOf(payment.getPayment_Amount())));
        }
    }
     
    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
         
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
         
        Paragraph p = new Paragraph("List of Payments", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
         
        document.add(p);
         
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.5f, 1.5f});
        table.setSpacingBefore(10);
         
        writeTableHeader(table);
        writeTableData(table);
         
        document.add(table);
         
        document.close();
         
    }
}