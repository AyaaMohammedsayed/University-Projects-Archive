#include <iostream>
#include <string>

using namespace std;

template <class T>
class Vector {
    T* data;
    int size;
public:
    Vector() {
        data = nullptr;
        size = 0;
    }

    Vector(int n) {
        data = new T[n];
        size = n;
    }

    ~Vector() {
        delete[] data;
    }

    int getsize() {
        return size;
    }

    void resize(int newsize) {
        T* newData = new T[newsize];
        for (int i = 0; i < size && i < newsize; ++i) {
            newData[i] = data[i];
        }
        delete[] data;
        data = newData;
        size = newsize;
    }

    T& operator[](int index) {
        if (index < 0 || index >= size) {
            cout<<"Index out of range";
        }
        return data[index];
    }

    void push_back(T item) {
        T* temp = new T[size + 1];
        for (int i = 0; i < size; i++) {
            temp[i] = data[i];
        }
        temp[size] = item;

        if (data != nullptr)
            delete[] data;
        size++;
        data = temp;
    }

    void pop_back() {
        if (size == 0) {
            cout<<"Vector is empty";
        }
        T* temp = new T[size - 1];
        for (int i = 0; i < size - 1; i++) {
            temp[i] = data[i];
        }

        if (data != nullptr)
            delete[] data;
        size--;
        data = temp;
    }

    bool empty() {
        return size == 0;
    }

    void clear() {
        if (size == 0) {
            return;
        }
        delete[] data;
        data = nullptr;
        size = 0;
    }

    void setsize(int i) {
        size = i;
    }
};

// Structure declared for storing details
struct details {
    string uname;
    int age;
    string password;
    string email;
    string mobile;
};

// Structure to store details about shops
struct Shops {
    string shop;
    string item1;
    string item2;
    string item3;
    int first, second, third;
};

struct purchases {
    string item;
    int price, n_item;
};
// Vector of Structure declared
Vector<Shops> Mole(5);
Vector<details> Users(100);
Vector<purchases> p(0);

// Global variables declared
string t_name, t_password1, t_password2, t_email, t_mobile, user_name;
int flag = 1, i=0, j = 0, Count = 0, caps = 0, z = 0;
int Small = 0, total = 0, success = 0, special = 0, numbers = 0, product1_quantity = 0, product2_quantity = 0, product3_quantity = 0;
int x, choice, t_age, item_choice, n, shop_choice, search_choice, confirm, ch, item, shop_id;

// Function prototypes
bool NameValidation(const string& t_name);
bool EmailValidation(const string& t_email);
bool PassValidation(const string& t_password1);
bool ConfirmPassValidation(const string& t_password1, const string& t_password2);
bool AgeValidation(int t_age);
bool MobileValidation(const string& t_mobile);
void signup();
void account_creation();
void login();
void shop_initialize();
void StartShopping();
void shop();
void Shop_selected(int shop_choice);
void items();
void item_selected(int item);
void cart();
void Confirm_order();
void Order();
int main();



bool NameValidation(const string& t_name) {
    // Name Validation
    for (int i = 0; i < t_name.length(); i++) {
        if (!((t_name[i] >= 'a' && t_name[i] <= 'z') || (t_name[i] >= 'A' && t_name[i] <= 'Z'))) {
            cout << "\nPlease enter a valid name!\n";
            return false;
        }
    }
    return true;
}

bool EmailValidation(const string& t_email) {
    // Email ID Validation
    int Count = 0;
    for (int i = 0; i < t_email.length(); i++) {
        if (t_email[i] == '@' || t_email[i] == '.')
            Count++;
    }
    if (Count >= 2 && t_email.length() >= 5) {
        return true;
    } else {
        cout << "\nPlease Enter a Valid E-Mail\n\n";
        return false;
    }
}

