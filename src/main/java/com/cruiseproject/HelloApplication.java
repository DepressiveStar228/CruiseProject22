// Розробники: Дорошенко Д.І., Нікіфоров А.А., Бариба Ю.Ю., Куц А.В., Придатко Н.В.
// Дисципліна: ООП на мові Java
// Завдання: Проєкт на тему "Круїз плавання"
// 27 травень 2024
// Години : 150 годин
// Всі в команді визнають, що це наша групова робота
/*
    Ця програма створена для того, щоб зробити процес планування та бронювання круїзів максимально простим і приємним
для користувачів.
    Перегляд доступних круїзів
Програма надає можливість переглядати список доступних круїзів. Користувачі можуть ознайомитися з різноманітними
пропозиціями та вибрати той круїз, який найбільше відповідає їхнім побажанням і вимогам.
    Замовлення квитків
Для зручності користувачів передбачено функцію замовлення квитків. Вона дозволяє легко та швидко бронювати круїзні
квитки, вводячи лише необхідні дані. Процес замовлення інтуїтивно зрозумілий і не потребує багато часу.
    Можливість відмінити та повернути квиток
Якщо у користувача виникне потреба відмінити своє бронювання, програма надає можливість швидко й без зайвих труднощів
скасувати замовлення та повернути квиток. Це робить використання програми ще більш гнучким і зручним.
    Можливість знайти квиток та дізнатися про його замовника
Програма включає функцію пошуку квитків, що дозволяє швидко знайти потрібний квиток за введеними даними.
Користувачі можуть також отримати детальну інформацію про замовника квитка, що полегшує управління бронюваннями.
    Отримання інформації про програму здійснюється через кнопку "Довідка".
    Отримання інформації про розробників програми здійснюється через кнопку "Розробники".
*/

package com.cruiseproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    // Метод відображення головного вікна програми при старті
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/cruiseproject/windows/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/cruiseproject/icons/icon-cruise-16x16.png"))));
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/cruiseproject/icons/icon-cruise-24x24.png"))));
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/cruiseproject/icons/icon-cruise-32x32.png"))));
        stage.setResizable(false);
        stage.setTitle("Cruise Project");
        stage.setScene(scene);
        stage.show();
    }

    // Метод запуску програми
    public static void main(String[] args) {
        launch();
    }
}