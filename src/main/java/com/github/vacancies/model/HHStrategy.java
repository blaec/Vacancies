package com.github.vacancies.model;

import com.github.vacancies.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HHStrategy implements Strategy {
    private static final String URL_FORMAT = "http://hh.ua/search/vacancy?text=java+%s&page=%d";
    //

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        String elQuery = "[data-qa='vacancy-serp__vacancy']";
        String elTitle = "[data-qa='vacancy-serp__vacancy-title']";
        String elCity = "[data-qa='vacancy-serp__vacancy-address']";
        String elCoName = "[data-qa='vacancy-serp__vacancy-employer']";
        String elSalary= "[data-qa='vacancy-serp__vacancy-compensation']";
        List<Vacancy> list = new ArrayList<>();
        int page = 0;

        while (true) {
            try {
                Document doc = getDocument(searchString, page);
                Elements elements = doc.select(elQuery);
                if (!elements.isEmpty()) {
                    for (Element element : elements) {
                        Vacancy vacancy = new Vacancy();
                        vacancy.setTitle(element.select(elTitle).text());
                        vacancy.setCity(element.select(elCity).text());
                        vacancy.setCompanyName(element.select(elCoName).text());
                        vacancy.setSalary(element.select(elSalary).text());
                        vacancy.setUrl(element.select(elTitle).attr("href"));
                        vacancy.setSiteName("https://hh.ua");
                        list.add(vacancy);
                    }
                } else {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            page++;
        }

        return list;
    }

    protected Document getDocument(String serchString, int page) throws IOException {
        String url = String.format(URL_FORMAT, serchString, page);

        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36")
                .referrer("https://hh.ua/search/vacancy?text=java+%D0%BA%D0%B8%D0%B5%D0%B2")
                .get();
    }

}
