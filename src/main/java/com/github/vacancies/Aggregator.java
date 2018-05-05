package com.github.vacancies;

import com.github.vacancies.model.HHStrategy;
import com.github.vacancies.model.Model;
import com.github.vacancies.model.MoikrugStrategy;
import com.github.vacancies.model.Provider;
import com.github.vacancies.view.HtmlView;

public class Aggregator {
    public static void main(String[] args) {
        HtmlView view = new HtmlView();
        Provider providerHH = new Provider(new HHStrategy());
        Provider providerMine = new Provider(new MoikrugStrategy());
        Model model = new Model(view, providerHH, providerMine);
        Controller controller = new Controller(model);

        view.setController(controller);
        view.userCitySelectEmulationMethod();
    }
}
