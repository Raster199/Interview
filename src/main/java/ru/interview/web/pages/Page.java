package ru.interview.web.pages;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by raster on 13.12.16.
 */
public interface Page {
    public String getHtml() throws IOException;
}
