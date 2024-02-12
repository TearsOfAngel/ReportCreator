package automaticReportGenerator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.*;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Logger.class.getName());
    private static final String USER_AGENT = ConfProperties.getProperty("user_agent");
    private static final String REFERRER = ConfProperties.getProperty("referrer");

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Map<String, String> dataFromUserSide = fetchData();
        printData(dataFromUserSide);
        long finish = (System.currentTimeMillis() - start) / 1000;
        LOGGER.info("Время выполнения: " + finish + " секунд");

    }

    private static Map<String, String> fetchData() {
        HashMap<String, String> numberToTaskStatus = new HashMap<>();
        numberToTaskStatus.put("1", "Создано: ");
        numberToTaskStatus.put("2", "Добавлено: ");
        numberToTaskStatus.put("3", "Обновлено: ");
        numberToTaskStatus.put("4", "Выполнено: ");
        numberToTaskStatus.put("5", "Просрочено: ");

        Map<String, String> dataFromUserSide = new LinkedHashMap<>();


        try {
            Connection.Response loginForm = Jsoup.connect("https://us.gblnet.net")
                    .userAgent(USER_AGENT)
                    .referrer(REFERRER)
                    .method(Connection.Method.GET)
                    .execute();

            LOGGER.info("Страница входа: " + loginForm.url());

            Connection.Response login = Jsoup.connect("https://us.gblnet.net/oper/index.php?")
                    .userAgent(USER_AGENT)
                    .referrer(REFERRER)
                    .data("username", ConfProperties.getProperty("login"))
                    .data("password", ConfProperties.getProperty("password"))
                    .data("action", "login")
                    .cookies(loginForm.cookies())
                    .method(Connection.Method.POST)
                    .execute();

            LOGGER.info("Страница после логина: " + login.url());

            for (int i = 1; i <= 5; i++) {
                Document doc = Jsoup.connect(ConfProperties.getProperty("page" + i + "_url"))
                        .userAgent(USER_AGENT)
                        .referrer(REFERRER)
                        .cookies(login.cookies())
                        .get();
                Element element = doc.select("div[style=div_space]").first();
                String tasksCounter = (element == null) ? "0" : element.text().replace("Результатов:", "").trim();
                dataFromUserSide.put(numberToTaskStatus.get(String.valueOf(i)), tasksCounter);
            }

        } catch (Exception e) {
            LOGGER.error("Произошла ошибка: " + Arrays.toString(e.getStackTrace()));
        }

        return dataFromUserSide;
    }

    private static void printData(Map<String, String> dataFromUserSide) {
        for (Map.Entry<String, String> entry : dataFromUserSide.entrySet()) {
            System.out.println(entry.getKey() + entry.getValue());
        }
    }
}
