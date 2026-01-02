public class Shop {
    /**
     * Вызвать метод совершения покупки несколько раз такимобразом,
     * чтобы заполнить массив покупок возвращаемыми значениями.
     * Обработать исключения следующим образомв (в заданном порядке):
     * */
    
    public enum Gender {
        MALE, FEMALE
    }
    
    public enum Holiday {
        NO_HOLIDAY, NEW_YEAR, WOMENS_DAY, MENS_DAY
    }
    
    private static class Customer {
        String name;
        int age;
        String phone;
        Gender gender;
        
        public Customer(String name, int age, String phone, Gender gender) {
            this.name = name;
            this.age = age;
            this.phone = phone;
            this.gender = gender;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public int getAge() {
            return age;
        }
        
        public void setAge(int age) {
            this.age = age;
        }
        
        public String getPhone() {
            return phone;
        }
        
        public void setPhone(String phone) {
            this.phone = phone;
        }
        
        public Gender getGender() {
            return gender;
        }
        
        public void setGender(Gender gender) {
            this.gender = gender;
        }

        @Override
        public String toString() {
            return "Customer{name='" + name + '\'' + ", age=" + age + ", phone='" + phone + "', gender=" + gender + "}";
        }
    }
    
    private static class Item {
        String name;
        int cost;
        
        public Item(String name, int cost) {
            this.name = name;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "Item{name='" + name + "', cost=" + cost + "}";
        }
    }

    private static class Order {
        Customer customer;
        Item item;
        int amount;

        public Order(Customer customer, Item item, int amount) {
            this.customer = customer;
            this.item = item;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "Order{customer=" + customer + ", item=" + item + ", amount=" + amount + "}";
        }
    }

    public static class CustomerException extends RuntimeException {
        public CustomerException(String message) { super(message); }
    }
    
    public static class ProductException extends RuntimeException {
        public ProductException(String message) { super(message); }
    }
    
    public static class AmountException extends RuntimeException {
        public AmountException(String message) { super(message); }
    }

    private final static Customer[] people = {
        new Customer("Ivan", 20, "+1-222-333-44-55", Gender.MALE),
        new Customer("Petr", 30, "+2-333-444-55-66", Gender.MALE),
        new Customer("Anna", 25, "+3-444-555-66-77", Gender.FEMALE),
        new Customer("Maria", 28, "+4-555-666-77-88", Gender.FEMALE)
    };
    
    private final static Item[] items = {
        new Item("Ball", 100),
        new Item("Sandwich", 1000),
        new Item("Table", 10000),
        new Item("Car", 100000),
        new Item("Rocket", 10000000)
    };
    
    private static Order[] orders = new Order[5];

    private static boolean isInArray(Object[] arr, Object o) {
        for (int i = 0; i < arr.length; i++)
            if (arr[i].equals(o)) return true;
        return false;
    }

    public static Order buy(Customer who, Item what, int howMuch) {
        if (!isInArray(people, who))
            throw new CustomerException("Unknown customer: " + who);
        if (!isInArray(items, what))
            throw new ProductException("Unknown item: " + what);
        if (howMuch < 0 || howMuch > 100)
            throw new AmountException("Incorrect amount: " + howMuch);
        return new Order(who, what, howMuch);
    }
    
    /**
     * Метод для поздравления сотрудников с праздниками
     * @param customers массив покупателей (сотрудников)
     * @param holiday текущий праздник
     */
    public static void congratulateCustomers(Customer[] customers, Holiday holiday) {
        System.out.println("\nПоздравления");
        
        for (Customer customer : customers) {
            switch (holiday) {
                case NEW_YEAR:
                    System.out.println("Дорогой(ая) " + customer.getName() + 
                                     ", поздравляем с Новым Годом! Желаем счастья и успехов!");
                    break;
                case WOMENS_DAY:
                    if (customer.getGender() == Gender.FEMALE) {
                        System.out.println("Дорогая " + customer.getName() + 
                                         ", поздравляем с 8 Марта! Желаем весеннего настроения!");
                    }
                    break;
                case MENS_DAY:
                    if (customer.getGender() == Gender.MALE) {
                        System.out.println("Дорогой " + customer.getName() + 
                                         ", поздравляем с 23 Февраля! Желаем силы и мужества!");
                    }
                    break;
                case NO_HOLIDAY:
                    System.out.println(customer.getName() + ": сегодня нет праздника");
                    break;
            }
        }
    }
    
    /**
     * Метод для определения текущего праздника
     */
    public static Holiday getCurrentHoliday() {
        return Holiday.NEW_YEAR; 
    }

    public static void main(String[] args) {
        System.out.println("Информация о покупателях");
        for (Customer customer : people) {
            System.out.println("Имя: " + customer.getName() + 
                             ", Возраст: " + customer.getAge() + 
                             ", Пол: " + customer.getGender());
        }
        
        Customer testCustomer = people[0];
        System.out.println("\nДемонстрация геттеров и сеттеров");
        System.out.println("Изначальное имя: " + testCustomer.getName());
        testCustomer.setName("Ivan Updated");
        System.out.println("Обновленное имя: " + testCustomer.getName());
        
        Holiday currentHoliday = getCurrentHoliday();
        System.out.println("\nТекущий праздник: " + currentHoliday);
        
        congratulateCustomers(people, currentHoliday);
        
        System.out.println("\nОбработка заказов");
        
        Object[][] info = {
            {people[0], items[0], 1}, 
            {people[1], items[1], -1}, 
            {people[0], items[2], 150}, 
            {people[1], new Item("Flower", 10), 1}, 
            {new Customer("Fedor", 40, "+3-444-555-66-77", Gender.MALE), items[3], 1}, 
        };
        
        int capacity = 0;
        int i = 0;
        while (capacity != orders.length - 1 || i != info.length) {
            try {
                orders[capacity] = buy((Customer) info[i][0], (Item) info[i][1], (int) info[i][2]);
                capacity++;
            } catch (ProductException e) {
                e.printStackTrace();
            } catch (AmountException e) {
                orders[capacity++] = buy((Customer) info[i][0], (Item) info[i][1], 1);
            } catch (CustomerException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println("Orders made: " + capacity);
            }
            ++i;
        }
        
        System.out.println("\nДемонстрация всех праздников");
        
        System.out.println("\n1. Новый год:");
        congratulateCustomers(people, Holiday.NEW_YEAR);
        
        System.out.println("\n2. 8 Марта:");
        congratulateCustomers(people, Holiday.WOMENS_DAY);
        
        System.out.println("\n3. 23 Февраля:");
        congratulateCustomers(people, Holiday.MENS_DAY);
        
        System.out.println("\n4. Нет праздника:");
        congratulateCustomers(people, Holiday.NO_HOLIDAY);
    }
}