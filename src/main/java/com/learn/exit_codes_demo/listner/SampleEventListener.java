package com.learn.exit_codes_demo.listner;

import org.springframework.boot.ExitCodeEvent;
import org.springframework.context.event.EventListener;

public class SampleEventListener {

    @EventListener
    public void exitEvent(ExitCodeEvent event){ // once we run this application, we can see that this log line has printed
        System.out.println("Application exit with event code: " + event.getExitCode());
    }
}
