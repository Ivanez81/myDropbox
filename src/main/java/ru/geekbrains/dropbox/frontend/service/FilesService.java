package ru.geekbrains.dropbox.frontend.service;

import com.vaadin.ui.TextField;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface FilesService {
    OutputStream getFileOutputStream(String fileName) throws IOException;
    File getFileByName(String fileName);
    InputStream getFileInputStream(String fileName) throws FileNotFoundException;
    List<File> getFileList();
    void removeFile(File file);
    Set<File> sFilter(List<String> filters);
}
