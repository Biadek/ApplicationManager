package pl.tbiadacz.ApplicationManager.application.interfaces;

import pl.tbiadacz.ApplicationManager.application.application.command.ApplicationAttributes;

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
