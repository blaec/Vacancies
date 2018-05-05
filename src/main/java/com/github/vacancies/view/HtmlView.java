package com.github.vacancies.view;

import com.github.vacancies.Controller;
import com.github.vacancies.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlView implements View {
    private Controller controller;
    private static final String filePath = "C:\\Users\\blaec\\Documents\\javaProjects\\Vacancies\\src\\main\\java\\com\\github\\vacancies\\view\\vacancies.html";
//    private final String filePath = "./src/" + this.getClass().getPackage().getName().replaceAll("\\.", "/") + "/vacancies.html";

    @Override
    public void update(List<Vacancy> vacancies) {
        try {
            updateFile(getUpdatedFileContent(vacancies));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getUpdatedFileContent(List<Vacancy> vacancies) {
        Document document;

        try {
            document = getDocument();

            Element templateOriginal = document.getElementsByClass("template").first();
            Element templateClone = templateOriginal.clone();
            templateClone.removeAttr("style");
            templateClone.removeClass("template");

            document.select("tr[class='vacancy']").remove().not("tr[class='vacancy template']");

            for (Vacancy vacancy : vacancies) {
                Element element = templateClone.clone();
                element.getElementsByClass("city").first().text(vacancy.getCity());
                element.getElementsByClass("companyName").first().text(vacancy.getCompanyName());
                element.getElementsByClass("salary").first().text(vacancy.getSalary());
                Element link = element.getElementsByTag("a").first();
                link.text(vacancy.getTitle());
                link.attr("href", vacancy.getUrl());

                templateOriginal.before(element.outerHtml());
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "Some exception occurred";
        }
        return document.html();
    }

    private void updateFile(String content) {
        try {
            FileWriter fileWriter = new FileWriter(new File(filePath));
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod() {
        controller.onCitySelect("add_city_name_here");
    }

    protected Document getDocument() throws IOException
    {
        return Jsoup.parse(new File(filePath), "UTF-8");
    }

}
