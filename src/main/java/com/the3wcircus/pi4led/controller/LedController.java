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
        return "--- SYSTEM ONLINE ---";
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
        GpioFactory.getInstance().shutdown();

        if (pin1!=null) {
            pin1.pulse(1000,true);
            pin1.low();
        }
        if (pin2!=null) {
            pin2.pulse(1000,true);
            pin2.low();
        }

        return "--- SYSTEM OFFLINE ---";
    }
}
