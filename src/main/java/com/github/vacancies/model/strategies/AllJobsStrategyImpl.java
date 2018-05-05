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

import static com.github.vacancies.util.JsoupUtil.getDocument;

public class AllJobsStrategyImpl implements Strategy {
    private static final String URL_FORMAT = "https://www.alljobs.co.il/SearchResultsGuest.aspx?page=%d&position=1153&type=4&city=&region=";

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> list = new ArrayList<>();
        int page = 0;

        while (true) {
            try {
                Document doc = getDocument(URL_FORMAT, page);
                Elements elements = doc.getElementsByClass("job-content-top");
                if (!elements.isEmpty()) {
                    for (Element element : elements) {
                        Vacancy vacancy = new Vacancy();
                        vacancy.setTitle(getElementText(element, "N", "title"));
                        vacancy.setCity(getElementText(element, "job-content-top-location", "href"));
                        if (vacancy.getCity().isEmpty())
                            vacancy.setCity(getElementText(element, "job-content-top-location-ltr", "href"));
                        vacancy.setCompanyName(getElementText(element, "T14", "href"));
                        vacancy.setSalary(element.getElementsByAttributeValue("class", "salary").text());
                        vacancy.setAdded(element.getElementsByAttributeValue("class", "job-content-top-date").text());
                        vacancy.setUrl("https://www.alljobs.co.il" + element.select("a[class=N]").select("a[title]").attr("href"));
                        vacancy.setSiteName(URL_FORMAT);
                        list.add(vacancy);
                    }
                } else {
                    break;
                }
                System.out.println(page + " : " + elements.isEmpty() + " : " + list.size() + " : " + String.format(URL_FORMAT, page));
                if (elements.size() < 15){
                    break;
                }
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
}
