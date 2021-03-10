package com.cg.banking.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.banking.entity.AccTransaction;
import com.cg.banking.exceptions.AccountException;
import com.cg.banking.exceptions.TransactionException;
import com.cg.banking.service.TransactionService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
public class PDFController {
	
	@Autowired
	private TransactionService ser;
	
	
	
	@CrossOrigin
	@GetMapping("viewpdf/{accountId}")
	public void downloadPdf( @PathVariable("accountId") String accId, HttpServletResponse resp) throws TransactionException, AccountException {
		List<AccTransaction> txnList = ser.getTransactions(accId);
	Document document = new Document();
    try
    {
    	//resp.setHeader("Content-Disposition", "attachement");
        PdfWriter writer = PdfWriter.getInstance(document, resp.getOutputStream());
        document.open();
        document.add(new Paragraph("List Of Transactions."));
        
        PdfPTable table = new PdfPTable(5); // 3 columns.
        table.setWidthPercentage(100); //Width 100%
        table.setSpacingBefore(10f); //Space before table
        table.setSpacingAfter(10f); //Space after table
 
        //Set Column widths
       // float[] columnWidths = {1f, 1f, 1f, 1f};
       // table.setWidths(columnWidths);
 
        PdfPCell cell1 = new PdfPCell(new Paragraph("Transaction ID"));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Transaction acmount"));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Transaction date"));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Description"));
        PdfPCell cell5 = new PdfPCell(new Paragraph("Transaction type"));
        
        
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        for(AccTransaction txn: txnList) {
        	cell1 = new PdfPCell(new Paragraph(txn.getTransaccountId()+""));
        	cell2 = new PdfPCell(new Paragraph(txn.getTransAmount()+""));
        	cell3 = new PdfPCell(new Paragraph(txn.getTransDate().toString()+""));
        	cell4 = new PdfPCell(new Paragraph(txn.getTransDescription()+""));
        	cell5 = new PdfPCell(new Paragraph(txn.getTransType()+""));
        	table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
        }
        document.add(table);
 
        document.close();
        writer.close();
    } catch (Exception e)
    {
        e.printStackTrace();
    }
	}

}
