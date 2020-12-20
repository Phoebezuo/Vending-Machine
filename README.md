# Vending Machine

A vending machines which sell different types of snacks, paid by cash or card. It also allows different users to login, which enables specific features. 

## How to run the program

You can use `gradle build` first to build the program. After that, you can use `gradle run` to run the program.

For testing, use `gradle test jacocoTestReport` to test the program. The jacoco test report can be found in `build/jacocoHtml/index.html`. (Notes: GUI is not tested)

**NOTE: If you run the test, it will restore all resources to the initial state for a uniform testing environment. All changes on users, cashes or products will lose.**

**NOTE: `gradle build` will run the tests automatically. If you want to build the program again but want to keep the resources, please use `gradle build -x test` to exclude testing. Or if you just want to run the program again, please use `gradle run`.**

## Usage

`blan` is an owner. Its password is `123`. Feel free to use this account to test functionalities.

One of credit cards has name `Charles` and card number `40691`. Feel free to use this card to test functionalities.

## Demo

`blan` is an owner. Its password is `123`. Feel free to use this account to test functionalities.

One of credit cards has name `Charles` and card number `40691`. Feel free to use this card to test functionalities.

Here we are using the code with tag `2.0` for demonstration which you can see on Release page on Github.

### Sign In or Sign Up

At first the window will display the current user is `anonymous` by default.

