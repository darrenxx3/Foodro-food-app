USE mobapp;
DROP DATABASE IF EXISTS mobapp;
CREATE DATABASE mobapp;
USE mobapp;
CREATE TABLE Roles (
    role_id INT AUTO_INCREMENT NOT NULL,
    role_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (role_id)
);
CREATE TABLE Status (
    status_id INT AUTO_INCREMENT NOT NULL,
    status_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (status_id)
);
CREATE TABLE PaymentCategory (
    category_id INT AUTO_INCREMENT NOT NULL,
    payment_name VARCHAR(100) NOT NULL,
    PRIMARY KEY (category_id)
);
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT NOT NULL,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    password CHAR(60) NOT NULL,
    email VARCHAR(255) NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id),
    FOREIGN KEY (role_id) REFERENCES Roles(role_id),
    UNIQUE(email)
);
CREATE TABLE Food (
    food_id INT AUTO_INCREMENT NOT NULL,
    food_name VARCHAR(50) NOT NULL,
    food_price INT NOT NULL,
    food_image VARCHAR(300) NOT NULL,
    merchant_id INT  NOT NULL,
    PRIMARY KEY (food_id),
    FOREIGN KEY (merchant_id) REFERENCES Users(user_id)
);

CREATE TABLE Orders (
    order_id INT AUTO_INCREMENT NOT NULL,
    user_id INT  NOT NULL,
    orderDate DATETIME NOT NULL,
    PRIMARY KEY (order_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)

    -- FOREIGN KEY (payment_id) REFERENCES Payment(payment_id)
);

CREATE TABLE OrderDetail (
    order_id INT NOT NULL,
    status_id INT NOT NULL,
    food_id INT NOT NULL,
    quantity INT NOT NULL,
    total INT NOT NULL,                                                                             
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),
    FOREIGN KEY (status_id) REFERENCES Status(status_id),
    FOREIGN KEY (food_id) REFERENCES Food(food_id)
);

CREATE TABLE Payment (
    payment_id INT NOT NULL,
    category_id INT NOT NULL,
    totalPayment INT NOT NULL,
    proofImage VARCHAR NOT NULL,
    -- PRIMARY KEY (payment_id),
    FOREIGN KEY (payment_id) REFERENCES Orders(order_id),
    FOREIGN KEY (category_id) REFERENCES PaymentCategory(category_id),
    UNIQUE(payment_id),
    UNIQUE(proofImage)
);

INSERT INTO Roles
VALUES (NULL, "Customer");
INSERT INTO Roles
VALUES (NULL, "Merchant");
INSERT INTO Roles
VALUES (NULL, "Admin");
INSERT INTO Users
VALUES (
        NULL,
        "customer1",
        "last",
        "pass",
        "email@a.com",
        1
    );
INSERT INTO Users
VALUES (
        NULL,
        "Customer2",
        "last2",
        "pass",
        "email@a1.com",
        1
    );
INSERT INTO Users
VALUES (
        NULL,
        "Customer3",
        "last3",
        "pass",
        "email@a2.com",
        1
    );
INSERT INTO Users
VALUES (
        NULL,
        "Merchant1",
        "last4",
        "1234567890qwertyuiopasdfghjkl;zxcvbnm,./1234567890-123456789",
        "email@merchant.com",
        2
    );
INSERT INTO Users
VALUES (
        NULL,
        "Merchant2",
        "last5",
        "1234567890qwertyuiopasdfghjkl;zxcvbnm,./1234567890-123456789",
        "email@merchant2.com",
        2
    );
INSERT INTO Users
VALUES (
        NULL,
        "Admin1",
        "last12",
        "1234567890qwertyuiopasdfghjkl;zxcvbnm,./1234567890-123456789",
        "email@admin.com",
        3
    );
INSERT INTO Status
VALUES (NULL, "Pending");
INSERT INTO Status
VALUES (NULL, "Ready");
INSERT INTO Status
VALUES (NULL, "Finished");
INSERT INTO PaymentCategory
VALUES (NULL, "Gopay");
INSERT INTO PaymentCategory
VALUES (NULL, "Ovo");
INSERT INTO PaymentCategory
VALUES (NULL, "Dana");
INSERT INTO PaymentCategory
VALUES (NULL, "M-BCA");
INSERT INTO Food
VALUES (
        NULL,
        "Tempe Mendoan",
        "2000",
        "images/1.jpg",
        1
    );
INSERT INTO Food
VALUES (
        NULL,
        "Bakso kiloan",
        "9000",
        "images/2.jpg",
        2
    );
INSERT INTO Food
VALUES (
        NULL,
        "Nasi Rebus",
        "15000",
        "images/3.jpg",
        2
    );

INSERT INTO Orders VALUES (NULL, 1, NOW());
INSERT INTO OrderDetail VALUES (LAST_INSERT_ID(), 3, 1, 1, 100000);
INSERT INTO Payment VALUES (LAST_INSERT_ID(), 1, 100000, "/proof/1.jpg");


INSERT INTO Orders VALUES (NULL, 1, NOW());
INSERT INTO OrderDetail VALUES (LAST_INSERT_ID(), 3, 1, 1, 200000);
INSERT INTO Payment VALUES (LAST_INSERT_ID(), 1, 200000, "/proof/2.jpg");


INSERT INTO Orders VALUES (NULL, 1, NOW());
INSERT INTO OrderDetail VALUES (LAST_INSERT_ID(), 3, 1, 1,300000);
INSERT INTO Payment VALUES (LAST_INSERT_ID(), 4, 300000, "/proof/3.jpg");

INSERT INTO Orders VALUES (NULL, 2, NOW());
INSERT INTO OrderDetail VALUES (LAST_INSERT_ID(), 3, 3, 1,300000);
INSERT INTO OrderDetail VALUES (LAST_INSERT_ID(), 1, 2, 1,300000);
INSERT INTO OrderDetail VALUES (LAST_INSERT_ID(), 2, 2, 1,300000);
INSERT INTO OrderDetail VALUES (LAST_INSERT_ID(), 3, 2, 1,300000);
INSERT INTO Payment VALUES (LAST_INSERT_ID(), 2, 600000);


-- SELECT Food.merchant_id, Food.food_id, Food.food_name, Food.food_price, OrderDetail.quantity, OrderDetail.total, OrderDetail.status_id,  OrderDetail.user_id, OrderDetail.orderDate FROM Orders
-- INNER JOIN OrderDetail ON Orders.order_id = OrderDetail.order_id
-- INNER JOIN Food ON OrderDetail.food_id = Food.food_id WHERE Food.merchant_id = 1

-- UPDATE OrderDetail SET status_id = 2 WHERE order_id = 4 AND food_id = 1 AND status_id = 3;
