import com.itextpdf.text.pdf.PdfObject;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import java.io.*;
import java.time.LocalDate;

public class StudentReportGenerator {
    String templateDir;
    String exportDir;

    public PdfObject generateReport() throws IOException {
        StudentReportInput studentReportInput = StudentReportDataAssembler.assemble();
        //File export = new File(this.exportDir,  "result.xls");
       // InputStream input=
        byte[] reportData = null;
        try {
            JRMapArrayDataSource dataSource = new JRMapArrayDataSource(new Object[]{studentReportInput.getDataSources()});

            JasperPrint jasperPrint = JasperFillManager.fillReport(TemplateCompiler.studentReportTemplate,
                    studentReportInput.getParameters(), dataSource);

            reportData = JasperExportManager.exportReportToPdf(jasperPrint);
            /*JRMapArrayDataSource dataSource = new JRMapArrayDataSource(new Object[]{studentReportInput.getDataSources()});
            JasperDesign design = JRXmlLoader.load(new FileInputStream(new File(this.templateDir, "studentReport.xml")));
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                    studentReportInput.getParameters(), dataSource);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            JRXlsExporter exporterXLS = new JRXlsExporter();
           OutputStream outputStream= new FileOutputStream(export);
            jasperPrint.setProperty("net.sf.jasperreports.export.xls.detect.cell.type", "true");
            jasperPrint.setProperty("net.sf.jasperreports.export.xls.page.scale", "80");
            exporterXLS.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporterXLS.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
            exporterXLS.exportReport();
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.flush();
            outputStream.close();

            reportData = JasperExportManager.exportReportToPdf(jasperPrint);*/
        } catch (JRException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JRXlsExporter exporterXLS = new JRXlsExporter();
        .setProperty("net.sf.jasperreports.export.xls.detect.cell.type", "true");
        print.setProperty("net.sf.jasperreports.export.xls.page.scale", "80");
        exporterXLS.setExporterInput(new SimpleExporterInput(print));
        exporterXLS.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
        exporterXLS.exportReport();
        outputStream.write(byteArrayOutputStream.toByteArray());
        outputStream.flush();
        outputStream.close();*/
        PdfObject pdfObject = new PdfObject.PdfObjectBuilder()
                .creationDate(LocalDate.now())
                .pdfContent(reportData)
                .fileName("studentReport")
                .build();

        return pdfObject;
    }
}
