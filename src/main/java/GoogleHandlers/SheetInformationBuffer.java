package GoogleHandlers;

import Main.Item;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
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

    public static final String shopID = "1U4ilMqpE7rINHN3P5EEUmXr_f0hZY-geWLuPReTRWNg";

    public static void deployNewList(List<Item> uncommons, List<Item> rares, Item veryRare) throws GeneralSecurityException, IOException {
        sheets = SheetsBuilder.getSheets();
        String rangeDeploy = "HenryCurrent";
        String rangeArchive = "HenryLog";

        Sheets.Spreadsheets.Values.Get requestCurrent =
                sheets.spreadsheets().values().get(shopID, rangeDeploy);
        List<List<Object>> toArchive = requestCurrent.execute().getValues();
        ValueRange body = new ValueRange().setValues(toArchive);
        sheets.spreadsheets().values()
                .append(ID, rangeArchive, body)
                .setValueInputOption("RAW")
                .setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true)
                .execute();

        List<List<Object>> toStore = new ArrayList<>();
        for(Item entry:uncommons){
            List<Object> row = new ArrayList<>();
            row.add(entry.getName());
            row.add(entry.getRarity());
            row.add(String.valueOf(entry.getPrice()));
            toStore.add(row);
        }
        for(Item entry:rares){
            List<Object> row = new ArrayList<>();
            row.add(entry.getName());
            row.add(entry.getRarity());
            row.add(String.valueOf(entry.getPrice()));
            toStore.add(row);
        }
        List<Object> row = new ArrayList<>();
        row.add(veryRare.getName());
        row.add(veryRare.getRarity());
        row.add(String.valueOf(veryRare.getPrice()));
        toStore.add(row);

        List<Object> timeStamp = new ArrayList<>();
        timeStamp.add(LocalDate.now().plus(3, ChronoUnit.DAYS).toString());
        toStore.add(timeStamp);

        body = new ValueRange().setValues(toStore);

        sheets.spreadsheets().values()
                .update(shopID, "HenryCurrent", body)
                .setValueInputOption("RAW")
                .setIncludeValuesInResponse(true)
                .execute();
    }
}
