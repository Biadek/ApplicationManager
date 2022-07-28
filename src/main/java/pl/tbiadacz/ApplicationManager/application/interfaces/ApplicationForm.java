package pl.tbiadacz.ApplicationManager.application.interfaces;

import pl.tbiadacz.ApplicationManager.application.service.command.ApplicationAttributes;

class ApplicationForm implements ApplicationAttributes {

    private String name;
    private String content;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getContent() {
        return content;
    }
}
