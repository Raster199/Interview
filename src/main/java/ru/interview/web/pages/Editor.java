package ru.interview.web.pages;

import ru.interview.web.FileWorker;
import ru.interview.web.UrlParser;

import java.io.IOException;

public class Editor implements Page {

    private String baseDir = new UrlParser().getBaseDir();

    public String getHtml() throws IOException {
        return FileWorker.read(baseDir + "editor/index.html");
    }
}
