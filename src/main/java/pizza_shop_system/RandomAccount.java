package pizza_shop_system;

import java.util.Random;

class RandomAccount extends User {

    static Random random = new Random();
    static String[] firstNames = new String[]{"Olivia", "Ethan", "Ava", "Liam", "Mia", "Noah", "Isabella", "Lucas", "Charlotte", "Mason", "Amelia", "James", "Sophia", "Benjamin", "Emma", "Jacob", "Harper", "William", "Ella", "Henry", "Jackson", "Grace", "Daniel", "Chloe", "Michael", "Zoe", "Alexander", "Lily", "Matthew", "Scarlett", "Jack", "Samantha", "David", "Avery", "Joseph", "Evelyn", "Samuel", "Victoria", "Andrew", "Leah", "Caleb", "Abigail", "Gabriel", "Ella", "Wyatt", "Madison", "Luke", "Grace", "Nathan", "Aria", "Thomas", "Emily", "Isaac", "Julia", "Evan", "Savannah", "Hunter", "Natalie", "Ryan", "Bella", "Logan", "Zoe", "Leo", "Nora", "Elijah", "Eleanor", "Owen", "Madeline", "Carter", "Lily", "Grace", "Oliver", "Sophia", "Mason", "Megan", "Elliot", "Violet", "Landon", "Riley", "Zoey", "Mackenzie", "Jackson", "Addison", "Eli", "Maya", "Jack", "Amos", "Sophie", "Aiden", "Hannah", "Madelyn", "Avery", "Brody", "Tessa", "Carson", "Megan", "Aiden", "Charlotte"};
    static String[] lastNames = new String[]{"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Roberts", "Lee", "Walker", "Hall", "Allen", "Young", "King", "Wright", "Scott", "Torres", "Nguyen", "Adams", "Baker", "Gonzalez", "Nelson", "Carter", "Mitchell", "Perez", "Robinson", "Hernandez", "Graham", "Clark", "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen", "Young", "King", "Wright", "Scott", "Torres", "Nguyen", "Hernandez", "Woods", "Martinez", "Gomez", "Reed", "Cook", "Morgan", "Bell", "Murphy", "Bailey", "Rivera", "Cooper", "Richards", "Chavez", "Ramos", "James", "Stone", "Bennett", "Dixon", "Hughes", "Freeman", "Bryant", "Alexander", "Bishop", "Curtis", "Duncan", "Sanders", "Sullivan", "Foster", "Gonzales", "Warren", "Henry", "Hawkins", "Bush", "Fuller", "Grant", "Bishop", "Curtis", "Duncan", "Sampson", "Simmons", "Perry", "Richards", "Knight", "Spencer", "Sutton", "Morris", "Ross", "Stewart", "Jenkins", "Patterson", "Hunt", "Carlson", "Gibson", "Austin", "Black", "Diaz", "Palmer", "Fox", "Sanders", "Kelley", "Bowman", "Stone", "West", "Jordan", "Hunter", "Burns"};
    static String[] addresses = new String[]{"123 Maple St, Atlanta, GA 30301", "456 Oak Rd, Savannah, GA 31401", "789 Pine Ave, Augusta, GA 30901", "101 Birch Blvd, Macon, GA 31201", "202 Cedar Ln, Columbus, GA 31901", "303 Elm St, Athens, GA 30601", "404 Willow Dr, Albany, GA 31701", "505 Ash St, Marietta, GA 30008", "606 Redwood Dr, Valdosta, GA 31601", "707 Poplar St, Roswell, GA 30075", "808 Birchwood Blvd, Sandy Springs, GA 30328", "909 Spruce Ln, Johns Creek, GA 30022", "123 Elmwood Ave, Warner Robins, GA 31093", "234 Maple Rd, Newnan, GA 30263", "345 Oakwood Dr, Kennesaw, GA 30144", "456 Pine Hill Rd, Dalton, GA 30720", "567 Willow Ln, Gainesville, GA 30501", "678 Cedar St, Douglasville, GA 30134", "789 Birch Blvd, Canton, GA 30114", "890 Redwood Rd, Peachtree City, GA 30269", "901 Ashwood Dr, Hinesville, GA 31313", "112 Pine Ave, Tifton, GA 31794", "223 Maple St, LaGrange, GA 30240", "334 Oak Ln, Griffin, GA 30223", "445 Elmwood Dr, McDonough, GA 30253", "556 Birch St, Rome, GA 30165", "667 Willow Rd, Smyrna, GA 30080", "778 Cedar Blvd, Stockbridge, GA 30281", "889 Pine Dr, Athens, GA 30606", "990 Redwood Ave, Cumming, GA 30040", "101 Birchwood Rd, Lawrenceville, GA 30044", "202 Oak Dr, Statesboro, GA 30458", "303 Cedar Ln, Griffin, GA 30224", "404 Pinewood Ave, Augusta, GA 30904", "505 Ashwood Rd, Warner Robins, GA 31088", "606 Redwood Blvd, Decatur, GA 30030", "707 Birch Dr, Albany, GA 31705", "808 Maple Ln, Milton, GA 30004", "909 Cedar St, Lilburn, GA 30047", "112 Oakwood Ave, Cartersville, GA 30120", "223 Pine St, Douglas, GA 31533", "334 Maple Blvd, Duluth, GA 30096", "445 Ash Dr, Norcross, GA 30071", "556 Redwood Ln, Suwanee, GA 30024", "667 Birch Ave, Woodstock, GA 30188", "778 Cedar Dr, East Point, GA 30344", "889 Oakwood Rd, Fort Valley, GA 31030", "990 Pinewood Ln, LaFayette, GA 30728"};

    public RandomAccount() {
        super();
        String randomFirstName = firstNames[random.nextInt(firstNames.length)];
        String randomLastName = lastNames[random.nextInt(lastNames.length)];
        String randomName = randomFirstName + " " + randomLastName;
        String randomAddress = addresses[random.nextInt(addresses.length)];
        int randEmailNumber = random.nextInt(1000);
        String newEmail = randomFirstName + randomLastName + String.format("%3d", randEmailNumber) + "@email.com";
        int phoneArea = random.nextInt(100, 999);
        int midPhone = random.nextInt(100, 999);
        int lastPhone = random.nextInt(10000);
        String randomPhoneNumber = String.format("(%03d)%03d-%04d", phoneArea, midPhone, lastPhone);
        int passwordLength = random.nextInt(6, 16);
        StringBuilder password = new StringBuilder();

        for(int i = 0; i < passwordLength; ++i) {
            int ascii;
            do {
                do {
                    ascii = random.nextInt(48, 123);
                } while(ascii >= 58 && ascii <= 62);
            } while(ascii >= 91 && ascii <= 96);
            //Potential optimization here instead of this do while. May look at it later

            char randChar = (char)ascii;
            password.append(randChar);
        }

        this.setName(randomName);
        this.setEmail(newEmail);
        this.setAddress(randomAddress);
        this.setPhoneNumber(randomPhoneNumber);
        this.setPassword(password.toString());
    }

}
