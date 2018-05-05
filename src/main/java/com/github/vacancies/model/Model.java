package com.github.vacancies.model;

import com.github.vacancies.view.View;
import com.github.vacancies.vo.Vacancy;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private View view;
    private Provider[] providers;

    public Model(View view, Provider... providers) throws IllegalArgumentException{
        if (view == null || providers == null || providers.length == 0) {
            throw new IllegalArgumentException();
        }
        this.view = view;
        this.providers = providers;
    }

    public void selectCity(String city) {
        List<Vacancy> list = new ArrayList<>();

        for (Provider provider : providers) {
            list.addAll(provider.getJavaVacancies(city));
        }
        view.update(list);
    }
}
