package by.javafx.communalPayments.controllers;

import by.javafx.communalPayments.interfaces.IDatabase;
import by.javafx.communalPayments.objects.MySQLDatabase;

public abstract class Controller {
    protected IDatabase database = new MySQLDatabase();
}
