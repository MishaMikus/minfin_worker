package ui_automation.upg;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import orm.entity.logan_park.card.FillingCard;
import orm.entity.logan_park.card.FillingCardDAO;
import orm.entity.logan_park.vehicle.Vehicle;
import orm.entity.logan_park.vehicle.VehicleDAO;
import orm.entity.logan_park.week_range.WeekRangeDAO;
import orm.entity.logan_park.filling.FillingRecord;
import util.NumberHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.codeborne.selenide.Selenide.*;

public class UpgBo extends BaseUpgBO {
    private final static Logger LOGGER = Logger.getLogger(BaseUpgBO.class);
    //17.10.2019 19:09:54
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public List<FillingRecord> getAllLatestFillings() {
        goToPath("/ua/owner/account/list");

        List<FillingRecord> res = new ArrayList<>();
        Map<String, String> hrefCardMap = new HashMap<>();
        $$(By.xpath("//a[contains(@href,'cid')]")).forEach(e -> {
            hrefCardMap.put(e.getText(), e.getAttribute("href"));
        });
        hrefCardMap.forEach((key, value) -> {
            LOGGER.info("Read transactions : " + key + " -> " + value);
            String cid = value.split("cid=")[1];
            goToPath("/ua/owner/account/list?cid=" + cid);
            res.addAll(parseTransactions());
        });
        res.forEach(LOGGER::info);
        close();
        return res;
    }

    private List<FillingRecord> parseTransactions() {
        List<FillingRecord> res = new ArrayList<>();
        $$(By.xpath("//*[@class='ui-corner-bottom ui-content']")).forEach(e -> {
            res.add(parseFillingPopup(e.innerHtml()));
        });
        return res;
    }

    private FillingRecord parseFillingPopup(String content) {
        FillingRecord fillingRecord = new FillingRecord();
        String idString = bound(content, "№ ", "</h3>");
        String dateString = bound(content, "<p><b>ДАТА ЧАС</b>: ", "</p>");
        String cardNumberString = bound(content, "<p><b>КАРТА</b>: ", "</p>");
        String itemAmountString = bound(content, "<p><b>КІЛЬКІСТЬ</b>: ", "</p>");
        String priceString = bound(content, "<p><b>ЦІНА</b>: ", "</p>");
        String stationNameString = bound(content, "<p><b>АЗС</b>: ", "</p>");
        String addressString = bound(content, "<p><b>Адреса</b>: ", "</p>");
        fillingRecord.setDate(parseDate(dateString));
        fillingRecord.setWeek_id(WeekRangeDAO.getInstance().findOrCreateWeek(fillingRecord.getDate()).getId());
        fillingRecord.setShop(stationNameString);
        fillingRecord.setAddress(addressString);
        fillingRecord.setId(idString);
        fillingRecord.setCard(cardNumberString.trim().replaceAll(" ", ""));
        fillingRecord.setItemAmount(Double.parseDouble(itemAmountString));
        fillingRecord.setPrice(Double.parseDouble(priceString));

        fillingRecord.setAmount(NumberHelper.round(fillingRecord.getItemAmount() * fillingRecord.getPrice()));
        fillingRecord.setCar(findOutCarIdentity(fillingRecord.getCard()));
        fillingRecord.setStation("upg");
        return fillingRecord;
    }

    private List<FillingCard> fillingCardList = FillingCardDAO.getInstance().findAll();
    private List<Vehicle> vehicleList = VehicleDAO.getInstance().findAll();

    private String findOutCarIdentity(String card) {

        FillingCard fillingCard = fillingCardList.stream().filter(f -> f.getId().equals(card)).findAny().orElse(null);
        if (fillingCard == null) {
            LOGGER.warn("find new card : " + card + " UPG");
            fillingCard.setId(card);
            fillingCard.setStation("upg");
            FillingCardDAO.getInstance().save(fillingCard);
        }

        Vehicle vehicle = vehicleList.stream().filter(v -> v.getId().equals(fillingCard.getVehicle_id())).findAny().orElse(null);
        if (vehicle == null) {
            LOGGER.warn("unassigned card detected");
            return "UNKNOWN_CAR";
        } else {
            return vehicle.getName() + "_" + vehicle.getPlate();
        }
    }

    private Date parseDate(String dateString) {
        try {
            return SDF.parse(dateString);
        } catch (ParseException e) {
            LOGGER.warn("date parsing failure : \n" + e.getMessage());
        }
        return null;
    }

    private String bound(String content, String from, String to) {
        return content.split(from)[1].split(to)[0];
    }
}
