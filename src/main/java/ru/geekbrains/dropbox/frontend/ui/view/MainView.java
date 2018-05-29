package ru.geekbrains.dropbox.frontend.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.ItemClickListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.geekbrains.dropbox.frontend.service.AuthService;
import ru.geekbrains.dropbox.frontend.service.FilesService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringView(name = "")
public class MainView extends VerticalLayout implements View {

    @Autowired
    FilesService filesService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthService authService;

    private Grid<File> gridFiles = new Grid<>();
    private FileDownloader fileDownloader;
    private Button btnDownload = new Button("Download");
    private Button btnDelete = new Button("Delete");
    private Panel pnlActions = new Panel();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        setSizeUndefined();

        gridFiles.addColumn(File::getName).setCaption("File");
        gridFiles.setSizeFull();
        gridFiles.setItems(filesService.getFileList()); //грид отображает данные

        // Выбираем файл который скачаем
        gridFiles.addItemClickListener(new ItemClickListener<File>() {
            @Override
            public void itemClick(Grid.ItemClick<File> itemClick) {
                // Удаляем старый даунлоадер
                if (fileDownloader != null)
                    btnDownload.removeExtension(fileDownloader);

                // Создаем компонент который будет скачивать
                fileDownloader = new FileDownloader(
                        createResource(
                                itemClick.getItem().getName()
                        )
                );
                fileDownloader.extend(btnDownload);
            }
        });

        btnDelete.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                gridFiles.getSelectedItems().iterator().forEachRemaining(file -> filesService.removeFile(file));
                gridFiles.setItems(filesService.getFileList());
            }
        });

        Upload uploadFile = new Upload();
        uploadFile.setButtonCaption("Upload");
        uploadFile.setImmediateMode(true);
        uploadFile.setReceiver(new Upload.Receiver() {
            @Override
            public OutputStream receiveUpload(String fileName, String mimeType) {
                try {
                    return filesService.getFileOutputStream(fileName);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    Notification.show("Upload error!").setDelayMsec(1000);
                }
                return null;
            }
        });
        uploadFile.addFinishedListener(finishedEvent -> gridFiles.setItems(filesService.getFileList()));

        HorizontalLayout layoutActions = new HorizontalLayout();
        layoutActions.setSizeUndefined();
        pnlActions.setSizeUndefined();
        pnlActions.setContent(layoutActions);
        layoutActions.addComponents(uploadFile, btnDelete, btnDownload);
        addComponents(gridFiles, pnlActions);

        layoutActions.addComponent(new Button("Exit user: " +
                SecurityContextHolder.getContext().getAuthentication().getName(), clickEvent -> {
//            getUI().getPage().open("/logout", null);
            getUI().getPage().setLocation("/start");
            authService.logout();
//            getUI().getNavigator().navigateTo("/start");
        }));

        ArrayList<TextField> arrTextFields = new ArrayList<>();

        HorizontalLayout layoutFilter = new HorizontalLayout();
        TextField textFilter = new TextField();
        arrTextFields.add(textFilter);
        Button btnAddFilter = new Button("Add Filter");

        Button btnFilterName = new Button("Search", clickEvent -> {
            List<String> arrStr =
                    arrTextFields
                            .stream()
                            .map(AbstractTextField::getValue)
                            .collect(Collectors.toList());
            gridFiles.setItems(filesService.sFilter(arrStr));
        });


        layoutFilter.addComponents(textFilter, btnAddFilter);
        addComponent(btnFilterName);
        addComponent(layoutFilter);

        btnAddFilter.addClickListener(clickEvent -> {
            HorizontalLayout layoutAddFilter = new HorizontalLayout();
            Label lblOr = new Label("Or: ");
            TextField textAddFilter = new TextField();
            Button btnDelFilter = new Button("Delete", clickEvent1 -> {
                removeComponent(layoutAddFilter);
                arrTextFields.remove(textAddFilter);
            });
            arrTextFields.add(textAddFilter);
            layoutAddFilter.addComponents(lblOr, textAddFilter, btnDelFilter);
            addComponent(layoutAddFilter);
        });
    }

    private void startedUpload(Upload.StartedEvent event) {
        Notification.show("UploadStart");
    }

    private StreamResource createResource(String fileName) {
        return new StreamResource(new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
                try {
                    return filesService.getFileInputStream(fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }, fileName);
    }
}
