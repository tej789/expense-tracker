package com.expensetracker.api.service;

import com.expensetracker.api.model.User;
import com.expensetracker.api.service.impl.EmailOtpObserver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OtpNotificationSubject {

    private final List<OtpObserver> observers;

    public OtpNotificationSubject(List<OtpObserver> observers) {
        this.observers = observers;
    }

    public void notifyObservers(User user, String otp) {

        for (OtpObserver observer : observers) {
//            observer.sendOtp(user, otp);

            if (observer instanceof EmailOtpObserver) {
                observer.sendOtp(user, otp);
            }
        }
    }
}
