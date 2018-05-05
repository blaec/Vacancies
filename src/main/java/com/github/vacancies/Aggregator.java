package com.github.vacancies;

import com.github.vacancies.model.Model;
import com.github.vacancies.model.Provider;
import com.github.vacancies.model.strategies.AllJobsStrategyImpl;
import com.github.vacancies.model.strategies.JobMasterStrategyImpl;
import com.github.vacancies.view.HtmlView;

public class Aggregator {
    public static void main(String[] args) {
        HtmlView view = new HtmlView();
        Provider providerAllJobs = new Provider(new AllJobsStrategyImpl());
        Provider providerJobMaster = new Provider(new JobMasterStrategyImpl());
        Model model = new Model(view, providerJobMaster, providerAllJobs);
        Controller controller = new Controller(model);

        view.setController(controller);
        view.userCitySelectEmulationMethod();
    }
}
