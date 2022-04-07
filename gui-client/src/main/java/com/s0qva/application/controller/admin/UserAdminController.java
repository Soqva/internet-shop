package com.s0qva.application.controller.admin;

import com.s0qva.application.controller.eventhandler.DefaultUserAccountEventHandler;
import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.dto.user.UserCreationDto;
import com.s0qva.application.dto.user.UserReadingDto;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.service.UserService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@FxmlView("users-admin-page.fxml")
@Component
public class UserAdminController implements Initializable {
    private final UserService userService;
    private final FxmlPageLoader fxmlPageLoader;
    private final DefaultUserAccountEventHandler defaultUserAccountEventHandler;
    private final Class<MainAdminPageController> mainAdminPageControllerClass;
    @FXML
    private ListView<UserReadingDto> users;
    @FXML
    private VBox windowForBan;
    @FXML
    private HBox account;

    @Autowired
    public UserAdminController(UserService userService,
                               FxmlPageLoader fxmlPageLoader,
                               DefaultUserAccountEventHandler defaultUserAccountEventHandler) {
        this.userService = userService;
        this.fxmlPageLoader = fxmlPageLoader;
        this.defaultUserAccountEventHandler = defaultUserAccountEventHandler;
        this.mainAdminPageControllerClass = MainAdminPageController.class;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addEventToShowWindowToBanSelectedUser();
        defaultUserAccountEventHandler.addEventHandlerToShowUserAccount(account);
    }

    public void onReceiveAllUsers() {
        List<UserReadingDto> receivedUsers = userService.getAllUsers();
        users.setItems(FXCollections.observableArrayList(receivedUsers));
    }

    public void onBanSelectedUser() {
        changeUserAccess(true);
    }

    public void onUnbanSelectedUser() {
        changeUserAccess(false);
    }

    public void onCloseWindowForBan() {
        windowForBan.setVisible(false);
    }

    private void changeUserAccess(boolean isBanned) {
        UserReadingDto selectedUser = users.getSelectionModel().getSelectedItem();
        UserCreationDto updatingUser = buildUserCreationDto(selectedUser, isBanned);
        UserReadingDto updatedUser = userService.changeUserAccess(selectedUser.getId(), updatingUser);

        int replacementIndex = users.getItems().indexOf(selectedUser);
        users.getItems().set(replacementIndex, updatedUser);
        windowForBan.setVisible(false);
    }

    public void onBackToMainAdminPage(ActionEvent event) {
        Parent root = fxmlPageLoader.loadFxmlFile(mainAdminPageControllerClass);
        SceneSwitcher.switchScene(event, root);
    }

    private void addEventToShowWindowToBanSelectedUser() {
        users.setOnMouseClicked((click) -> {
            if (click.getClickCount() == 2) {
                windowForBan.setVisible(true);
            }
        });
    }

    private UserCreationDto buildUserCreationDto(UserReadingDto user, boolean isBanned) {
        return UserCreationDto.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .banned(isBanned)
                .build();
    }
}
