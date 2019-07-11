package com.imona.assigment;


import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;
import java.util.List;

@SpringUI
@Theme("valo")
public class CustomerInformationUI extends UI {
    private String errLabel = "";


    @Autowired
    private CustomersRepository customersRepository;



    @Override
    protected void init(VaadinRequest vaadinRequest) {

        final VerticalLayout mainLayout = new VerticalLayout();
        final FormLayout formLayout = new FormLayout();
        final VerticalLayout contentLayout = new VerticalLayout();
        final HorizontalLayout footerLayout = new HorizontalLayout();

        mainLayout.setSizeFull();//to ensure whole space is in use

        formLayout.setSizeFull();
        /**/
        List<Customers> customers = customersRepository.findAll();
        Label test=new Label();
        test.setValue(customers.get(0).getFirstName()+"-"+customers.get(0).getLastName());
        mainLayout.addComponent(test);
        /**/
        contentLayout.addComponent(formLayout);
        contentLayout.addComponent(footerLayout);
        mainLayout.addComponent(contentLayout);
        setContent(mainLayout);

        /*Content Layout*/
        PropertysetItem myfields = new PropertysetItem();
        myfields.addItemProperty("name", new ObjectProperty(""));
        myfields.addItemProperty("surname", new ObjectProperty(""));
        myfields.addItemProperty("gender", new ObjectProperty(""));
        myfields.addItemProperty("date", new ObjectProperty(""));
        myfields.addItemProperty("city", new ObjectProperty(""));
        myfields.addItemProperty("flag", new ObjectProperty(""));
        myfields.addItemProperty("channels", new ObjectProperty(""));

        Label result = new Label();

        /*NAME*/
        TextField nameText = new TextField("Name:");
        nameText.setValidationVisible(false);
        nameText.setRequired(true);
        String nameErrorMessage = "Name is required";
        nameText.setRequiredError(nameErrorMessage);
        formLayout.addComponent(nameText);

        /*SURNAME*/
        TextField surNameText = new TextField("Surname:");
        surNameText.setValidationVisible(false);
        surNameText.setRequired(true);
        String surnameErrorMessage = "Surname is required";
        surNameText.setRequiredError(surnameErrorMessage);
        formLayout.addComponent(surNameText);

        /*GENDER*/
        ComboBox gender = new ComboBox("Select your gender:");
        gender.addItems("Male", "Female", "Other");
        gender.setValue(null);
        gender.setNullSelectionAllowed(false);
        gender.setTextInputAllowed(false);
        gender.setNewItemsAllowed(false);
        gender.setValue(gender.getItemIds().iterator().next());

        gender.setRequired(true);
        String genderErrorMessage = "Gender is required";
        gender.setRequiredError(genderErrorMessage);
        formLayout.addComponent(gender);

        /*BIRTHDATE*/
        // Create a DateField with the default style
        DateField birthDate = new DateField("Birth Date:");
        birthDate.setDateFormat("dd/MM/yyyy");
        birthDate.setValidationVisible(false);
        birthDate.setRequired(true);
        String dateErrorMessage = "Birth date is required";
        birthDate.setRequiredError(dateErrorMessage);
        formLayout.addComponent(birthDate);

        /*BIRTH CITY*/
        ComboBox birthCity = new ComboBox("Birth City:");
        birthCity.addItems("Male", "Female", "Other");
        birthCity.setValue(null);
        gender.setNullSelectionAllowed(false);
        birthCity.setTextInputAllowed(false);
        birthCity.setNewItemsAllowed(false);
        birthCity.setRequired(true);
        String birthCityErrorMessage = "Birth city is required";
        birthCity.setRequiredError(birthCityErrorMessage);
        formLayout.addComponent(birthCity);

        /*FLAG*/
        CheckBox flag = new CheckBox("Active/Passive");
        formLayout.addComponent(flag);

        /* CHANNELS*/
        TwinColSelect channels = new TwinColSelect("Select Channels");
        channels.addItems("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune");
        channels.setLeftColumnCaption("Available Channels");
        channels.setRightColumnCaption("Your Channels");
        channels.setRows(channels.size());
        formLayout.addComponent(channels);

        FieldGroup fieldGroup = new FieldGroup(myfields);
        fieldGroup.bind(nameText, "name");
        fieldGroup.bind(surNameText, "surname");
        fieldGroup.bind(gender, "gender");
        fieldGroup.bind(birthDate, "date");
        fieldGroup.bind(birthCity, "city");
        fieldGroup.bind(flag, "flag");

        Button button = new Button("Submit");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Boolean failed = false;
                errLabel = "";
                try {
                    nameText.validate();
                } catch (Validator.InvalidValueException e) {
                    if (e.getMessage() != "") {
                        nameText.setValidationVisible(true);
                        errLabel = e.getMessage();
                        failed = true;
                    }
                }
                try {
                    surNameText.validate();
                } catch (Validator.InvalidValueException e) {
                    if (e.getMessage() != "") {
                        surNameText.setValidationVisible(true);
                        errLabel += "<br/>" + e.getMessage();
                        failed = true;
                    }

                }
                try {
                    gender.validate();
                } catch (Validator.InvalidValueException e) {
                    if (e.getMessage() != "" || gender.getValue().equals("")) {
                        gender.setValidationVisible(true);
                        errLabel += "<br/>" + "Gender is required";
                        failed = true;
                    }

                }
                try {
                    birthDate.validate();
                } catch (Validator.InvalidValueException e) {
                    if (e.getMessage() != "") {
                        birthDate.setValidationVisible(true);
                        errLabel += "<br/>" + e.getMessage();
                        failed = true;
                    }

                }
                try {
                    birthCity.validate();
                } catch (Validator.InvalidValueException e) {
                    if (e.getMessage() != "") {
                        birthCity.setValidationVisible(true);
                        errLabel += "<br/>" + e.getMessage();
                        failed = true;
                    }

                }
                if (failed) {
                    new Notification("",
                            errLabel,
                            Notification.Type.ERROR_MESSAGE, true)
                            .show(Page.getCurrent());
                } else {
                    result.setValue(nameText.getValue() + "-" +
                                    surNameText.getValue() + "-" +
                                    gender.getValue() + "-" +
                                    birthDate.getValue() + "-" +
                                    birthCity.getValue() + "-" +
                                    flag.getValue() + "-"
                            //  channels.getValue()
                    );
                }
            }
        });
        formLayout.addComponent(button);
        formLayout.addComponent(result);

        /*Footer Layout*/

        /*CANCEL BUTTON*/
        Button cancelButton = new Button("Cancel");
        cancelButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                nameText.setValue("");
                surNameText.setValue("");
                birthDate.setValue(null);
                birthCity.setValue(null);
                gender.setValue(null);
                flag.setValue(false);
                channels.removeAllItems();
                channels.addItems("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune");

                nameText.setValidationVisible(false);
                surNameText.setValidationVisible(false);
                birthDate.setValidationVisible(false);
                birthCity.setValidationVisible(false);
                gender.setValidationVisible(false);
                flag.setValidationVisible(false);

            }
        });
        /*SAVE BUTTON*/
        Button saveButton = new Button("Save");


        footerLayout.addComponent(saveButton);
        footerLayout.addComponent(cancelButton);

    }
    @WebServlet(urlPatterns = "/home", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = CustomerInformationUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
