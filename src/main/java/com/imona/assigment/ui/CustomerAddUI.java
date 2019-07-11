package com.imona.assigment.ui;


import com.imona.assigment.model.Customers;
import com.imona.assigment.repository.CustomersRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI(path = "/home")
@Theme("valo")
public class CustomerAddUI extends UI {
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

        /*NAME*/
        TextField txt_name = new TextField("Name:");
        txt_name.setValidationVisible(false);
        txt_name.setRequired(true);
        String nameErrorMessage = "Name is required";
        txt_name.setRequiredError(nameErrorMessage);
        formLayout.addComponent(txt_name);

        /*SURNAME*/
        TextField txt_surname = new TextField("Surname:");
        txt_surname.setValidationVisible(false);
        txt_surname.setRequired(true);
        String surnameErrorMessage = "Surname is required";
        txt_surname.setRequiredError(surnameErrorMessage);
        formLayout.addComponent(txt_surname);

        /*GENDER*/
        ComboBox cmb_gender = new ComboBox("Select your gender:");
        cmb_gender.addItems("Male", "Female", "Other");
        cmb_gender.setValue(null);
        cmb_gender.setNullSelectionAllowed(false);
        cmb_gender.setTextInputAllowed(false);
        cmb_gender.setNewItemsAllowed(false);
        cmb_gender.setValue(cmb_gender.getItemIds().iterator().next());

        cmb_gender.setRequired(true);
        String genderErrorMessage = "Gender is required";
        cmb_gender.setRequiredError(genderErrorMessage);
        formLayout.addComponent(cmb_gender);

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
        ComboBox cmb_birthCity = new ComboBox("Birth City:");
        cmb_birthCity.addItems("Male", "Female", "Other");
        cmb_birthCity.setValue(null);
        cmb_birthCity.setTextInputAllowed(false);
        cmb_birthCity.setNewItemsAllowed(false);
        cmb_birthCity.setRequired(true);
        String birthCityErrorMessage = "Birth city is required";
        cmb_birthCity.setRequiredError(birthCityErrorMessage);
        formLayout.addComponent(cmb_birthCity);

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
        fieldGroup.bind(txt_name, "name");
        fieldGroup.bind(txt_surname, "surname");
        fieldGroup.bind(cmb_gender, "gender");
        fieldGroup.bind(birthDate, "date");
        fieldGroup.bind(cmb_birthCity, "city");
        fieldGroup.bind(flag, "flag");

        /*Footer Layout*/
        /*CANCEL BUTTON*/
        Button cancelButton = new Button("Cancel");
        cancelButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                txt_name.setValue("");
                txt_surname.setValue("");
                birthDate.setValue(null);
                cmb_birthCity.setValue(null);
                cmb_gender.setValue(null);
                flag.setValue(false);
                channels.removeAllItems();
                channels.addItems("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune");

                txt_name.setValidationVisible(false);
                txt_surname.setValidationVisible(false);
                birthDate.setValidationVisible(false);
                cmb_birthCity.setValidationVisible(false);
                cmb_gender.setValidationVisible(false);
                flag.setValidationVisible(false);

            }
        });
        /*SAVE BUTTON*/
        Button saveButton = new Button("Save");
        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
               /* Boolean failed = false;
                errLabel = "";
                try {
                    txt_name.validate();
                } catch (Validator.InvalidValueException e) {
                    if (e.getMessage() != "") {
                        txt_name.setValidationVisible(true);
                        errLabel = e.getMessage();
                        failed = true;
                    }
                }
                try {
                    txt_surname.validate();
                } catch (Validator.InvalidValueException e) {
                    if (e.getMessage() != "") {
                        txt_surname.setValidationVisible(true);
                        errLabel += "<br/>" + e.getMessage();
                        failed = true;
                    }

                }
                try {
                    cmb_gender.validate();
                    if (cmb_gender.getValue().equals("")) {
                        cmb_gender.setValidationVisible(true);
                        errLabel += "<br/>" + "Gender is required";
                        failed = true;
                    }
                } catch (Validator.InvalidValueException e) {
                    if (e.getMessage() != "") {
                        cmb_gender.setValidationVisible(true);
                        errLabel += "<br/>" + e.getMessage();
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
                    cmb_birthCity.validate();
                    if (cmb_birthCity.getValue().equals("")) {
                        cmb_birthCity.setValidationVisible(true);
                        errLabel += "<br/>" + "Birth City is required";
                        failed = true;
                    }
                } catch (Validator.InvalidValueException e) {
                    if (e.getMessage() != "") {
                        cmb_birthCity.setValidationVisible(true);
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
                    Customers customer=new Customers();
                    customer.setName(txt_name.getValue());
                    customer.setSurname(txt_surname.getValue());
                    customer.setBirthCity(cmb_birthCity.getValue().toString());
                    customer.setBirthDate(birthDate.getValue());
                    if(cmb_gender.getValue().toString().toLowerCase().equals("male")){
                        customer.setGender(true);
                    }
                    else{
                        customer.setGender(false);
                    }
                    customer.setChannels(channels.getValue().toString());
                    customer.setActive(flag.getValue());
                    customersRepository.save(customer);


                }*/
                Notification.show("customer successfully added",
                        "",
                        Notification.Type.HUMANIZED_MESSAGE);
            }
        });

        footerLayout.addComponent(saveButton);
        footerLayout.addComponent(cancelButton);

    }
}