bool PassValidation(const string& t_password1) {
    int caps = 0, Small = 0, numbers = 0, special = 0;
    if (t_password1.length() >= 8 && t_password1.length() <= 12) {
        for (int i = 0; i < t_password1.length(); i++) {
            if (t_password1[i] >= 'A' && t_password1[i] <= 'Z')
                caps++;
            else if (t_password1[i] >= 'a' && t_password1[i] <= 'z')
                Small++;
            else if (t_password1[i] >= '0' && t_password1[i] <= '9')
                numbers++;
            else if (t_password1[i] == '@' || t_password1[i] == '&' || t_password1[i] == '#' || t_password1[i] == '*')
                special++;
        }
        if (caps >= 1 && Small >= 1 && numbers >= 1 && special >= 1)
            return true;
        else {
            cout << "\n\nPlease Enter a strong password. Your password must contain at least one uppercase, lowercase, number, and special character.\n\n";
            return false;
        }
    } else {
        cout << "\nYour Password's Length must be between 8 to 12\n\n";
        return false;
    }
}

bool ConfirmPassValidation(const string& t_password1, const string& t_password2) {
    if (t_password1 == t_password2)
        return true;
    else {
        cout << "\nPassword Mismatch\n\n";
        return false;
    }
}

bool AgeValidation(int t_age) {
    if (t_age >= 18)
        return true;
    else {
        cout << "\n\nPlease Enter a valid age\n\n";
        return false;
    }
}

bool MobileValidation(const string& t_mobile) {
    if (t_mobile.length() == 11) {
        for (int i = 0; i < 11; i++) {
            if (t_mobile[i] >= '0' && t_mobile[i] <= '9')
                return true;
            else {
                cout << "\n\nPlease Enter an 11-digit mobile number\n\n";
                return false;
            }
        }
    } else {
        cout << "\n\nPlease Enter an 11-digit mobile number\n\n";
        return false;
    }
}

void signup() {
    bool valid_account_items = false;

    cout << "\n\n\t******************Welcome to Signup Page*********************\n\n";

    // Taking name as input
    cout << "\tEnter Your name : ";
    cin >> t_name;
    valid_account_items = NameValidation(t_name);
    if (!valid_account_items)
        signup();

    // Taking email as input
    cout << "\tEnter Your Email : ";
    cin >> t_email;
    valid_account_items = EmailValidation(t_email);
    if (!valid_account_items)
        signup();

    // Taking password as input
    cout << "\tEnter Password : ";
    cin >> t_password1;
    valid_account_items = PassValidation(t_password1);
        signup();

    // Taking Confirm Password as input
    cout << "\tConfirm Password : ";
    cin >> t_password2;
    valid_account_items = ConfirmPassValidation(t_password1, t_password2);
    if (!valid_account_items)
        signup();

    // Taking Mobile number as input
    cout << "\tEnter Your Mobile Number : ";
    cin >> t_mobile;
    valid_account_items = MobileValidation(t_mobile);
        signup();

    // Taking Age as input
    cout << "\tEnter Your Age : ";
    cin >> t_age;
    valid_account_items = AgeValidation(t_age);
    if (!valid_account_items)
        signup();

    if (valid_account_items) {
        account_creation();
        login();
    }
}

void account_creation() {
    for (int k = 0; k < j; k++) {
        // Check if account already exists
        if (t_email == Users[k].email) {
            cout << "\n\nAccount already exists. Please login!\n\n";
            main();
            return;
        }
    }

    // If no account is present, create one
    if (j < 100) {
        Users[j].uname = t_name;
        Users[j].age = t_age;
        Users[j].password = t_password1;
        Users[j].email = t_email;
        Users[j].mobile = t_mobile;
        j++;
        cout << "\n\n\nAccount successfully created!\n\n";
    } else {
        cout << "\n\nCannot create more accounts. Limit reached!\n\n";
    }
}

void login() {
    cout << "\n\n\t******************Welcome to Login Page********************\n\n";
    cout << "\n\nLOGIN\n\n";
    cout << "\t Enter Your Email: ";
    cin >> t_email;
    cout << "\t Enter Your Password: ";
    cin >> t_password1;

    for (int k = 0; k < j; k++) {
        if (t_email == Users[k].email) {
            if (t_password1 == Users[k].password) {
                user_name = Users[k].uname;
                cout << "\n\nWelcome " << Users[k].uname << ", ";
                cout << "You are successfully logged in\n\n ";

                StartShopping();
                return;
            } else {
                cout << "\n\nInvalid Password!!\n ";
                cout << "Please enter the correct password\n\n";
                login();
                return;
            }
        }
    }

    cout << "\n\nAccount doesn't exist. Please login again!!\n\n ";
    main();
}


