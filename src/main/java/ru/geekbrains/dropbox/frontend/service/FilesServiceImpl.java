package ru.geekbrains.dropbox.frontend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.geekbrains.dropbox.frontend.dao.FilesDAO;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilesServiceImpl implements FilesService {
    @Autowired
    FilesDAO dao;


//    @PreAuthorize("hasRole('USER')")
    @Override
    public OutputStream getFileOutputStream(String fileName) throws IOException {
        return dao.getFileOutputStream(fileName);
    }

//    @PreAuthorize("hasRole('USER')")
    @Override
    public File getFileByName(String fileName) {
        return dao.getFileByName(fileName);
    }

//    @PreAuthorize("hasRole('USER')")
    @Override
    public InputStream getFileInputStream(String fileName) throws FileNotFoundException {
        return dao.getFileInputStream(fileName);
    }

//    @PreAuthorize("hasRole('USER')")
    public List<File> getFileList() {
        return dao.getFileList();
    }

//    @PreAuthorize("hasRole('USER')")
    @Override
    public void removeFile(File file) {
        dao.removeFile(file);
    }

//    @PreAuthorize("hasRole('USER')")
    @Override
    public Set<File> sFilter(List<String> filters) {
        Set<File> filterSet = new HashSet<>();

        filters.forEach(str ->
                filterSet.addAll(getFileList()
                        .stream()
                        .filter(file -> file.getName()
                                .contains(str))
                        .collect(Collectors.toList())
                )
        );
        return filterSet;
    }
}
