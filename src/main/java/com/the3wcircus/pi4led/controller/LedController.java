package com.the3wcircus.pi4led.controller;


import com.pi4j.io.gpio.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LedController {

    private static GpioPinDigitalOutput pin1;
    private static GpioPinDigitalOutput pin2;

    @RequestMapping("/")
    public String greeting() {
        return "Howdy!";
    }

    @RequestMapping("/light")
    public String light() {

        if (pin1 == null) {
            GpioController gpio = GpioFactory.getInstance();
            pin1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "Pin1", PinState.LOW);
            pin2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "Pin2", PinState.HIGH);
        }
        pin1.toggle();
        pin2.toggle();

        return "FLAME ON!!!";
    }

    @RequestMapping("/stop")
    public String shutdownGpio() {
        if (GpioFactory.getInstance().isShutdown()) {
            GpioFactory.getInstance().shutdown();
            return "--- SYSTEM OFFLINE ---";
        } else {

            return "--- SYSTEM UNAVAILABLE  ---";
        }
    }
}
