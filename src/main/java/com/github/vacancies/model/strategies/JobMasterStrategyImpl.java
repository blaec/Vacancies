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

public class JobMasterStrategyImpl implements Strategy {
    private static final String URL_FORMAT = "https://www.jobmaster.co.il/code/check/search.asp?currPage=%d&q=תוכנה+java&l=";

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> list = new ArrayList<>();
        int page = 0;

        while (true) {
            try {
                Document doc = getDocument(URL_FORMAT, page);
                Elements elements = doc.getElementsByAttributeValueContaining("class", "CardStyle JobItem font14");
                if (!elements.isEmpty()) {
                    for (Element element : elements) {
                        Vacancy vacancy = new Vacancy();
                        vacancy.setTitle(getElementText(element, "CardHeader", null));
                        vacancy.setCity(getElementText(element, "jobLocation", null));
                        vacancy.setCompanyName(getElementText(element, "font14 ByTitle", null));
                        vacancy.setSalary(getElementText(element, "jobSalary", null));
                        vacancy.setAdded("");
                        vacancy.setUrl(String.format(URL_FORMAT, page));
                        vacancy.setSiteName(URL_FORMAT);
                        list.add(vacancy);
                    }
                } else {
                    break;
                }
                System.out.println(page + " : " + elements.isEmpty() + " : " + list.size() + " : " + String.format(URL_FORMAT, page));
                if (page > 20) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            page++;
        }

        return list;
    }

    private String getElementText(Element element, String className, String aElement) {
        if (aElement != null) {
            return element.getElementsByAttributeValue("class", className).select("a[" + aElement + "]").text();
        } else {
            return element.getElementsByAttributeValue("class", className).text();
        }
    }
}
