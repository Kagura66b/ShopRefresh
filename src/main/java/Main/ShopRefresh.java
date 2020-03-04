package Main;

import GoogleHandlers.SheetInformationBuffer;
import net.dv8tion.jda.api.JDA;

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

    public void refreshShop(){
        List<Item> uncommons = new ArrayList<Item>();
        List<Item> rares = new ArrayList<Item>();
        Item veryRare = null;
        Random rand = new Random();
        int size = dataTable.getSize();


        while(veryRare == null || rares.size()<3 || uncommons.size()<5){
            Item check = dataTable.getItemByIndex(rand.nextInt(size));
            if(check.isCraftable()&&check.isLootable()&&!check.isConsumable()){
                if(check.getRarity().contains("Very Rare")&&veryRare==null){
                    veryRare=check;
                }else if(check.getRarity().contains("Rare")&&rares.size()<3){
                    rares.add(check);
                }else if(check.getRarity().contains("Uncommon")&&uncommons.size()<5){
                    uncommons.add(check);
                }
            }
        }

        System.out.println(uncommons.get(0).name);
        System.out.println(uncommons.get(1).name);
        System.out.println(uncommons.get(2).name);
        System.out.println(uncommons.get(3).name);
        System.out.println(uncommons.get(4).name);
        System.out.println(rares.get(0).name);
        System.out.println(rares.get(1).name);
        System.out.println(rares.get(2).name);
        System.out.println(veryRare.name);

    }
}
