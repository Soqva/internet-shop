package com.s0qva.application.service;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

import static com.itextpdf.text.BaseColor.BLACK;
import static com.itextpdf.text.FontFactory.TIMES_ROMAN;

@Service
@RequiredArgsConstructor
public class PdfService {

    @SneakyThrows
    public void createPdf(String pdfFileName, Object data) {
        var document = new Document();
        var font = FontFactory.getFont(TIMES_ROMAN, 10, BLACK);

        PdfWriter.getInstance(document, new FileOutputStream(pdfFileName));

        document.open();
        document.add(new Chunk(data.toString(), font));
        document.close();
    }
}
