import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestPOI {
    public static void main(String[] args) {
        try {
            System.out.println("Intentando crear XSSFWorkbook...");
            XSSFWorkbook wb = new XSSFWorkbook();
            System.out.println("XSSFWorkbook creado exitosamente!");
            wb.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
