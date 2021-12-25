package com.recruitme.enums.mail;

public enum MailTemplate {

    VERIFICATION_EMAIL_TEMPLATE("VerificationEmail"),
    DRIVE_NOTIFICATION_EMAIL_TEMPLATE("DriveNotificationEmail"),
    RESET_PASSWORD_EMAIL_TEMPLATE("ResetPassword"),
    WELCOME_EMAIL_TEMPLATE("WelcomeEmail"),
    MAIL_FROM("divyjain62000@gmail.com");


    private String mailTemplate;
    MailTemplate(String mailTemplate) { this.mailTemplate=mailTemplate; }
    public String getMailTemplate() { return this.mailTemplate; }
}
