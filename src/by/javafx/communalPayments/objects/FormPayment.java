package by.javafx.communalPayments.objects;

public class FormPayment extends MyObjects{
    private int id;
    private String form;

    public FormPayment(int id, String form) {
        this.id = id;
        this.form = form;
    }

    public FormPayment() {
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
