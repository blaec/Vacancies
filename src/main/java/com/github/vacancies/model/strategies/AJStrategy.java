package com.github.vacancies.model.strategies;

import com.github.vacancies.model.Strategy;
import com.github.vacancies.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AJStrategy implements Strategy {
    private static final String URL_FORMAT = "https://www.alljobs.co.il/SearchResultsGuest.aspx?page=%d&position=1153&type=4&city=&region=";

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> list = new ArrayList<>();
        int page = 0;

        while (true) {
            try {
                Document doc = getDocument(searchString, page);
                Elements elements = doc.getElementsByClass("job-content-top");
                if (!elements.isEmpty()) {
                    for (Element element : elements) {
                        Vacancy vacancy = new Vacancy();
                        vacancy.setTitle(getElementText(element, "N", "title"));
                        vacancy.setCity(getElementText(element, "job-content-top-location", "href"));
                        if (vacancy.getCity().isEmpty())
                            vacancy.setCity(getElementText(element, "job-content-top-location-ltr", "href"));
                        vacancy.setCompanyName(getElementText(element, "T14", "href"));
                        vacancy.setSalary(element.getElementsByAttributeValue("class", "job-content-top-date").text());
                        vacancy.setUrl("https://www.alljobs.co.il" + element.select("a[class=N]").select("a[title]").attr("href"));
                        vacancy.setSiteName(URL_FORMAT);
                        list.add(vacancy);
                    }
                } else {
                    break;
                }
                System.out.println(page + " : " + elements.isEmpty() + " : " + list.size() + " : " + String.format(URL_FORMAT, page));
                if (elements.size() < 15) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            page++;
        }

        return list;
    }

    private String getElementText(Element element, String className, String aElement) {
        return element.getElementsByAttributeValue("class", className).select("a[" + aElement + "]").text();
    }

    protected Document getDocument(String searchString, int page) throws IOException {
        String url = String.format(URL_FORMAT, page);

        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36")
                .referrer("https://www.alljobs.co.il/SearchResultsGuest.aspx?page=1&position=460&type=&city=&region=")
                .get();
    }

}