void shop_initialize() {
    // All the Products available are being stored

    Mole[1].shop = "Puma";
    Mole[1].item1 = "puma sneaker V2";
    Mole[1].item2 = "mens dryflex";
    Mole[1].item3 = "one8x puma";
    Mole[1].first = 3499;
    Mole[1].second = 2999;
    Mole[1].third = 5999;

    Mole[2].shop = "USA";
    Mole[2].item1 = "US polo Tshirt";
    Mole[2].item2 = "Shoes";
    Mole[2].item3 = "shirt";
    Mole[2].first = 999;
    Mole[2].second = 2000;
    Mole[2].third = 1499;

    Mole[3].shop = "WROGN";
    Mole[3].item1 = "Mens Watch";
    Mole[3].item2 = "mens solid jacket";
    Mole[3].item3 = "casual T-shirt";
    Mole[3].first = 2000;
    Mole[3].second = 2239;
    Mole[3].third = 799;
}

void StartShopping() {

    cout << "We provide two ways of search: \n ";
    cout << "1) Search by Shop\n ";
    cout << "2) Search by Item\n ";
    cout << "3) Exit\n\n";
    cout << "Please Enter your choice : ";
    cin >> search_choice;
    switch (search_choice) {
        case 1: {
            shop();
            break;
        }
        case 2: {
            items();
            break;
        }
        case 3: {
            main();
            break;
        }
        default: {
            cout << "Please Enter the valid choice!!!\n\n";
            break;
        }
    }
}

void shop() {
    shop_initialize();
    cout << "\n\nPlease Choose the Shop \n\n1) " << Mole[1].shop
         << "\n2) " << Mole[2].shop << "\n3) " << Mole[3].shop;
    cout << "\n4) Exit\n\nPlease select the shop\t";
    cin >> shop_choice;
    if (shop_choice < 1 || shop_choice > 4) {
        cout << "Please Enter a valid choice\n\n";
        shop();
    } else if (shop_choice == 4) {
        StartShopping();
    } else {
        Shop_selected(shop_choice);
    }
}

void Shop_selected(int shop_choice) {

    while (true) {
        cout << "\n\nList of items available in " << Mole[shop_choice].shop << "\n\n";
        cout << "1) " << Mole[shop_choice].item1 << " --> " << Mole[shop_choice].first << "\n";
        cout << "2) " << Mole[shop_choice].item2 << " --> " << Mole[shop_choice].second << "\n";
        cout << "3) " << Mole[shop_choice].item3 << " --> " << Mole[shop_choice].third << "\n";
        cout << "4) Cart\n5) Exit\n\nPlease Enter Your Choice : ";
        cin >> item_choice;
        switch (item_choice) {
            case 1:
            case 2:
            case 3: {

                cout << "Please Enter the Count of ";
                if (item_choice == 1)
                    cout << Mole[shop_choice].item1 << " : ";
                else if (item_choice == 2)
                    cout << Mole[shop_choice].item2 << " : ";
                else
                    cout << Mole[shop_choice].item3 << " : ";
                cin >> n;
                if (item_choice == 1)
                    total += n * Mole[shop_choice].first;
                else if (item_choice == 2)
                    total += n * Mole[shop_choice].second;
                else
                    total += n * Mole[shop_choice].third;
p.push_back({(item_choice == 1) ? Mole[shop_choice].item1 : ((item_choice == 2) ? Mole[shop_choice].item2 : Mole[shop_choice].item3),
                (item_choice == 1) ? Mole[shop_choice].first : ((item_choice == 2) ? Mole[shop_choice].second : Mole[shop_choice].third),
                n});
                break;
            }
            case 4:
                cart();
                break;
            case 5:
                shop();
                return;
            default:
                cout << "Please Enter a valid Choice\n\n";
                break;
        }
    }
}


