package com.s0qva.application.controller.admin;

import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.dto.user.UserReadingDto;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.service.UserService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@FxmlView("users-admin-page.fxml")
@Component
public class UserAdminController {
    private final Class<MainAdminPageController> mainAdminPageControllerClass;
    private final UserService userService;
    private final FxmlPageLoader fxmlPageLoader;
    @FXML
    private ListView<UserReadingDto> users;

    @Autowired
    public UserAdminController(UserService userService, FxmlPageLoader fxmlPageLoader) {
        this.userService = userService;
        this.fxmlPageLoader = fxmlPageLoader;
        this.mainAdminPageControllerClass = MainAdminPageController.class;
    }

    public void onReceiveAllUsers() {
        List<UserReadingDto> receivedUsers = userService.getAllUsers();
        users.setItems(FXCollections.observableArrayList(receivedUsers));
    }

    public void onBackToMainAdminPage(ActionEvent event) {
        Parent root = fxmlPageLoader.loadFxmlFile(mainAdminPageControllerClass);
        SceneSwitcher.switchScene(event, root);
    }
}
