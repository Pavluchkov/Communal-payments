package by.javafx.communalPayments.representationObjects;

public class FormPayments extends MyObjects {
    private int id;
    private String form;

    public FormPayments(int id, String form) {
        this.id = id;
        this.form = form;
    }

    public FormPayments() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }
}