void items() {
    shop_initialize();
    int item;
    while (true) {
        //Showing all the choices of items
        cout << "\n\nPlease choose the item\n\n";
        cout << "1) " << Mole[1].item1 << " \t--> " << Mole[1].first << "\n";
        cout << "2) " << Mole[1].item2 << "\t --> " << Mole[1].second << "\n";
        cout << "3) " << Mole[1].item3 << "\t --> " << Mole[1].third << "\n";
        cout << "4) " << Mole[2].item1 << "\t --> " << Mole[2].first << "\n";
        cout << "5) " << Mole[2].item2 << "\t --> " << Mole[2].second << "\n";
        cout << "6) " << Mole[2].item3 << "\t --> " << Mole[2].third << "\n";
        cout << "7) " << Mole[3].item1 << "\t --> " << Mole[3].first << "\n";
        cout << "8) " << Mole[3].item2 << "\t --> " << Mole[3].second << "\n";
        cout << "9) " << Mole[3].item3 << "\t --> " << Mole[3].third << "\n";
        cout << "10) Cart\n";
        cout << "11) Exit\n";
        cout << "\nPlease Enter Your Choice : ";
        cin >> item;
        if (item < 1 || item > 11) {
            cout << "Please Enter a valid choice\n\n";
        } else if (item == 10) {
            cart();
        } else if (item == 11) {
            StartShopping();
        } else {
            item_selected(item);
        }
    }
}

// item_selected function to process items ordered
void item_selected(int item) {



    if (item >= 1 && item <= 3)
        shop_id = 1;
    else if (item >= 4 && item <= 6)
        shop_id = 2;
    else
        shop_id = 3;
    if ((item % 3) == 1) {
        cout << "Please Enter the Count of " << Mole[shop_id].item1 << " : ";
        cin >> n;
        total = total + (n * Mole[shop_id].first);
        p.push_back({Mole[shop_id].item1, Mole[shop_id].first, n});
    } else if ((item % 3) == 2) {
        cout << "Please Enter the Count of " << Mole[shop_id].item2 << "\t";
        cin >> n;
        total = total + (n * Mole[shop_id].second);
        p.push_back({Mole[shop_id].item2, Mole[shop_id].second, n});
    } else if ((item % 3) == 0) {
        cout << "Please Enter the Count of " << Mole[shop_id].item3 << "\t";
        cin >> n;
        total = total + (n * Mole[shop_id].third);
        p.push_back({Mole[shop_id].item3, Mole[shop_id].third, n});
    }
}

void cart()
{
    // details about the products in cart
    cout << "\n\n\n\n\t*********************************"
        "Cart*********************************";
    cout<<"\n\t\t\tWelcome "<< user_name ;
    for(int k=0;k<p.getsize();k++){
        cout<<"\nAmount\t\tElement\t\tprice\n ";
        cout<<p[k].n_item<<"\t\t"<<p[k].item<<"\t\t"<<p[k].price<<"\n";

    }
    cout << "\n\nYour Total Order Amount is : " << total << "\n";
    cout << "\n\nDo you want to remove the last item from the cart? (y=1/n=0) : ";
    cin >> ch;
    if (ch == 1) {

            p.pop_back(); // Remove the last item from the cart
            cout << "\n\nLast item removed from the cart.\n\n";
            // Recalculate the total amount
            total = 0;
            for(int k=0;k<p.getsize();k++){
                total += (p[k].n_item * p[k].price);
            }
        }


    Order();
}

void Order() {
    cout << "\n\nDo You wish to order (y=1/n=0) : ";
    cin >> ch;
    if (ch == 1) {
        cout << "\n\nThank You " << user_name << " for your Order";
        cout << "\nYour item is on the way. Welcome again \n\n ";
        StartShopping();
        return;
    } else if (ch == 0) {
            total=0;
        cout << "To cancel Your Order = 1 \nTo Exit& just Save = 0 ";
        cout << "Select option : ";
        cin >> confirm;
        if (confirm == 1) {
            cout << "\n\nThanks! Your item is canceled!! Exiting..\n\n";
        p.clear(); // Clearing the data in vector p
            StartShopping();
            return;
        }
        else if (confirm == 0) {
            total=0;
            StartShopping();
            return;
        } else {
            cout << "\nPlease Enter a valid choice\n\n";
            Order();
            return;
        }
    }
}
// main function

int main() {
total=0;
    cout << "\n\n\t\t****Welcome to the E-commerce Website****\n\n";
    cout << "1) Signup\n2) Login\n3) Exit\n\nPlease Enter Your choice : ";
    cin >> choice;
    switch (choice) {
        case 1:
            signup();
            break;
        case 2:
            login();
            break;
        case 3:
            cout << "\n\nThanks for Visiting!!\n\n ";
            break;
        default:
            cout << "Please Enter a valid choice\n\n";
            break;
    }
    return 0;
}
