package com.github.vacancies.view;

import com.github.vacancies.Controller;
import com.github.vacancies.vo.Vacancy;

import java.util.List;

public interface View {
    void update(List<Vacancy> vacancies);
    void setController(Controller controller);
}
