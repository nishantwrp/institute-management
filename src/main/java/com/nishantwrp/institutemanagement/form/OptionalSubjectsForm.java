package com.nishantwrp.institutemanagement.form;

import com.nishantwrp.institutemanagement.model.Subject;

import java.util.ArrayList;
import java.util.List;

class OptionalSubjectSelector {
    private Subject subject;
    private Boolean selected;

    public Boolean getSelected() {
        return selected;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}

public class OptionalSubjectsForm {
    private List<OptionalSubjectSelector> optionalSubjects;

    public List<OptionalSubjectSelector> getOptionalSubjects() {
        return optionalSubjects;
    }

    public void setOptionalSubjects(List<OptionalSubjectSelector> optionalSubjects) {
        this.optionalSubjects = optionalSubjects;
    }

    public List<Subject> getSelectedSubjects() {
        List<Subject> subjectList = new ArrayList<>();

        if (optionalSubjects == null) {
            return subjectList;
        }

        for (int i = 0; i < optionalSubjects.size(); i++) {
            OptionalSubjectSelector optionalSubjectSelector = optionalSubjects.get(i);
            if (optionalSubjectSelector.getSelected()) {
                subjectList.add(optionalSubjectSelector.getSubject());
            }
        }

        return subjectList;
    }

    public OptionalSubjectsForm buildForm(List<Subject> optionalSubjects) {
        List<OptionalSubjectSelector> optionalSubjectSelectorList = new ArrayList<>();

        for (int i = 0; i < optionalSubjects.size(); i++) {
            OptionalSubjectSelector optionalSubjectSelector = new OptionalSubjectSelector();
            optionalSubjectSelector.setSubject(optionalSubjects.get(i));
            optionalSubjectSelectorList.add(optionalSubjectSelector);
        }

        OptionalSubjectsForm optionalSubjectsForm = new OptionalSubjectsForm();
        optionalSubjectsForm.setOptionalSubjects(optionalSubjectSelectorList);
        return optionalSubjectsForm;
    }
}
