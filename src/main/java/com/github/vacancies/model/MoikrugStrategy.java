package com.github.vacancies.model;

import com.github.vacancies.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MoikrugStrategy implements Strategy {
    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?q=java+%s&page=%d";
    //

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> list = new ArrayList<>();
        int page = 0;

        while (true) {
            try {
                Document doc = getDocument(searchString, page);
                Elements elements = doc.getElementsByClass("job");
                if (!elements.isEmpty()) {
                    for (Element element : elements) {
                        Vacancy vacancy = new Vacancy();
                        vacancy.setTitle(element.getElementsByAttributeValue("class", "title").text());
                        vacancy.setCity(element.getElementsByAttributeValue("class", "location").text());
                        vacancy.setCompanyName(element.getElementsByAttributeValue("class", "company_name").text());
                        vacancy.setSalary(element.getElementsByAttributeValue("class", "salary").text());
                        vacancy.setUrl("https://moikrug.ru" + element.select("a[class=job_icon]").attr("href"));
                        vacancy.setSiteName(URL_FORMAT);
                        list.add(vacancy);
                    }
                } else {
                    break;
                }
                System.out.println(page + " : " + elements.isEmpty() + " : " + list.size() + " : " + String.format(URL_FORMAT, searchString, page));
            } catch (IOException e) {
                e.printStackTrace();
            }
            page++;
        }

        return list;
    }

    protected Document getDocument(String searchString, int page) throws IOException {
        String url = String.format(URL_FORMAT, searchString, page);

        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36")
                .referrer("https://moikrug.ru/vacancies?page=2&q=java+Dnepropetrovsk")
                .get();
    }

}
