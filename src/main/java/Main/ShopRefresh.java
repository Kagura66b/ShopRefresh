package Main;

import GoogleHandlers.SheetInformationBuffer;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShopRefresh {
    ItemDataTable dataTable;
    JDA jda;

    public ShopRefresh(JDA importJDA, ItemDataTable importTable){
        jda = importJDA;
        dataTable = importTable;
    }

    public void refreshShop(JDA jda){
        List<Item> uncommons = new ArrayList<Item>();
        List<Item> rares = new ArrayList<Item>();
        Item veryRare = null;
        Random rand = new Random();
        int size = dataTable.getSize();


        while(veryRare == null || rares.size()<3 || uncommons.size()<5){
            Item check = dataTable.getItemByIndex(rand.nextInt(size));
            if (check.isCraftable() && check.isLootable() && !check.isConsumable()) {
                if (check.getRarity().contains("Very Rare") && veryRare == null) {
                    veryRare = check;
                } else if (check.getRarity().contains("Rare") && rares.size() < 3) {
                    rares.add(check);
                } else if (check.getRarity().contains("Uncommon") && uncommons.size() < 5) {
                    uncommons.add(check);
                }
            }
        }

        try {
            SheetInformationBuffer.deployNewList(uncommons, rares, veryRare);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        StringBuilder builder = new StringBuilder();
        builder.append("1: " + uncommons.get(0).name + " for " + uncommons.get(0).getPrice() + " gold");
        builder.append("\n");
        builder.append("2: " + uncommons.get(1).name + " for " + uncommons.get(1).getPrice() + " gold");
        builder.append("\n");
        builder.append("3: " + uncommons.get(2).name + " for " + uncommons.get(2).getPrice() + " gold");
        builder.append("\n");
        builder.append("4: " + uncommons.get(3).name + " for " + uncommons.get(3).getPrice() + " gold");
        builder.append("\n");
        builder.append("5: " + uncommons.get(4).name + " for " + uncommons.get(4).getPrice() + " gold");
        builder.append("\n");
        builder.append("6: " + rares.get(0).name + " for " + rares.get(0).getPrice() + " gold");
        builder.append("\n");
        builder.append("7: " + rares.get(1).name + " for " + rares.get(1).getPrice() + " gold");
        builder.append("\n");
        builder.append("8: " + rares.get(2).name + " for " + rares.get(2).getPrice() + " gold");
        builder.append("\n");
        builder.append("9: " + veryRare.name + " for " + veryRare.getPrice() + " gold");

        List<Message> messages = jda.getTextChannelById("699670958344110200").getHistory().getRetrievedHistory();

        jda.getTextChannelById("699670958344110200").editMessageById("699671498763272373", builder.toString()).queue();
    }
}
