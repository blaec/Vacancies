package com.github.vacancies;

import com.github.vacancies.model.*;
import com.github.vacancies.model.strategies.AJStrategy;
import com.github.vacancies.view.HtmlView;

public class Aggregator {
    public static void main(String[] args) {
        HtmlView view = new HtmlView();
//        Provider providerHH = new Provider(new HHStrategy());
//        Provider providerMine = new Provider(new MoikrugStrategy());
        Provider providerAllJobs = new Provider(new AJStrategy());
        Model model = new Model(view, providerAllJobs);
        Controller controller = new Controller(model);

        view.setController(controller);
        view.userCitySelectEmulationMethod();
    }
}
