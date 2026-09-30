// util/ExportadorReportes.java
package util;

import javax.swing.JTable;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileOutputStream;
import java.io.File;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportadorReportes {
    
    // Exportar tabla a Excel
    public static void exportarAExcel(JTable tabla, String nombreArchivo) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar reporte en Excel");
        fileChooser.setSelectedFile(new File(nombreArchivo + ".xlsx"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos Excel (*.xlsx)", "xlsx");
        fileChooser.setFileFilter(filter);
        
        int resultado = fileChooser.showSaveDialog(null);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Reporte");
                
                // Crear estilo para encabezados
                CellStyle headerStyle = workbook.createCellStyle();
                Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerStyle.setFont(headerFont);
                headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                
                // Crear fila de encabezados
                Row headerRow = sheet.createRow(0);
                for (int col = 0; col < tabla.getColumnCount(); col++) {
                    Cell cell = headerRow.createCell(col);
                    cell.setCellValue(tabla.getColumnName(col));
                    cell.setCellStyle(headerStyle);
                }
                
                // Llenar datos
                for (int row = 0; row < tabla.getRowCount(); row++) {
                    Row dataRow = sheet.createRow(row + 1);
                    for (int col = 0; col < tabla.getColumnCount(); col++) {
                        Cell cell = dataRow.createCell(col);
                        Object value = tabla.getValueAt(row, col);
                        if (value != null) {
                            cell.setCellValue(value.toString());
                        }
                    }
                }
                
                // Ajustar ancho de columnas
                for (int col = 0; col < tabla.getColumnCount(); col++) {
                    sheet.autoSizeColumn(col);
                }
                
                // Guardar archivo
                try (FileOutputStream fileOut = new FileOutputStream(archivo)) {
                    workbook.write(fileOut);
                }
                
                JOptionPane.showMessageDialog(null,
                    "Reporte exportado exitosamente a:\n" + archivo.getAbsolutePath(),
                    "Exportación exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                    "Error al exportar a Excel: " + e.getMessage(),
                    "Error de exportación",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Método para formatear moneda
    public static String formatearMoneda(double monto) {
        return String.format("S/ %.2f", monto);
    }
    
    // Método para formatear porcentaje
    public static String formatearPorcentaje(double porcentaje) {
        return String.format("%.2f%%", porcentaje);
    }
}