![Screen Shot 2020-11-19 at 11.53.35 PM.png](https://i.loli.net/2020/11/19/SRg5oT4AUZe3P91.png)

After click on `Account` button, it display a window to sign in or sign up.

<img src='https://i.loli.net/2020/11/05/EGFWUjfksDiKBrM.png' alt='EGFWUjfksDiKBrM'/>

When it sign in with correct username and password the main window will display corresponding username and its type on the top right corner, as well as the text on button will change to `Logout` instead of `Account`.

<a href="https://sm.ms/image/ig7YMaljtQCvZez" target="_blank"><img src="https://i.loli.net/2020/11/20/ig7YMaljtQCvZez.png" ></a>

When it sign in with wrong username or password, it will display an alert to notify user it has wrong username or password.

<img src='https://i.loli.net/2020/11/05/RsQorqTLCEMVWZa.png' alt='RsQorqTLCEMVWZa'/>

### Shopping Cart

#### Display Product

The Product table on default page is available for every user include all the products that the machine has.

![Screen Shot 2020-11-19 at 9.25.53 PM.png](https://i.loli.net/2020/11/19/1wu3kqCRMzKDQg8.png)

#### Display cart

The cart display on product table on default page is available for every user include all the products were selected by current user.

![Screen Shot 2020-11-19 at 9.23.17 PM.png](https://i.loli.net/2020/11/19/PNF53hZKXsVxajG.png)

#### Add to Cart

The user can select which product he want to add to cart, then click the row which the product is, after that this product's name will be automatically filled in the item name field, then choose the quantity he want. After that click on `Set Qty in cart` button. The cart table will update accordingly.

![Screen Shot 2020-11-19 at 9.37.05 PM.png](https://i.loli.net/2020/11/19/V6NQiOWn28kAwJL.png)

#### Remove Cart

![Screen Shot 2020-11-19 at 9.39.47 PM.png](https://i.loli.net/2020/11/19/KMJj852vYg7hbCo.png)

The user can select which product he want to remove, then click the row which the product is,  then choose the quantity he want to remove. After that click on `Set Qty in cart` button. The cart table will update accordingly.

![Screen Shot 2020-11-19 at 9.40.26 PM.png](https://i.loli.net/2020/11/19/ExUvHzhpyrjlksm.png)

### Purchase Item

#### Checkout Window

The Checkout window is available for every users. If current cart table has nothing, after click `Checkout` button, it will display an alert to notify current user have no item to purchase.

![Screen Shot 2020-11-19 at 9.58.21 PM.png](https://i.loli.net/2020/11/19/Rbx2XtHATY6Gfsw.png)

After click `Checkout` button, there is a window display items' amount in cart. Besides, there are three more operations, card, cash or cancel. And in the middle there is 120s countdown, when it comes to 0, it will automatically back to default window, and record this transaction to be cancelled with reason "timeout".

![Screen Shot 2020-11-19 at 9.59.17 PM.png](https://i.loli.net/2020/11/19/YmyXugcSDkZnzMd.png)

#### Card Payment

After click the `Card` button, it need user input card name and number.  Besides, there are two more operations, check and pay. And in the upper left corner there is 120s countdown, when it comes to 0, it will automatically back to default window. And in the lower left corner, user can tick to save the card information or not.

![Screen Shot 2020-11-20 at 1.17.47 AM.png](https://i.loli.net/2020/11/20/qF1aWQxd4mVIE6Z.png)

User input the name and number of the card he want to pay this transaction.

![Screen Shot 2020-11-20 at 2.33.20 PM.png](https://i.loli.net/2020/11/20/4k8L7hq6X1ndluD.png)

If user save the card before, System will automatically filled the information.

![Screen Shot 2020-11-20 at 3.00.37 PM.png](https://i.loli.net/2020/11/20/DejKxI4AXYvU6W9.png)

After click the `Cancel` button, it will back to default window and record this transaction to be cancelled with reason "user cancelled".

#### Check card

After click the `check` button, if the card information is incorrect, it will display an alert notify current user the card is wrong. If the information is correct, this transaction is succeeded.

![Screen Shot 2020-11-20 at 3.06.00 PM.png](https://i.loli.net/2020/11/20/EL5eHJ4KOFqbMtv.png)

![Screen Shot 2020-11-19 at 10.02.02 PM.png](https://i.loli.net/2020/11/20/oGTPhIwsXMNfWr8.png)

If the user tick the `Save card` and click `Check` button. if user is anonymous, it will display an alert to notify user is anonymous which does not right to save the card.

![Screen Shot 2020-11-20 at 3.00.37 PM.png](https://i.loli.net/2020/11/20/rSQKeTAFvxk3JWZ.png)

![Screen Shot 2020-11-20 at 1.24.01 AM.png](https://i.loli.net/2020/11/20/TBa8CVXLJlFpQgx.png)

#### Cash Payment

After click the `Cash` button, there is a table include the cash's value and number.  Besides, there are two more operations, set, pay and cancel. In addition, in the upper left corner there is 120s countdown, when it comes to 0, it will automatically back to default window, and record this transaction to be cancelled with reason "timeout".

![Screen Shot 2020-11-19 at 10.02.02 PM.png](https://i.loli.net/2020/11/19/hUwDjcamTgzvPL1.png)

#### Set Cash to Pay

![Screen Shot 2020-11-19 at 10.04.15 PM.png](https://i.loli.net/2020/11/19/pEbWNTgs1zwnFZv.png)

The user can input cash type and its amount. Then click the`Set` button, after that the table will update accordingly.

![Screen Shot 2020-11-19 at 10.04.23 PM.png](https://i.loli.net/2020/11/19/RXM7ZjG4A8J3viY.png)

#### Pay

If user didn't add any cash. It will display an alert to notify current user doesn't pay any cashes.

![Screen Shot 2020-11-12 at 7.27.54 PM.png](https://i.loli.net/2020/11/12/zBiTOxtRXC4G75V.png)

After click the `Pay` button. There is a table of change include different values and amount. The cash in the machine also will be reduced accordingly.

![32131605174938_.pic.jpg](https://i.loli.net/2020/11/12/yDHv5XOYoZzQKgG.png)

If user didn't pay enough money to purchase, it will automatically cancelled this transaction with reason "no enough money paid".

If vending machine does not have enough money to give back change, it will also automatically cancelled this transaction with reason "no available changes".

### Display Information

#### Display the latest 5 Products

The latest bought items table on default page is available for every user. If you have login, it will show your purchase history, else it will show the anonymous' last 5 bought items.

![Screen Shot 2020-11-19 at 11.06.53 PM.png](https://i.loli.net/2020/11/19/kJ54rwRtx3E2cDe.png)

#### Display Sold History

The sold history window is only available for owner and seller users. If current user is not them, it will display an alert to notify current user have no access to this feature.

![截屏2020-11-12 下午3.03.15.png]( https://i.loli.net/2020/11/19/EerYG6ysmFgoq1W.png)

After click the `Sold History` button. There is a table of all the product that have been sold in the system.

![Screen Shot 2020-11-20 at 12.02.14 AM.png](https://i.loli.net/2020/11/20/BWj7oeMc9y1gDYa.png)

#### Display Transaction History

The  Transaction History  window is only available for owner and cashier users. If current user is not owner or cashier, it will display an alert to notify current user have no access to this feature.

![截屏2020-11-12 下午3.03.15.png]( https://i.loli.net/2020/11/19/EerYG6ysmFgoq1W.png)

After click the `Transaction History` button. There is a table of transactions that includes transaction date and time, item sold, amount of money paid, returned change and payment method. Besides, there is one more operation, view transaction product history.

![Screen Shot 2020-11-20 at 2.33.43 PM.png](https://i.loli.net/2020/11/20/lNa1zkdXSg6Eu4H.png)

The owner can select which history he want to view, then then click on `View details` button. There is a table of all products sold in this transaction include name, category, price and quantity.

![Screen Shot 2020-11-20 at 2.33.49 PM.png](https://i.loli.net/2020/11/20/QkTlZ32IC8px5Hu.png)

#### Display Cancelled History

The user management window is only available for owner users. If current user is not owner, it will display an alert to notify current user have no access to this feature.

![截屏2020-11-12 下午3.03.15.png]( https://i.loli.net/2020/11/19/EerYG6ysmFgoq1W.png)

After click the `Cancelled History` button. There is a table of cancelled transaction during this system includes date and time of the cancelled, the user and the reasons.

![Screen Shot 2020-11-20 at 2.50.26 PM.png](https://i.loli.net/2020/11/20/53AG9vUu2SVNKPO.png)

#### Generate Report

![Screen Shot 2020-11-20 at 1.12.40 AM.png](https://i.loli.net/2020/11/20/nbldTSsXNvCmL3q.png)

After click the `Generae Report` button. There are some operations which can generate different report . After click these buttons, a new folder called `report` will be created, and then according to different permissions, different csv reports can be generated in the `report` folder.

![Screen Shot 2020-11-20 at 2.34.53 PM.png](https://i.loli.net/2020/11/20/XUSivCYakFQ8WNz.png)

### User Management

#### Display Users

The user management window is only available for owner users. If current user is not owner, it will display an alert to notify current user have no access to this feature.

![截屏2020-11-12 下午3.03.15.png]( https://i.loli.net/2020/11/19/EerYG6ysmFgoq1W.png)

After click the `Manage User` button. There is a table of all the users that have been registered in the system. Besides, there are three more operations, add or change or remove users, that owner have access.

<a href="https://sm.ms/image/uVJlwexs8d5XNrR" target="_blank"><img src="https://i.loli.net/2020/11/12/uVJlwexs8d5XNrR.png" ></a>

#### Add Users

<a href="https://sm.ms/image/fr5Uy8JKEpTtXAS" target="_blank"><img src="https://i.loli.net/2020/11/12/fr5Uy8JKEpTtXAS.png" ></a>

The owner can input new username, password and user type, then click on `Add` button. The table will update accordingly.

<img src="https://i.loli.net/2020/11/12/hELAuvcTClirbmQ.png" >

If owner does not input full relevant information then click on `Add` button, it will display an alert to notify current owner to fill in the missing information.

![Screen Shot 2020-11-20 at 2.38.21 PM.png](https://i.loli.net/2020/11/20/9reMsNWm1GoULO5.png)

#### Change Users

<a href="https://sm.ms/image/hELAuvcTClirbmQ" target="_blank"><img src="https://i.loli.net/2020/11/12/hELAuvcTClirbmQ.png" ></a>

The owner first need to select which users he want to change, then click on the row where the user is, then the relevant information of this user will be automatically filled in the information field, then edit the username, password or user which users want to be change, then click on `Change` button. The table will update accordingly.

#### Remove Users

<img src="https://i.loli.net/2020/11/12/hELAuvcTClirbmQ.png" >

The owner can select which users he want to remove, then click the row which the user is, after that this user's relevant information will be automatically filled in the information field, then click on `Remove` button.  the table will update accordingly.

![Screen Shot 2020-11-20 at 2.42.57 PM.png](https://i.loli.net/2020/11/20/e8Ri71Lqu49lz2h.png)

### Cash Management

#### Display Cash

The cash management window is only available for owner and cashier users. If current user is not them, it will display an alert to notify current user have no access to this feature.

![截屏2020-11-12 下午3.03.15.png]( https://i.loli.net/2020/11/19/EerYG6ysmFgoq1W.png)

After click the `Manage Cash` button, there is a table has all types of cash exist in Australia. Besides, there are one more operation change the number of the  value.

<a href="https://sm.ms/image/uWpcfbNvVwKgyFC" target="_blank"><img src="https://i.loli.net/2020/11/12/uWpcfbNvVwKgyFC.png" ></a>

#### Change Cash

<a href="https://sm.ms/image/5GQh6q7bvIAKiSo" target="_blank"><img src="https://i.loli.net/2020/11/12/5GQh6q7bvIAKiSo.png" ></a>

The user can select which value he wants to change, then click the row which the value is, after that the relevant information of this value will be automatically filled in the information field. Input the new number which the user want, then click the `Change number` button.  The table will update accordingly.

If the user input symbol or letters. An alert box will notify it  is fail to change.

![截屏2020-11-12 下午6.19.13.png](https://i.loli.net/2020/11/12/VBr1KYnGwzFufbT.png)

![Screen Shot 2020-11-12 at 7.30.26 PM.png](https://i.loli.net/2020/11/12/MvQ6oEKbcSIWXP3.png)

### Product Management

#### Display Product

The product management window is only available for owner and seller users. If current user is not them, it will display an alert to notify current user have no access to this feature.

![截屏2020-11-12 下午3.03.15.png]( https://i.loli.net/2020/11/19/EerYG6ysmFgoq1W.png)

After click the `Manage Product` button, there is a table of all the products that the machine has. Besides, there are three more operations, add or change or remove products.

<a href="https://sm.ms/image/eKgdJUSvxuiNYj9" target="_blank"><img src="https://i.loli.net/2020/11/12/eKgdJUSvxuiNYj9.png" ></a>

#### Add Product

<a href="https://sm.ms/image/3a4CNjrhck5Et8q" target="_blank"><img src="https://i.loli.net/2020/11/12/3a4CNjrhck5Et8q.png" ></a>

The user can input new code, Name, Price and Quantity, then click on `Add` button. An alert box will notify it is successfully added and the table will update accordingly.

![Screen Shot 2020-11-20 at 2.58.12 PM.png](https://i.loli.net/2020/11/20/3nYacXBKGDzLt17.png)

If users input the repeated code or name then click on `Add` button, it will display an alert to notify current user the product exists in the system.![Screen Shot 2020-11-12 at 7.34.17 PM.png](https://i.loli.net/2020/11/12/DdxVQ1iSBzjKyw6.png)

#### Change Product

<a href="https://sm.ms/image/Cl2ULbuFWf8KHx6" target="_blank"><img src="https://i.loli.net/2020/11/12/Cl2ULbuFWf8KHx6.png" ></a>

The user first need to select which product he want to change, then click on the row where the product is, then the relevant information of this product will be automatically filled in the information field, then edit the code,name,price,quantity and category which user want. then click on `Change` button. The table will update accordingly.

If user input invalid price like lettes or symbols. An alert box will notify it is failed changed.

![截屏2020-11-12 下午12.14.09.png](https://i.loli.net/2020/11/12/cnVwloX4rN9UEZ8.png)

![Screen Shot 2020-11-12 at 7.45.52 PM.png](https://i.loli.net/2020/11/12/BLDsrO2ANq9FVit.png)

#### Remove Product

![截屏2020-11-12 下午12.17.26.png](https://i.loli.net/2020/11/12/OFZ1TarV2HNbJ4d.png)

The user can select which product he want to remove, then click the row which the product is, after that this product's relevant information will be automatically filled in the information field, then click on `Remove` button. The table will update accordingly.

