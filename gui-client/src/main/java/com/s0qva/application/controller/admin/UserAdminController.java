package com.s0qva.application.controller.admin;

import com.s0qva.application.controller.scene.SceneSwitcher;
import com.s0qva.application.dto.user.UserCreationDto;
import com.s0qva.application.dto.user.UserReadingDto;
import com.s0qva.application.fxml.FxmlPageLoader;
import com.s0qva.application.service.UserService;
import com.s0qva.application.session.UserSession;
import com.s0qva.application.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
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
    private final Class<MainAdminPageController> mainAdminPageControllerClass;
    private final UserService userService;
    private final FxmlPageLoader fxmlPageLoader;
    @FXML
    private ListView<UserReadingDto> users;
    @FXML
    private VBox windowForBan;
    @FXML
    private HBox account;

    @Autowired
    public UserAdminController(UserService userService, FxmlPageLoader fxmlPageLoader) {
        this.userService = userService;
        this.fxmlPageLoader = fxmlPageLoader;
        this.mainAdminPageControllerClass = MainAdminPageController.class;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addEventToShowWindowToBanSelectedUser();
        addEventToShowUserAccount();
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

    private void addEventToShowUserAccount() {
        UserSession user = UserSession.getInstance();
        Label username = (Label) account.getChildren().get(0);
        username.setText(user.getUsername());

        account.setOnMouseClicked((click) -> {
            String content = "username: " + user.getUsername()
                    + "\nfull name: " + user.getFirstName() + " " + user.getLastName()
                    + "\namount of orders: " + user.getOrders().size();

            AlertUtil.generateInformationAlert(
                    DefaultAlertValue.INFO_ALERT_ACCOUNT_TITLE,
                    DefaultAlertValue.INFO_ALERT_ACCOUNT_HEADER,
                    content
            );
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

    private static class DefaultAlertValue {
        private static final String INFO_ALERT_ACCOUNT_TITLE = "My account";
        private static final String INFO_ALERT_ACCOUNT_HEADER = "Here is information about me";
    }
}
