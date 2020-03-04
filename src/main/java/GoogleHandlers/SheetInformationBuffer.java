package GoogleHandlers;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class SheetInformationBuffer {
    public static final String ID = "1U4ilMqpE7rINHN3P5EEUmXr_f0hZY-geWLuPReTRWNg";
    public static final String itemsID = "12Y06S_7WHCj6gYXVZLcph0PSpe2sdiudErWamr7z0Ck";
    private static List<String[]> itemArray;
    private static Sheets sheets;

    public static List<List<String>> retrieveItemSheetData() throws IOException, GeneralSecurityException {
        sheets = SheetsBuilder.getSheets();
        String range = "Bot Friendly";
        Sheets.Spreadsheets.Values.Get request =
                sheets.spreadsheets().values().get(itemsID, range);

        List<List<Object>> response = request.execute().getValues();
        List<List<String>> sheetData = new ArrayList<>(new ArrayList<>());
        for(List<Object> row:response){
            List<String> cellText = new ArrayList<>();
            for(Object cell:row){
                cellText.add(cell.toString());
            }
            sheetData.add(cellText);
        }
        return sheetData;
    }
}
