package ru.geekbrains.dropbox.frontend.service;

import java.io.*;
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
